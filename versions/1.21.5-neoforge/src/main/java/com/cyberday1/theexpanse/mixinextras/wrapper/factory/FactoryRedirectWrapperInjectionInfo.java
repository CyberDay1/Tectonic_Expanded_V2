package com.cyberday1.theexpanse.mixinextras.wrapper.factory;

import com.cyberday1.theexpanse.mixinextras.wrapper.WrapperInjectionInfo;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@InjectionInfo.AnnotationType(FactoryRedirectWrapper.class)
@InjectionInfo.HandlerPrefix("factoryWrapper")
public class FactoryRedirectWrapperInjectionInfo extends WrapperInjectionInfo {
    public FactoryRedirectWrapperInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(FactoryRedirectWrapperImpl::new, mixin, method, annotation);
    }
}
