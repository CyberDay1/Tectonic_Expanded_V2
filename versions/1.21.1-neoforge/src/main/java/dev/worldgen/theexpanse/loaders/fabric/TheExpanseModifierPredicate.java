//? if 1.20.1 {
/*package dev.worldgen.theexpanse.loaders.fabric;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.lithostitched.worldgen.modifier.predicate.ModifierPredicate;
import dev.worldgen.theexpanse.config.ConfigHandler;

public record TheExpanseModifierPredicate(String key) implements ModifierPredicate {
    public static final Codec<TheExpanseModifierPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("key").forGetter(TheExpanseModifierPredicate::key)
    ).apply(instance, TheExpanseModifierPredicate::new));

    @Override
    public boolean test() {
        return switch (this.key) {
            case "disable_islands" -> !ConfigHandler.getState().islands.enabled;
            case "increased_height" -> ConfigHandler.getState().globalTerrain.increasedHeight;
            case "remove_frozen_ocean_ice" -> ConfigHandler.getState().oceans.removeFrozenOceanIce;
            case "river_lanterns" -> ConfigHandler.getState().continents.riverLanterns;
            default -> false;
        };
    }

    @Override
    public Codec<? extends ModifierPredicate> codec() {
        return CODEC;
    }
}
*///?}
