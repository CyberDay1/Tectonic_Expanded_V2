package com.cyberday1.tectonicexpanded.mixin;

import com.cyberday1.tectonicexpanded.world.WorldgenConstants;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Heightmap.class)
public abstract class HeightmapMixin {
    // CUSTOM: extended vertical range (heightmap)
    @Redirect(method = "primeHeightmaps", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkAccess;getHighestSectionPosition()I"))
    private static int tectonic$extendHeightmapTop(ChunkAccess chunkAccess) {
        int vanillaTop = chunkAccess.getHighestSectionPosition();
        int customTop = WorldgenConstants.OVERWORLD_MAX_Y + 1;
        if (chunkAccess.getHeight() == WorldgenConstants.OVERWORLD_HEIGHT && chunkAccess.getMinBuildHeight() == WorldgenConstants.OVERWORLD_MIN_Y) {
            return Math.max(vanillaTop, customTop);
        }
        return vanillaTop;
    }

    // CUSTOM: extended vertical range (heightmap)
    @Redirect(method = "primeHeightmaps", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkAccess;getMinBuildHeight()I"))
    private static int tectonic$extendHeightmapBottomPrime(ChunkAccess chunkAccess) {
        int vanillaMin = chunkAccess.getMinBuildHeight();
        if (chunkAccess.getHeight() == WorldgenConstants.OVERWORLD_HEIGHT && vanillaMin > WorldgenConstants.OVERWORLD_MIN_Y) {
            return WorldgenConstants.OVERWORLD_MIN_Y;
        }
        return vanillaMin;
    }

    // CUSTOM: extended vertical range (heightmap)
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkAccess;getMinBuildHeight()I"))
    private int tectonic$extendHeightmapBottomUpdate(ChunkAccess chunkAccess) {
        int vanillaMin = chunkAccess.getMinBuildHeight();
        if (chunkAccess.getHeight() == WorldgenConstants.OVERWORLD_HEIGHT && vanillaMin > WorldgenConstants.OVERWORLD_MIN_Y) {
            return WorldgenConstants.OVERWORLD_MIN_Y;
        }
        return vanillaMin;
    }
}
