package com.cyberday1.theexpanse.mixinextras.expression.impl.ast.expressions;

import com.cyberday1.theexpanse.mixinextras.expression.impl.ExpressionSource;
import com.cyberday1.theexpanse.mixinextras.expression.impl.ast.identifiers.TypeIdentifier;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowValue;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.postprocessing.LMFInfo;
import com.cyberday1.theexpanse.mixinextras.expression.impl.point.ExpressionContext;
import com.cyberday1.theexpanse.mixinextras.expression.impl.utils.FlowDecorations;
import org.objectweb.asm.Type;

public class ConstructorReferenceExpression extends SimpleExpression {
    public final TypeIdentifier type;

    public ConstructorReferenceExpression(ExpressionSource src, TypeIdentifier type) {
        super(src);
        this.type = type;
    }

    @Override
    protected boolean matchesImpl(FlowValue node, ExpressionContext ctx) {
        LMFInfo info = node.getDecoration(FlowDecorations.LMF_INFO);
        if (info == null || info.type != LMFInfo.Type.INSTANTIATION) {
            return false;
        }
        return type.matches(ctx.pool, Type.getObjectType(info.impl.getOwner()));
    }
}
