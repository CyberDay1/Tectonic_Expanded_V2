package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.expressions;

import com.cyberday1.theexpanse.mixinextras.expression.impl.ExpressionSource;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.point.ExpressionContext;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.VarInsnNode;

public class ThisExpression extends SimpleExpression {
    public ThisExpression(ExpressionSource src) {
        super(src);
    }

    @Override
    protected boolean matchesImpl(FlowValue node, ExpressionContext ctx) {
        if (ctx.isStatic) {
            return false;
        }
        return node.getInsn().getOpcode() == Opcodes.ALOAD && ((VarInsnNode) node.getInsn()).var == 0;
    }
}
