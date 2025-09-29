package dev.worldgen.theexpanse.config.state;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.theexpanse.TheExpanse;

public class ConfigState {
    public static final Codec<ConfigState> BASE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
        General.CODEC.fieldOf("general").forGetter(state -> state.general),
        GlobalTerrain.CODEC.fieldOf("global_terrain").orElse(GlobalTerrain.DEFAULT).forGetter(state -> state.globalTerrain),
        Continents.CODEC.fieldOf("continents").orElse(Continents.DEFAULT).forGetter(state -> state.continents),
        Islands.CODEC.fieldOf("islands").orElse(Islands.DEFAULT).forGetter(state -> state.islands),
        Oceans.CODEC.fieldOf("oceans").orElse(Oceans.DEFAULT).forGetter(state -> state.oceans),
        Biomes.CODEC.fieldOf("biomes").orElse(Biomes.DEFAULT).forGetter(state -> state.biomes)
    ).apply(instance, ConfigState::new));
    public static final Codec<ConfigState> CODEC = TheExpanse.withAlternative(BASE_CODEC, V2ConfigState.CODEC, V2ConfigState::upgrade);

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
            case "lava_tunnels" -> this.globalTerrain.lavaTunnels ? 1 : 0;

            case "ocean_offset" -> this.continents.oceanOffset;
            case "continents_scale" -> this.continents.continentsScale;
            case "erosion_scale" -> this.continents.erosionScale;
            case "ridge_scale" -> this.continents.ridgeScale;
            case "underground_rivers" -> this.continents.undergroundRivers ? -1 : 0;
            case "flat_terrain_skew" -> this.continents.flatTerrainSkew;
            case "rolling_hills" -> this.continents.rollingHills ? 1 : 0;
            case "jungle_pillars" -> this.continents.junglePillars ? 1 : 0;

            case "ocean_depth" -> this.oceans.oceanDepth;
            case "deep_ocean_depth" -> this.oceans.deepOceanDepth;

            case "islands_scale" -> this.islands.noiseScale;

            case "temperature_multiplier" -> this.biomes.temperatureMultiplier;
            case "temperature_scale" -> this.biomes.temperatureScale;
            case "vegetation_multiplier" -> this.biomes.vegetationMultiplier;
            case "vegetation_scale" -> this.biomes.vegetationScale;

            default -> 0;
        };
    }


    public static class General {
        public static final boolean MOD_ENABLED = true;
        public static final int SNOW_START_OFFSET = 128;

        public static final General DEFAULT = new General(MOD_ENABLED, SNOW_START_OFFSET);
        public static final Codec<General> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("mod_enabled").forGetter(general -> general.modEnabled),
            Codec.INT.fieldOf("snow_start_offset").orElse(SNOW_START_OFFSET).forGetter(general -> general.snowStartOffset)
        ).apply(instance, General::new));

        public boolean modEnabled;
        public int snowStartOffset;

        public General(boolean modEnabled, int snowStartOffset) {
            this.modEnabled = modEnabled;
            this.snowStartOffset = snowStartOffset;
        }
    }

    public static class GlobalTerrain {
        public static final double VERTICAL_SCALE = 1.125;
        public static final boolean INCREASED_HEIGHT = false;
        public static final boolean LAVA_TUNNELS = true;

        public static final GlobalTerrain DEFAULT = new GlobalTerrain(VERTICAL_SCALE, INCREASED_HEIGHT, LAVA_TUNNELS);
        public static final Codec<GlobalTerrain> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("vertical_scale").orElse(VERTICAL_SCALE).forGetter(globalTerrain -> globalTerrain.verticalScale),
            Codec.BOOL.fieldOf("increased_height").orElse(INCREASED_HEIGHT).forGetter(globalTerrain -> globalTerrain.increasedHeight),
            Codec.BOOL.fieldOf("lava_tunnels").orElse(LAVA_TUNNELS).forGetter(globalTerrain -> globalTerrain.lavaTunnels)
        ).apply(instance, GlobalTerrain::new));

        public double verticalScale;
        public boolean increasedHeight;
        public boolean lavaTunnels;

        public GlobalTerrain(double verticalScale, boolean increasedHeight, boolean lavaTunnels) {
            this.verticalScale = verticalScale;
            this.increasedHeight = increasedHeight;
            this.lavaTunnels = lavaTunnels;
        }
    }

    public static class Continents {
        public static final double OCEAN_OFFSET = -0.8;
        public static final double CONTINENTS_SCALE = 0.13;
        public static final double EROSION_SCALE = 0.25;
        public static final double RIDGE_SCALE = 0.25;
        public static final boolean UNDERGROUND_RIVERS = true;
        public static final boolean RIVER_LANTERNS = true;
        public static final double FLAT_TERRAIN_SKEW = 0.1;
        public static final boolean ROLLING_HILLS = true;
        public static final boolean JUNGLE_PILLARS = true;

        public static final Continents DEFAULT = new Continents(OCEAN_OFFSET, CONTINENTS_SCALE, EROSION_SCALE, RIDGE_SCALE, UNDERGROUND_RIVERS, RIVER_LANTERNS, FLAT_TERRAIN_SKEW, ROLLING_HILLS, JUNGLE_PILLARS);
        public static final Codec<Continents> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("ocean_offset").orElse(OCEAN_OFFSET).forGetter(continents -> continents.oceanOffset),
            Codec.DOUBLE.fieldOf("continents_scale").orElse(CONTINENTS_SCALE).forGetter(continents -> continents.continentsScale),
            Codec.DOUBLE.fieldOf("erosion_scale").orElse(EROSION_SCALE).forGetter(continents -> continents.erosionScale),
            Codec.DOUBLE.fieldOf("ridge_scale").orElse(RIDGE_SCALE).forGetter(continents -> continents.ridgeScale),
            Codec.BOOL.fieldOf("underground_rivers").orElse(UNDERGROUND_RIVERS).forGetter(continents -> continents.undergroundRivers),
            Codec.BOOL.fieldOf("river_lanterns").orElse(RIVER_LANTERNS).forGetter(continents -> continents.riverLanterns),
            Codec.DOUBLE.fieldOf("flat_terrain_skew").orElse(FLAT_TERRAIN_SKEW).forGetter(continents -> continents.flatTerrainSkew),
            Codec.BOOL.fieldOf("rolling_hills").orElse(ROLLING_HILLS).forGetter(continents -> continents.rollingHills),
            Codec.BOOL.fieldOf("jungle_pillars").orElse(JUNGLE_PILLARS).forGetter(continents -> continents.junglePillars)
        ).apply(instance, Continents::new));

        public double oceanOffset;
        public double continentsScale;
        public double erosionScale;
        public double ridgeScale;
        public boolean undergroundRivers;
        public boolean riverLanterns;
        public double flatTerrainSkew;
        public boolean rollingHills;
        public boolean junglePillars;

        public Continents(double oceanOffset, double continentsScale, double erosionScale, double ridgeScale, boolean undergroundRivers, boolean riverLanterns, double flatTerrainSkew, boolean rollingHills, boolean junglePillars) {
            this.oceanOffset = oceanOffset;
            this.continentsScale = continentsScale;
            this.erosionScale = erosionScale;
            this.ridgeScale = ridgeScale;
            this.undergroundRivers = undergroundRivers;
            this.riverLanterns = riverLanterns;
            this.flatTerrainSkew = flatTerrainSkew;
            this.rollingHills = rollingHills;
            this.junglePillars = junglePillars;
        }
    }

    public static class Islands {
        public static final boolean ENABLED = true;
        public static final double NOISE_SCALE = 0.11;

        public static final Islands DEFAULT = new Islands(ENABLED, NOISE_SCALE);
        public static final Codec<Islands> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("enabled").orElse(ENABLED).forGetter(islands -> islands.enabled),
            Codec.DOUBLE.fieldOf("noise_scale").orElse(NOISE_SCALE).forGetter(islands -> islands.noiseScale)
        ).apply(instance, Islands::new));

        public boolean enabled;
        public double noiseScale;

        public Islands(boolean enabled, double noiseScale) {
            this.enabled = enabled;
            this.noiseScale = noiseScale;
        }
    }

    public static class Oceans {
        public static final double OCEAN_DEPTH = -0.22;
        public static final double DEEP_OCEAN_DEPTH = -0.45;
        public static final int MONUMENT_OFFSET = -30;
        public static final boolean REMOVE_FROZEN_OCEAN_ICE = false;

        public static final Oceans DEFAULT = new Oceans(OCEAN_DEPTH, DEEP_OCEAN_DEPTH, MONUMENT_OFFSET, REMOVE_FROZEN_OCEAN_ICE);
        public static final Codec<Oceans> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("ocean_depth").orElse(OCEAN_DEPTH).forGetter(oceans -> oceans.oceanDepth),
            Codec.DOUBLE.fieldOf("deep_ocean_depth").orElse(DEEP_OCEAN_DEPTH).forGetter(oceans -> oceans.deepOceanDepth),
            Codec.INT.fieldOf("monument_offset").orElse(MONUMENT_OFFSET).forGetter(oceans -> oceans.monumentOffset),
            Codec.BOOL.fieldOf("remove_frozen_ocean_ice").orElse(REMOVE_FROZEN_OCEAN_ICE).forGetter(oceans -> oceans.removeFrozenOceanIce)
        ).apply(instance, Oceans::new));

        public double oceanDepth;
        public double deepOceanDepth;
        public int monumentOffset;
        public boolean removeFrozenOceanIce;

        public Oceans(double oceanDepth, double deepOceanDepth, int monumentOffset, boolean removeFrozenOceanIce) {
            this.oceanDepth = oceanDepth;
            this.deepOceanDepth = deepOceanDepth;
            this.monumentOffset = monumentOffset;
            this.removeFrozenOceanIce = removeFrozenOceanIce;
        }
    }

    public static class Biomes {
        public static final double TEMPERATURE_MULTIPLIER = 1.0;
        public static final double TEMPERATURE_SCALE = 0.25;
        public static final double VEGETATION_MULTIPLIER = 1.0;
        public static final double VEGETATION_SCALE = 0.25;

        public static final Biomes DEFAULT = new Biomes(TEMPERATURE_MULTIPLIER, TEMPERATURE_SCALE, VEGETATION_MULTIPLIER, VEGETATION_SCALE);
        public static final Codec<Biomes> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("temperature_multiplier").orElse(TEMPERATURE_MULTIPLIER).forGetter(biomes -> biomes.temperatureMultiplier),
            Codec.DOUBLE.fieldOf("temperature_scale").orElse(TEMPERATURE_SCALE).forGetter(biomes -> biomes.temperatureScale),
            Codec.DOUBLE.fieldOf("vegetation_multiplier").orElse(VEGETATION_MULTIPLIER).forGetter(biomes -> biomes.vegetationMultiplier),
            Codec.DOUBLE.fieldOf("vegetation_scale").orElse(VEGETATION_SCALE).forGetter(biomes -> biomes.vegetationScale)
        ).apply(instance, Biomes::new));

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
