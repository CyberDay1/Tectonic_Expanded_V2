package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.expressions;

import com.cyberday1.theexpanse.mixinextras.expression.impl.ExpressionSource;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.point.ExpressionContext;
import com.cyberday1.theexpanse.mixinextras.expression.impl.utils.ExpressionDecorations;
import com.cyberday1.theexpanse.mixinextras.expression.impl.utils.ExpressionASMUtils;
import org.objectweb.asm.Type;

public abstract class SimpleExpression extends Expression {
    public SimpleExpression(ExpressionSource src) {
        super(src);
    }

    @Override
    public void capture(FlowValue node, ExpressionContext ctx) {
        Type type = node.getType();
        if (type.equals(ExpressionASMUtils.BOTTOM_TYPE)) {
            type = ExpressionASMUtils.OBJECT_TYPE;
        }
        if (!type.equals(Type.VOID_TYPE)) {
            ctx.decorate(node.getInsn(), ExpressionDecorations.SIMPLE_EXPRESSION_TYPE, type);
        }
        super.capture(node, ctx);
    }
}
