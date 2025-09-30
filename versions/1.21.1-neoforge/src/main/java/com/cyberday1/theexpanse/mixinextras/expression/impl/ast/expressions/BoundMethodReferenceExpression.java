package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.expressions;

import com.cyberday1.theexpanse.mixinextras.expression.impl.ExpressionSource;
import com.cyberday1.theexpanse.mixinextras.expression.impl.ast.identifiers.MemberIdentifier;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.postprocessing.LMFInfo;
import com.cyberday1.theexpanse.mixinextras.expression.impl.point.ExpressionContext;
import com.cyberday1.theexpanse.mixinextras.expression.impl.utils.FlowDecorations;

public class BoundMethodReferenceExpression extends SimpleExpression {
    public final Expression receiver;
    public final MemberIdentifier name;

    public BoundMethodReferenceExpression(ExpressionSource src, Expression receiver, MemberIdentifier name) {
        super(src);
        this.receiver = receiver;
        this.name = name;
    }

    @Override
    protected boolean matchesImpl(FlowValue node, ExpressionContext ctx) {
        LMFInfo info = node.getDecoration(FlowDecorations.LMF_INFO);
        if (info == null || info.type != LMFInfo.Type.BOUND_METHOD || !name.matches(ctx.pool, node)) {
            return false;
        }
        ctx.reportPartialMatch(node, this);
        return receiver.matches(node.getInput(0), ctx);
    }
}
