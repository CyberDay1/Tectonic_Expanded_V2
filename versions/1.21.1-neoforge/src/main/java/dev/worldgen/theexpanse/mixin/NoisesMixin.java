package dev.worldgen.theexpanse.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Noises;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Noises.class)
public abstract class NoisesMixin {

    @ModifyArg(
        method = "instantiate",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/levelgen/PositionalRandomFactory;fromHashOf(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/util/RandomSource;"
        )
    )
    private static ResourceLocation theexpanse$fixNoiseSeeds(ResourceLocation name) {
        if (name.getNamespace().equals("theexpanse")) {
            String path = name.getPath();
            if (path.startsWith("parameter/")) {
                //? if 1.20.1 {
                /*return new ResourceLocation(path.substring(10));
                *///?} else {
                return ResourceLocation.withDefaultNamespace(path.substring(10));
                 //?}
            }
        }
        return name;
    }
}
