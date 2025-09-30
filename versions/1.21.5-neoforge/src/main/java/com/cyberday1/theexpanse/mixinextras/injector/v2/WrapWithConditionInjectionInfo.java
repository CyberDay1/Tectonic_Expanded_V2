package com.cyberday1.theexpanse.mixinextras.injector.v2;

import com.cyberday1.theexpanse.mixinextras.injector.MixinExtrasLateInjectionInfo;
import com.cyberday1.theexpanse.mixinextras.injector.WrapWithConditionInjector;
import com.cyberday1.theexpanse.mixinextras.utils.InjectorUtils;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.HandlerPrefix;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@InjectionInfo.AnnotationType(WrapWithCondition.class)
@HandlerPrefix("wrapWithCondition")
public class WrapWithConditionInjectionInfo extends MixinExtrasLateInjectionInfo {
    public WrapWithConditionInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
        super(mixin, method, annotation);
    }

    @Override
    protected Injector parseInjector(AnnotationNode injectAnnotation) {
        return new WrapWithConditionInjector(this);
    }

    @Override
    public void prepare() {
        super.prepare();
        InjectorUtils.checkForImmediatePops(targetNodes);
    }

    @Override
    public String getLateInjectionType() {
        return "WrapWithCondition";
    }
}
