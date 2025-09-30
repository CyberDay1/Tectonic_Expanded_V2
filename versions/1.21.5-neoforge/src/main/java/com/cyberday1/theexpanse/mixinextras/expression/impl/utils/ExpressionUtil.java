package com.cyberday1.theexpanse.mixinextras.expression.impl.utils;

import com.cyberday1.theexpanse.mixinextras.expression.impl.ast.expressions.CapturingExpression;
import com.cyberday1.theexpanse.mixinextras.expression.impl.ast.expressions.Expression;

public class ExpressionUtil {
    public static Expression skipCapturesDown(Expression expr) {
        while (expr instanceof CapturingExpression) {
            expr = ((CapturingExpression) expr).expression;
        }
        return expr;
    }
}
