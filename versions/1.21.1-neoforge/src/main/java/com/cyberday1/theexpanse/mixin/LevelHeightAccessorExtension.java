package com.cyberday1.theexpanse.mixin;

/**
 * Bridge interface that exposes the NeoForge 1.21.1 height accessors while compiling against
 * the existing LevelHeightAccessor type. Implemented via mixin to keep call sites using the
 * new {@code getMinY}/{@code getMaxY} contract without introducing reflection.
 */
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LevelHeightAccessor;

public interface LevelHeightAccessorExtension {
    int getMinY();

    int getMaxY();

    /**
     * Mirrors the NeoForge 1.21.x {@code ChunkAccess#getMaxSectionY()} API for versions that
     * still compile against the legacy {@code getHighestSectionPosition()} method.
     */
    default int getMaxSectionY() {
        return SectionPos.sectionToBlockCoord(((LevelHeightAccessor) this).getMaxSection());
    }
}
