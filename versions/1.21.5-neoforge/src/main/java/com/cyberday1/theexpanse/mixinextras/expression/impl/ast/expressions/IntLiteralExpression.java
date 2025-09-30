package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.expressions;

import com.cyberday1.theexpanse.mixinextras.expression.impl.ExpressionSource;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.point.ExpressionContext;
import com.cyberday1.theexpanse.mixinextras.expression.impl.utils.ExpressionASMUtils;
import org.objectweb.asm.Type;

public class IntLiteralExpression extends SimpleExpression {
    public final long value;

    public IntLiteralExpression(ExpressionSource src, long value) {
        super(src);
        this.value = value;
    }

    @Override
    protected boolean matchesImpl(FlowValue node, ExpressionContext ctx) {
        if (!node.typeMatches(Type.INT_TYPE) && !node.typeMatches(Type.LONG_TYPE)) {
            return false;
        }
        Object cst = ExpressionASMUtils.getConstant(node.getInsn());
        if (cst == null) {
            return false;
        }
        return (cst instanceof Integer || cst instanceof Long) && ((Number) cst).longValue() == value;
    }
}
