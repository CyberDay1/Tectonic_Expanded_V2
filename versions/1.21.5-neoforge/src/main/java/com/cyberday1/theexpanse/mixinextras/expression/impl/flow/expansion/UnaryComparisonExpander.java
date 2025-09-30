package com.cyberday1.theexpanse.mixinextras.expression.impl.flow.expansion;

import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.postprocessing.FlowPostProcessor;
import com.cyberday1.theexpanse.mixinextras.expression.impl.utils.ExpressionASMUtils;
import com.cyberday1.theexpanse.mixinextras.expression.impl.utils.FlowDecorations;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;

public class UnaryComparisonExpander extends InsnExpander {
    @Override
    public void process(FlowValue node, FlowPostProcessor.OutputSink sink) {
        AbstractInsnNode insn = node.getInsn();
        int cstOpcode = getCstOpcode(insn);
        if (cstOpcode == -1) {
            return;
        }
        JumpInsnNode jump = (JumpInsnNode) insn;
        int jumpOpcode = getExpandedJumpOpcode(insn);

        if (isComplexComparison(node.getInput(0))) {
            // Complex comparisons are similar to `lcmp(a, b) == 0`, for example, but the comparison with zero
            // should not be considered.
            node.getInput(0).decorate(FlowDecorations.COMPLEX_COMPARISON_JUMP, node);
            sink.markAsSynthetic(node);
            return;
        }

        AbstractInsnNode cstInsn = new InsnNode(cstOpcode);
        FlowValue cst = new FlowValue(ExpressionASMUtils.getNewType(cstInsn), cstInsn);
        registerComponent(cst, Component.CST, jump);

        node.setInsn(new JumpInsnNode(jumpOpcode, jump.label));
        node.setParents(node.getInput(0), cst);
        registerComponent(node, Component.JUMP, jump);

        sink.registerFlow(cst);
    }

    @Override
    public void expand(Target target, InjectionNodes.InjectionNode node, Expansion expansion) {
        if (node.isReplaced()) {
            // A @ModifyConstant has been here
            AbstractInsnNode next = node.getCurrentTarget().getNext();
            if (!(next instanceof JumpInsnNode)) {
                throw new IllegalStateException("Could not find jump for expanded @ModifyConstant comparison! Please inform LlamaLad7!");
            }
            JumpInsnNode jump = (JumpInsnNode) next;
            expansion.registerInsn(Component.CST, node.getCurrentTarget());
            expansion.registerInsn(Component.JUMP, jump);
            return;
        }
        AbstractInsnNode insn = node.getCurrentTarget();
        int cstOpcode = getCstOpcode(insn);
        if (cstOpcode == -1) {
            return;
        }
        JumpInsnNode jump = (JumpInsnNode) insn;
        int jumpOpcode = getExpandedJumpOpcode(insn);
        target.method.maxStack++;
        expandInsn(
                target, node,
                expansion.registerInsn(Component.CST, new InsnNode(cstOpcode)),
                expansion.registerInsn(Component.JUMP, new JumpInsnNode(jumpOpcode, jump.label))
        );
    }

    private int getCstOpcode(AbstractInsnNode insn) {
        if (Opcodes.IFEQ <= insn.getOpcode() && insn.getOpcode() <= Opcodes.IFLE) {
            return Opcodes.ICONST_0;
        }
        if (insn.getOpcode() == Opcodes.IFNULL || insn.getOpcode() == Opcodes.IFNONNULL) {
            return Opcodes.ACONST_NULL;
        }
        return -1;
    }

    private int getExpandedJumpOpcode(AbstractInsnNode insn) {
        if (Opcodes.IFEQ <= insn.getOpcode() && insn.getOpcode() <= Opcodes.IFLE) {
            return insn.getOpcode() + (Opcodes.IF_ICMPEQ - Opcodes.IFEQ);
        }
        if (insn.getOpcode() == Opcodes.IFNULL || insn.getOpcode() == Opcodes.IFNONNULL) {
            return insn.getOpcode() - (Opcodes.IFNULL - Opcodes.IF_ACMPEQ);
        }
        return -1;
    }

    private boolean isComplexComparison(FlowValue node) {
        if (node.isComplex()) {
            return false;
        }
        AbstractInsnNode insn = node.getInsn();
        return Opcodes.LCMP <= insn.getOpcode() && insn.getOpcode() <= Opcodes.DCMPG;
    }

    private enum Component implements InsnComponent {
        CST, JUMP
    }
}
