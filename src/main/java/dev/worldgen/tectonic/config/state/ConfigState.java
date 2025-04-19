package dev.worldgen.tectonic.config.state;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ConfigState {
    public static final Codec<ConfigState> BASE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
        General.CODEC.fieldOf("general").forGetter(state -> state.general),
        GlobalTerrain.CODEC.fieldOf("global_terrain").orElse(GlobalTerrain.DEFAULT).forGetter(state -> state.globalTerrain),
        Continents.CODEC.fieldOf("continents").orElse(Continents.DEFAULT).forGetter(state -> state.continents),
        Islands.CODEC.fieldOf("islands").orElse(Islands.DEFAULT).forGetter(state -> state.islands),
        Oceans.CODEC.fieldOf("oceans").orElse(Oceans.DEFAULT).forGetter(state -> state.oceans),
        Biomes.CODEC.fieldOf("biomes").orElse(Biomes.DEFAULT).forGetter(state -> state.biomes)
    ).apply(instance, ConfigState::new));
    public static final Codec<ConfigState> CODEC = Codec.withAlternative(BASE_CODEC, V2ConfigState.CODEC, V2ConfigState::upgrade);

    public General general;
    public GlobalTerrain globalTerrain;
    public Continents continents;
    public Islands islands;
    public Oceans oceans;
    public Biomes biomes;

    public ConfigState(General general, GlobalTerrain globalTerrain, Continents continents, Islands islands, Oceans oceans, Biomes biomes) {
        this.general = general;
        this.globalTerrain = globalTerrain;
        this.continents = continents;
        this.islands = islands;
        this.oceans = oceans;
        this.biomes = biomes;
    }

    public double getValue(String option) {
        return switch (option) {
            case "vertical_scale" -> this.globalTerrain.verticalScale;
            case "lava_rivers" -> this.globalTerrain.lavaRivers ? 1 : 0;

            case "ocean_offset" -> this.continents.oceanOffset;
            case "continents_scale" -> this.continents.continentsScale;
            case "erosion_scale" -> this.continents.erosionScale;
            case "underground_rivers" -> this.continents.undergroundRivers ? -1 : 0;
            case "flat_terrain_skew" -> this.continents.flatTerrainSkew;

            case "ocean_depth" -> this.oceans.oceanDepth;
            case "deep_ocean_depth" -> this.oceans.deepOceanDepth;

            case "temperature_multiplier" -> this.biomes.temperatureMultiplier;
            case "temperature_scale" -> this.biomes.temperatureScale;
            case "vegetation_multiplier" -> this.biomes.vegetationMultiplier;
            case "vegetation_scale" -> this.biomes.vegetationScale;

            default -> 0;
        };
    }


    public static class General {
        public static final Codec<General> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("mod_enabled").forGetter(general -> general.modEnabled),
            Codec.BOOL.fieldOf("hide_beta_warning").orElse(false).forGetter(general -> general.hideBetaWarning),
            Codec.INT.fieldOf("snow_start_offset").orElse(128).forGetter(general -> general.snowStartOffset)
        ).apply(instance, General::new));
        public static final General DEFAULT = new General(true, false, 128);

        public boolean modEnabled;
        public boolean hideBetaWarning;
        public int snowStartOffset;

        public General(boolean modEnabled, boolean hideBetaWarning, int snowStartOffset) {
            this.modEnabled = modEnabled;
            this.hideBetaWarning = hideBetaWarning;
            this.snowStartOffset = snowStartOffset;
        }
    }

    public static class GlobalTerrain {
        public static final Codec<GlobalTerrain> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("vertical_scale").orElse(1.125).forGetter(globalTerrain -> globalTerrain.verticalScale),
            Codec.BOOL.fieldOf("increased_height").orElse(false).forGetter(globalTerrain -> globalTerrain.increasedHeight),
            Codec.BOOL.fieldOf("lava_rivers").orElse(true).forGetter(globalTerrain -> globalTerrain.lavaRivers)
        ).apply(instance, GlobalTerrain::new));
        public static final GlobalTerrain DEFAULT = new GlobalTerrain(1.125, false, true);

        public double verticalScale;
        public boolean increasedHeight;
        public boolean lavaRivers;

        public GlobalTerrain(double verticalScale, boolean increasedHeight, boolean lavaRivers) {
            this.verticalScale = verticalScale;
            this.increasedHeight = increasedHeight;
            this.lavaRivers = lavaRivers;
        }
    }

    public static class Continents {
        public static final Codec<Continents> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("ocean_offset").orElse(-0.8).forGetter(continents -> continents.oceanOffset),
            Codec.DOUBLE.fieldOf("continents_scale").orElse(0.13).forGetter(continents -> continents.continentsScale),
            Codec.DOUBLE.fieldOf("erosion_scale").orElse(0.25).forGetter(continents -> continents.erosionScale),
            Codec.BOOL.fieldOf("underground_rivers").orElse(true).forGetter(continents -> continents.undergroundRivers),
            Codec.DOUBLE.fieldOf("flat_terrain_skew").orElse(0.1).forGetter(continents -> continents.flatTerrainSkew)
        ).apply(instance, Continents::new));
        public static final Continents DEFAULT = new Continents(-0.8, 0.13, 0.25, true, 0.1);

        public double oceanOffset;
        public double continentsScale;
        public double erosionScale;
        public boolean undergroundRivers;
        public double flatTerrainSkew;

        public Continents(double oceanOffset, double continentsScale, double erosionScale, boolean undergroundRivers, double flatTerrainSkew) {
            this.oceanOffset = oceanOffset;
            this.continentsScale = continentsScale;
            this.erosionScale = erosionScale;
            this.undergroundRivers = undergroundRivers;
            this.flatTerrainSkew = flatTerrainSkew;
        }
    }

    public static class Islands {
        public static final Codec<Islands> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("enabled").orElse(true).forGetter(islands -> islands.enabled)
        ).apply(instance, Islands::new));
        public static final Islands DEFAULT = new Islands(true);

        public boolean enabled;

        public Islands(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class Oceans {
        public static final Codec<Oceans> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("use_vanilla_shaping").orElse(false).forGetter(oceans -> oceans.useVanillaShaping),
            Codec.DOUBLE.fieldOf("ocean_depth").orElse(-0.22).forGetter(oceans -> oceans.oceanDepth),
            Codec.DOUBLE.fieldOf("deep_ocean_depth").orElse(-0.45).forGetter(oceans -> oceans.deepOceanDepth),
            Codec.INT.fieldOf("monument_offset").orElse(-30).forGetter(oceans -> oceans.monumentOffset),
            Codec.BOOL.fieldOf("remove_frozen_ocean_ice").orElse(false).forGetter(oceans -> oceans.removeFrozenOceanIce)
        ).apply(instance, Oceans::new));
        public static final Oceans DEFAULT = new Oceans(false, -0.22, -0.45, -30, false);

        public boolean useVanillaShaping;
        public double oceanDepth;
        public double deepOceanDepth;
        public int monumentOffset;
        public boolean removeFrozenOceanIce;

        public Oceans(boolean useVanillaShaping, double oceanDepth, double deepOceanDepth, int monumentOffset, boolean removeFrozenOceanIce) {
            this.useVanillaShaping = useVanillaShaping;
            this.oceanDepth = oceanDepth;
            this.deepOceanDepth = deepOceanDepth;
            this.monumentOffset = monumentOffset;
            this.removeFrozenOceanIce = removeFrozenOceanIce;
        }
    }

    public static class Biomes {
        public static final Codec<Biomes> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("temperature_multiplier").orElse(1.0).forGetter(biomes -> biomes.temperatureMultiplier),
            Codec.DOUBLE.fieldOf("temperature_scale").orElse(0.25).forGetter(biomes -> biomes.temperatureScale),
            Codec.DOUBLE.fieldOf("vegetation_multiplier").orElse(1.0).forGetter(biomes -> biomes.vegetationMultiplier),
            Codec.DOUBLE.fieldOf("vegetation_scale").orElse(0.25).forGetter(biomes -> biomes.vegetationScale)
        ).apply(instance, Biomes::new));
        public static final Biomes DEFAULT = new Biomes(1, 0.25, 1, 0.25);

        public double temperatureMultiplier;
        public double temperatureScale;
        public double vegetationMultiplier;
        public double vegetationScale;

        public Biomes(double temperatureMultiplier, double temperatureScale, double vegetationMultiplier, double vegetationScale) {
            this.temperatureMultiplier = temperatureMultiplier;
            this.temperatureScale = temperatureScale;
            this.vegetationMultiplier = vegetationMultiplier;
            this.vegetationScale = vegetationScale;
        }
    }
}
