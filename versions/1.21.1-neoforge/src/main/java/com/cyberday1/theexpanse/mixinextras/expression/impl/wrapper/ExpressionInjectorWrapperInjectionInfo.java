package com.cyberday1.theexpanse.mixinextras.expression.impl.wrapper;

import com.cyberday1.theexpanse.mixinextras.wrapper.WrapperInjectionInfo;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@InjectionInfo.AnnotationType(ExpressionInjectorWrapper.class)
@InjectionInfo.HandlerPrefix("expressionWrapper")
public class ExpressionInjectorWrapperInjectionInfo extends WrapperInjectionInfo {
    public ExpressionInjectorWrapperInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(ExpressionInjectorWrapperImpl::new, mixin, method, annotation);
    }
}
