package com.cyberday1.theexpanse.mixinextras.expression.impl.point;

import com.cyberday1.theexpanse.mixinextras.expression.impl.ExpressionService;
import com.cyberday1.theexpanse.mixinextras.expression.impl.flow.FlowContext;
import com.cyberday1.theexpanse.mixinextras.utils.CompatibilityHelper;
import com.cyberday1.theexpanse.mixinextras.utils.InjectorUtils;
import org.objectweb.asm.Type;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

class RuntimeExpressionService extends ExpressionService {
    @Override
    public RuntimeException makeInvalidInjectionException(InjectionInfo info, String message) {
        return CompatibilityHelper.makeInvalidInjectionException(info, message);
    }

    @Override
    public void decorateInjectorSpecific(InjectionNodes.InjectionNode node, InjectionInfo info, String key, Object value) {
        InjectorUtils.decorateInjectorSpecific(node, info, key, value);
    }

    @Override
    public Type getCommonSuperClass(FlowContext ctx, Type type1, Type type2) {
        return ClassInfo.getCommonSuperClassOrInterface(type1, type2).getType();
    }
}
