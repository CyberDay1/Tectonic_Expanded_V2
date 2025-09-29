package com.cyberday1.theexpanse.mixin;

import com.cyberday1.theexpanse.world.WorldgenConstants;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Heightmap.class)
public abstract class HeightmapMixin {
    // CUSTOM: extended vertical range (heightmap)
    @Redirect(method = "primeHeightmaps", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkAccess;getHighestSectionPosition()I"))
    private static int theexpanse$extendHeightmapTop(ChunkAccess chunkAccess) {
        int vanillaTop = chunkAccess.getHighestSectionPosition();
        int customTop = WorldgenConstants.OVERWORLD_MAX_Y + 1;
        LevelHeightAccessorExtension accessor = (LevelHeightAccessorExtension) chunkAccess;
        int minY = accessor.getMinY();
        int height = accessor.getMaxY() - minY;
        if (height == WorldgenConstants.OVERWORLD_HEIGHT && minY == WorldgenConstants.OVERWORLD_MIN_Y) {
            return Math.max(vanillaTop, customTop);
        }
        return vanillaTop;
    }

    // CUSTOM: extended vertical range (heightmap)
    @Redirect(method = "primeHeightmaps", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkAccess;getMinY()I"))
    private static int theexpanse$extendHeightmapBottomPrime(ChunkAccess chunkAccess) {
        LevelHeightAccessorExtension accessor = (LevelHeightAccessorExtension) chunkAccess;
        int vanillaMin = accessor.getMinY();
        int height = accessor.getMaxY() - vanillaMin;
        if (height == WorldgenConstants.OVERWORLD_HEIGHT && vanillaMin > WorldgenConstants.OVERWORLD_MIN_Y) {
            return WorldgenConstants.OVERWORLD_MIN_Y;
        }
        return vanillaMin;
    }

    // CUSTOM: extended vertical range (heightmap)
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkAccess;getMinY()I"))
    private int theexpanse$extendHeightmapBottomUpdate(ChunkAccess chunkAccess) {
        LevelHeightAccessorExtension accessor = (LevelHeightAccessorExtension) chunkAccess;
        int vanillaMin = accessor.getMinY();
        int height = accessor.getMaxY() - vanillaMin;
        if (height == WorldgenConstants.OVERWORLD_HEIGHT && vanillaMin > WorldgenConstants.OVERWORLD_MIN_Y) {
            return WorldgenConstants.OVERWORLD_MIN_Y;
        }
        return vanillaMin;
    }
}
