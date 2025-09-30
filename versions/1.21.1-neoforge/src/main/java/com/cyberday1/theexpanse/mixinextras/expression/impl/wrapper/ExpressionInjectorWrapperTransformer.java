package com.cyberday1.theexpanse.mixinextras.expression.impl.wrapper;

import com.cyberday1.theexpanse.mixinextras.expression.Expression;
import com.cyberday1.theexpanse.mixinextras.transformer.MixinTransformer;
import com.cyberday1.theexpanse.mixinextras.utils.ASMUtils;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

public class ExpressionInjectorWrapperTransformer implements MixinTransformer {
    @Override
    public void transform(IMixinInfo mixinInfo, ClassNode mixinNode) {
        for (MethodNode method : mixinNode.methods) {
            if (ASMUtils.getRepeatedMEAnnotation(method, Expression.class) != null) {
                AnnotationNode ann = InjectionInfo.getInjectorAnnotation(mixinInfo, method);
                wrapInjectorAnnotation(method, ann);
            }
        }
    }

    private void wrapInjectorAnnotation(MethodNode method, AnnotationNode ann) {
        AnnotationNode wrapped = new AnnotationNode(Type.getDescriptor(ExpressionInjectorWrapper.class));
        wrapped.visit("original", ann);
        method.visibleAnnotations.remove(ann);
        method.visibleAnnotations.add(wrapped);
    }
}
