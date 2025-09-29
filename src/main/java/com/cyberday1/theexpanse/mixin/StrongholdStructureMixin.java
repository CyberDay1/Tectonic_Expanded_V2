package com.cyberday1.theexpanse.mixin;

import com.cyberday1.theexpanse.world.WorldgenConstants;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(StrongholdStructure.class)
public abstract class StrongholdStructureMixin {
    // CUSTOM: extended vertical range (structure placement)
    @ModifyArg(
        method = "generatePieces",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePiecesBuilder;moveBelowSeaLevel(IILnet/minecraft/util/RandomSource;I)I"
        ),
        index = 1
    )
    private static int theexpanse$extendStrongholdFloor(int minY) {
        return Math.min(minY, WorldgenConstants.OVERWORLD_MIN_Y);
    }
}
