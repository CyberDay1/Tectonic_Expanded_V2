package com.cyberday1.theexpanse.mixinextras.expression.impl.flow;

import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.expansion.IincExpander;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.expansion.StringConcatFactoryExpander;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.expansion.UnaryComparisonExpander;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.postprocessing.*;
import com.cyberday1.theexpanse.mixinextras.expression.impl.utils.ExpressionASMUtils;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.Interpreter;
import org.spongepowered.asm.util.asm.ASM;

import java.util.*;
import java.util.function.Function;

import static org.objectweb.asm.Opcodes.*;

public class FlowInterpreter extends Interpreter<FlowValue> {
    private final FlowContext context;
    private final Map<AbstractInsnNode, FlowValue> cache = new IdentityHashMap<>();
    private final Map<VarInsnNode, Type> localTypes;
    private final List<FlowPostProcessor> postProcessors;

    protected FlowInterpreter(ClassNode classNode, MethodNode methodNode, FlowContext ctx) {
        super(ASM.API_VERSION);
        this.context = ctx;
        this.localTypes = LocalsCalculator.getLocalTypes(classNode, methodNode, ctx);
        this.postProcessors = Arrays.asList(
                new NewArrayPostProcessor(methodNode), // Must go early because it is sensitive to BCI
                new IincExpander(),
                new UnaryComparisonExpander(),
                new StringConcatFactoryExpander(),
                new InstantiationPostProcessor(),
                new StringConcatPostProcessor(),
                new CallTaggingPostProcessor(classNode, methodNode),
                new LMFPostProcessor(classNode),

                new SplitNodeRemovalPostProcessor()
        );
    }

    public static Collection<FlowValue> analyze(ClassNode classNode, MethodNode methodNode, FlowContext ctx) {
        FlowInterpreter interpreter = new FlowInterpreter(classNode, methodNode, ctx);
        try {
            new Analyzer<>(interpreter).analyze(classNode.name, methodNode);
        } catch (AnalyzerException e) {
            throw new RuntimeException("Failed to analyze value flow: ", e);
        }
        return new ArrayList<>(interpreter.finish());
    }

    public Collection<FlowValue> finish() {
        Set<FlowValue> flows = Collections.newSetFromMap(new IdentityHashMap<>());
        flows.addAll(cache.values());
        for (FlowValue value : flows) {
            value.finish();
        }
        for (FlowValue value : flows) {
            value.onFinished();
        }
        for (FlowPostProcessor postProcessor : postProcessors) {
            Set<FlowValue> synthetic = Collections.newSetFromMap(new IdentityHashMap<>());
            List<FlowValue> newFlows = new ArrayList<>();
            FlowPostProcessor.OutputSink sink = new FlowPostProcessor.OutputSink() {
                @Override
                public void markAsSynthetic(FlowValue node) {
                    if (!node.isComplex()) {
                        synthetic.add(node);
                    }
                }

                @Override
                public void registerFlow(FlowValue... nodes) {
                    for (FlowValue node : nodes) {
                        if (!node.isComplex()) {
                            newFlows.add(node);
                        }
                    }
                }
            };
            for (FlowValue value : flows) {
                postProcessor.process(value, sink);
            }
            flows.removeAll(synthetic);
            for (FlowValue syntheticValue : synthetic) {
                syntheticValue.setParents(); // Unlink from the graph
            }
            flows.addAll(newFlows);
            for (FlowValue value : flows) {
                value.finish();
            }
            for (FlowValue value : flows) {
                value.onFinished();
            }
        }
        return flows;
    }

    @Override
    public FlowValue newValue(final Type type) {
        if (type == null) {
            return DummyFlowValue.UNINITIALIZED;
        }
        if (type == Type.VOID_TYPE) {
            return null;
        }
        return new DummyFlowValue(type);
    }

    @Override
    public FlowValue newOperation(final AbstractInsnNode insn) {
        Type type = ExpressionASMUtils.getNewType(insn);
        return recordFlow(type, insn);
    }

    @Override
    public FlowValue copyOperation(final AbstractInsnNode insn, FlowValue value) {
        switch (insn.getOpcode()) {
            case DUP:
            case DUP_X1:
            case DUP_X2:
            case DUP2:
            case DUP2_X1:
            case DUP2_X2:
            case SWAP:
                return value;
            case ISTORE:
            case LSTORE:
            case FSTORE:
            case DSTORE:
            case ASTORE:
                recordFlow(Type.VOID_TYPE, insn, value);
                return new DummyFlowValue(value.getType());
        }
        VarInsnNode varNode = (VarInsnNode) insn;
        Type type = localTypes.get(varNode);
        return recordFlow(type, insn);
    }

    @Override
    public FlowValue unaryOperation(final AbstractInsnNode insn, final FlowValue value) {
        Type type = ExpressionASMUtils.getUnaryType(insn);
        if (insn.getOpcode() == IINC) {
            recordFlow(Type.VOID_TYPE, insn);
            return new DummyFlowValue(type);
        }
        return recordFlow(type, insn, value);
    }

    @Override
    public FlowValue binaryOperation(
            final AbstractInsnNode insn, final FlowValue value1, final FlowValue value2) {
        if (insn.getOpcode() == AALOAD || insn.getOpcode() == BALOAD) {
            // Can't determine their types without the parent type
            // AALOAD could give any object type, and BALOAD could give a byte or a boolean
            return recordComputedFlow(1, inputs -> ExpressionASMUtils.getInnerType(inputs[0].getType()), insn, value1, value2);
        }
        Type type = ExpressionASMUtils.getBinaryType(insn, null);
        return recordFlow(type, insn, value1, value2);
    }

    @Override
    public FlowValue ternaryOperation(
            final AbstractInsnNode insn,
            final FlowValue value1,
            final FlowValue value2,
            final FlowValue value3) {
        return recordFlow(Type.VOID_TYPE, insn, value1, value2, value3);
    }

    @Override
    public FlowValue naryOperation(
            final AbstractInsnNode insn, final List<? extends FlowValue> values) {
        if (insn instanceof MethodInsnNode && Boxing.isBoxing((MethodInsnNode) insn)) {
            return values.get(0);
        }
        Type type = ExpressionASMUtils.getNaryType(insn);
        return recordFlow(type, insn, values.toArray(new FlowValue[0]));
    }

    @Override
    public void returnOperation(
            final AbstractInsnNode insn, final FlowValue value, final FlowValue expected) {
        // Nothing to do.
    }

    @Override
    public FlowValue merge(final FlowValue value1, final FlowValue value2) {
        return value1.mergeWith(value2, context);
    }

    private FlowValue recordFlow(Type type, AbstractInsnNode insn, FlowValue... inputs) {
        FlowValue cached = cache.get(insn);
        if (cached == null) {
            cached = new FlowValue(type, insn, inputs);
            cache.put(insn, cached);
        } else {
            cached.mergeInputs(inputs, context);
        }
        return cached;
    }

    private FlowValue recordComputedFlow(int size, Function<FlowValue[], Type> type, AbstractInsnNode insn, FlowValue... inputs) {
        FlowValue cached = cache.get(insn);
        if (cached == null) {
            cached = new ComputedFlowValue(size, type, insn, inputs);
            cache.put(insn, cached);
        } else {
            cached.mergeInputs(inputs, context);
        }
        return cached;
    }
}
