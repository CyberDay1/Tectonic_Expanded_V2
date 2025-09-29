package com.cyberday1.theexpanse.mixin;

import com.cyberday1.theexpanse.world.WorldgenConstants;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MineshaftStructure.class)
public abstract class MineshaftStructureMixin {
    // CUSTOM: extended vertical range (structure placement)
    @ModifyArg(
        method = "findGenerationPoint",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;<init>(III)V"),
        index = 1
    )
    private int theexpanse$adjustBaseY(int originalY) {
        return WorldgenConstants.OVERWORLD_MIN_Y + 50;
    }
}
