package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.expressions;

import com.cyberday1.theexpanse.mixinextras.expression.impl.ExpressionSource;
import com.cyberday1.theexpanse.mixinextras.expression.impl.ast.identifiers.MemberIdentifier;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.postprocessing.MethodCallType;
import com.cyberday1.theexpanse.mixinextras.expression.impl.point.ExpressionContext;

import java.util.List;

public class SuperCallExpression extends SimpleExpression {
    public final MemberIdentifier name;
    public final List<Expression> arguments;

    public SuperCallExpression(ExpressionSource src, MemberIdentifier name, List<Expression> arguments) {
        super(src);
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    protected boolean matchesImpl(FlowValue node, ExpressionContext ctx) {
        if (!MethodCallType.SUPER.matches(node)) {
            return false;
        }
        if (!name.matches(ctx.pool, node)) {
            return false;
        }
        return inputsMatch(node, ctx, ctx.allowIncompleteListInputs, arguments.toArray(new Expression[0]));
    }
}
