package dev.worldgen.tectonic.mixin;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldCarver.class)
public class WorldCarverMixin {
    @Inject(method = "canReplaceBlock", at = @At("HEAD"), cancellable = true)
    private <C extends CarverConfiguration> void init(C config, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(BlockTags.DIRT)) {
            cir.setReturnValue(false);
        }
    }
}
