package com.cyberday1.theexpanse.mixinextras.utils;

import com.cyberday1.theexpanse.mixinextras.versions.MixinVersion;
import com.cyberday1.theexpanse.mixinextras.wrapper.WrapperInjectionInfo;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator.Context;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Helpers for writing code that is compatible with all variants of Mixin 0.8+
 */
public class CompatibilityHelper {
    public static RuntimeException makeInvalidInjectionException(InjectionInfo info, String message) {
        return MixinVersion.getInstance().makeInvalidInjectionException(info, message);
    }

    public static IMixinContext getMixin(InjectionInfo info) {
        return MixinVersion.getInstance().getMixin(info);
    }

    public static Context makeLvtContext(InjectionInfo info, Type returnType, boolean argsOnly, Target target, AbstractInsnNode node) {
        return MixinVersion.getInstance().makeLvtContext(info, returnType, argsOnly, target, node);
    }

    public static void preInject(InjectionInfo info) {
        MixinVersion.getInstance().preInject(info);
    }

    public static AnnotationNode getAnnotation(InjectionInfo info) {
        return MixinVersion.getInstance().getAnnotation(info);
    }

    public static int getOrder(InjectionInfo info) {
        return MixinVersion.getInstance().getOrder(info);
    }

    public static List<Target> getTargets(InjectionInfo info) {
        if (info instanceof WrapperInjectionInfo) {
            return ((WrapperInjectionInfo) info).getSelectedTargets();
        }
        return new ArrayList<>(MixinVersion.getInstance().getTargets(info));
    }

    public static MemberInfo parseMemberInfo(String targetSelector, InjectionInfo info) {
        return MixinVersion.getInstance().parseMemberInfo(targetSelector, info);
    }
}
