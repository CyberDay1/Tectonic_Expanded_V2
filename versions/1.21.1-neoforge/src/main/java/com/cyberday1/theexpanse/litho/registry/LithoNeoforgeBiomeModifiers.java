package com.cyberday1.theexpanse.litho.registry;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ClimateSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public final class LithoNeoforgeBiomeModifiers {
    private LithoNeoforgeBiomeModifiers() {
    }

    public record ReplaceClimateBiomeModifier(HolderSet<Biome> biomes, Biome.ClimateSettings climateSettings) implements BiomeModifier {
        public static final MapCodec<ReplaceClimateBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(ReplaceClimateBiomeModifier::biomes),
            Biome.ClimateSettings.CODEC.fieldOf("climate").forGetter(ReplaceClimateBiomeModifier::climateSettings)
        ).apply(builder, ReplaceClimateBiomeModifier::new));

        @Override
        public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.MODIFY && this.biomes.contains(biome)) {
                ClimateSettingsBuilder climateSettings = builder.getClimateSettings();
                climateSettings.setTemperature(this.climateSettings.temperature());
                climateSettings.setDownfall(this.climateSettings.downfall());
                climateSettings.setHasPrecipitation(this.climateSettings.hasPrecipitation());
                climateSettings.setTemperatureModifier(this.climateSettings.temperatureModifier());
            }
        }

        @Override
        public MapCodec<? extends BiomeModifier> codec() {
            return CODEC;
        }
    }
}
