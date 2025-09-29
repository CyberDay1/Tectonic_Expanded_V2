package dev.worldgen.theexpanse.mixin;

import dev.worldgen.theexpanse.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Biome.class)
public abstract class BiomeMixin {
    @ModifyVariable(
        method = "getHeightAdjustedTemperature",
        at = @At("HEAD"),
        ordinal = 0,
        argsOnly = true
    )
    private BlockPos theexpanse$adjustSnowStart(BlockPos pos) {
        return pos.below(ConfigHandler.getState().general.snowStartOffset);
    }
}
