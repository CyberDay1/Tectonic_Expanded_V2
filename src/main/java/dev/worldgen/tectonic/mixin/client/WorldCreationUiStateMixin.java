package dev.worldgen.tectonic.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.worldgen.tectonic.Tectonic;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(WorldCreationUiState.class)
public class WorldCreationUiStateMixin {
    @Unique private static final TagKey<WorldPreset> INCOMPATIBLE = TagKey.create(Registries.WORLD_PRESET, Tectonic.id("incompatible"));

    @ModifyReturnValue(
        method = "getNonEmptyList",
        at = @At("RETURN")
    )
    private static Optional<List<WorldCreationUiState.WorldTypeEntry>> tectonic$removeUnusablePresets(Optional<List<WorldCreationUiState.WorldTypeEntry>> original) {
        if (original.isPresent()) {
            var list = new ArrayList<>(original.get());
            list.removeIf(entry -> entry.preset() != null && entry.preset().is(INCOMPATIBLE));
            return Optional.of(list);
        }
        return original;
    }
}
