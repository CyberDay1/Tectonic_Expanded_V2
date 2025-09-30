package com.cyberday1.theexpanse.mixinextras.sugar.impl;


import com.cyberday1.theexpanse.mixinextras.wrapper.WrapperInjectionInfo;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@InjectionInfo.AnnotationType(SugarWrapper.class)
@InjectionInfo.HandlerPrefix("sugarWrapper")
public class SugarWrapperInjectionInfo extends WrapperInjectionInfo {
    public SugarWrapperInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(SugarWrapperImpl::new, mixin, method, annotation);
    }
}
