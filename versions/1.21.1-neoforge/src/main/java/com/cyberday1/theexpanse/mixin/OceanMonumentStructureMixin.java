package com.cyberday1.theexpanse.mixin;

import com.cyberday1.theexpanse.world.WorldgenConstants;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OceanMonumentStructure.class)
public abstract class OceanMonumentStructureMixin {
    // CUSTOM: extended vertical range (structure placement)
    @Inject(method = "generatePieces", at = @At("TAIL"))
    private static void theexpanse$extendMonument(StructurePiecesBuilder builder, GenerationContext context, CallbackInfo ci) {
        if (context.chunkGenerator().getMinBuildHeight() > WorldgenConstants.OVERWORLD_MIN_Y) {
            builder.moveBelowSeaLevel(
                context.chunkGenerator().getSeaLevel(),
                WorldgenConstants.OVERWORLD_MIN_Y,
                context.random(),
                0
            );
        }
    }
}
