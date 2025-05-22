//? if fabric && >1.20.1 {
package dev.worldgen.tectonic.loaders.fabric;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.tectonic.Tectonic;
import dev.worldgen.tectonic.config.ConfigHandler;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps;
import org.jetbrains.annotations.Nullable;

public record ConfigResourceCondition(String key) implements ResourceCondition {
    public static final MapCodec<ConfigResourceCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Codec.STRING.fieldOf("key").forGetter(ConfigResourceCondition::key)
    ).apply(instance, ConfigResourceCondition::new));
    public static final ResourceConditionType<ConfigResourceCondition> TYPE = ResourceConditionType.create(Tectonic.id("config"), CODEC);

    @Override
    public ResourceConditionType<?> getType() {
        return TYPE;
    }

    @Override
    public boolean test(@Nullable /*? >1.21.1 {*/RegistryOps.RegistryInfoLookup/*?} else {*//*HolderLookup.Provider*//*?}*/ registries) {
        return switch (this.key) {
            case "disable_islands" -> !ConfigHandler.getState().islands.enabled;
            case "increased_height" -> ConfigHandler.getState().globalTerrain.increasedHeight;
            case "remove_frozen_ocean_ice" -> ConfigHandler.getState().oceans.removeFrozenOceanIce;
            case "river_lanterns" -> ConfigHandler.getState().continents.riverLanterns;
            default -> false;
        };
    }
}
//?}