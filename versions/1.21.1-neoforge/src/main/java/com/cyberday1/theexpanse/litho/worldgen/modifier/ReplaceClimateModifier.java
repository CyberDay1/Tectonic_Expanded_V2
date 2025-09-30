package com.cyberday1.theexpanse.litho.worldgen.modifier;

import com.cyberday1.theexpanse.litho.registry.LithoNeoforgeBiomeModifiers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;

public final class ReplaceClimateModifier extends AbstractBiomeModifier {
    public static final MapCodec<ReplaceClimateModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        Biome.LIST_CODEC.fieldOf("biomes").forGetter(ReplaceClimateModifier::biomes),
        Biome.ClimateSettings.CODEC.fieldOf("climate").forGetter(ReplaceClimateModifier::climateSettings)
    ).apply(instance, ReplaceClimateModifier::new));

    private final HolderSet<Biome> biomes;
    private final Biome.ClimateSettings climateSettings;

    public ReplaceClimateModifier(HolderSet<Biome> biomes, Biome.ClimateSettings climateSettings) {
        super(new LithoNeoforgeBiomeModifiers.ReplaceClimateBiomeModifier(biomes, climateSettings));
        this.biomes = biomes;
        this.climateSettings = climateSettings;
    }

    public HolderSet<Biome> biomes() {
        return this.biomes;
    }

    public Biome.ClimateSettings climateSettings() {
        return this.climateSettings;
    }

    @Override
    public MapCodec<? extends Modifier> codec() {
        return CODEC;
    }
}
