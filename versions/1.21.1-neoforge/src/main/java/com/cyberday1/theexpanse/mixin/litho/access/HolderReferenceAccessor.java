package com.cyberday1.theexpanse.mixin.litho.access;

import net.minecraft.core.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Holder.Reference.class)
public interface HolderReferenceAccessor<T> {
    @Accessor("value")
    void setValue(T value);
}
