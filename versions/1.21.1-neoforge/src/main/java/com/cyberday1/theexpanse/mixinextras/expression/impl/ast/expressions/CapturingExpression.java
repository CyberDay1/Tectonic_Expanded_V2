package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.expressions;

import com.cyberday1.theexpanse.mixinextras.expression.impl.ExpressionSource;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.point.ExpressionContext;

public class CapturingExpression extends SimpleExpression {
    public final Expression expression;

    public CapturingExpression(ExpressionSource src, Expression expression) {
        super(src);
        this.expression = expression;
    }

    @Override
    protected boolean matchesImpl(FlowValue node, ExpressionContext ctx) {
        boolean matches = expression.matches(node, ctx);
        if (matches) {
            expression.capture(node, ctx);
        }
        return matches;
    }
}
