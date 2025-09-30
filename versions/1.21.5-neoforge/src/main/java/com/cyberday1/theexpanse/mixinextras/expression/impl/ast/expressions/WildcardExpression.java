package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.expressions;

import com.cyberday1.theexpanse.mixinextras.expression.impl.ExpressionSource;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.point.ExpressionContext;

public class WildcardExpression extends SimpleExpression {
    public WildcardExpression(ExpressionSource src) {
        super(src);
    }

    @Override
    protected boolean matchesImpl(FlowValue node, ExpressionContext ctx) {
        return true;
    }
}
