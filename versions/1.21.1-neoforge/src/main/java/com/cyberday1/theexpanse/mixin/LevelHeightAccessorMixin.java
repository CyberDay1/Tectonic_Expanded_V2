package com.cyberday1.theexpanse.mixin;

import com.cyberday1.theexpanse.world.WorldgenConstants;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LevelHeightAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LevelHeightAccessor.class)
public interface LevelHeightAccessorMixin {
    // CUSTOM: extended vertical range (sectionpos)
    @Overwrite
    default int getMinSection() {
        int minBuildHeight = this.getMinY();
        if (minBuildHeight == WorldgenConstants.OVERWORLD_MIN_Y && this.getHeight() == WorldgenConstants.OVERWORLD_HEIGHT) {
            return SectionPos.blockToSectionCoord(WorldgenConstants.OVERWORLD_MIN_Y);
        }
        return SectionPos.blockToSectionCoord(minBuildHeight);
    }

    // CUSTOM: extended vertical range (sectionpos)
    @Overwrite
    default int getMaxSection() {
        if (this.getMinY() == WorldgenConstants.OVERWORLD_MIN_Y && this.getHeight() == WorldgenConstants.OVERWORLD_HEIGHT) {
            return SectionPos.blockToSectionCoord(WorldgenConstants.OVERWORLD_MAX_Y) + 1;
        }
        return SectionPos.blockToSectionCoord(this.getMaxY() - 1) + 1;
    }

    // CUSTOM: extended vertical range (sectionpos)
    @Overwrite
    default int getSectionsCount() {
        if (this.getMinY() == WorldgenConstants.OVERWORLD_MIN_Y && this.getHeight() == WorldgenConstants.OVERWORLD_HEIGHT) {
            return WorldgenConstants.OVERWORLD_SECTION_COUNT;
        }
        return this.getMaxSection() - this.getMinSection();
    }
}
