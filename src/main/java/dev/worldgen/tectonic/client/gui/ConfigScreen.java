package dev.worldgen.tectonic.client.gui;

import dev.worldgen.tectonic.config.ConfigHandler;
import dev.worldgen.tectonic.config.state.ConfigState;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import static dev.worldgen.tectonic.config.state.ConfigState.Biomes.*;
import static dev.worldgen.tectonic.config.state.ConfigState.Continents.*;
import static dev.worldgen.tectonic.config.state.ConfigState.General.*;
import static dev.worldgen.tectonic.config.state.ConfigState.GlobalTerrain.*;
import static dev.worldgen.tectonic.config.state.ConfigState.Oceans.*;

public class ConfigScreen extends Screen {
    private final Screen parent;

    private ConfigList list;
    final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    public ConfigScreen(Screen parent) {
        super(text("title"));
        this.parent = parent;
    }

    @Override
    public void init() {
        ConfigState state = ConfigHandler.getState();

        layout.addTitleHeader(title, font);

        list = layout.addToContents(new ConfigList(minecraft, width, this));

        list.addCategory("general", font);
        list.addBoolean("mod_enabled", bool -> state.general.modEnabled = bool, state.general.modEnabled, MOD_ENABLED);
        list.addBoolean("hide_beta_warning", bool -> state.general.hideBetaWarning = bool, state.general.hideBetaWarning, HIDE_BETA_WARNING);
        list.addInteger("snow_start_offset", 0, 256, value -> state.general.snowStartOffset = value, state.general.snowStartOffset, SNOW_START_OFFSET);

        list.addCategory("global_terrain", font);
        list.addDouble("vertical_scale", 0.75, 4, 0.005, value -> state.globalTerrain.verticalScale = value, state.globalTerrain.verticalScale, VERTICAL_SCALE);
        list.addBoolean("increased_height", bool -> state.globalTerrain.increasedHeight = bool, state.globalTerrain.increasedHeight, INCREASED_HEIGHT);
        //list.addBoolean("lava_rivers", bool -> state.globalTerrain.lavaRivers = bool, state.globalTerrain.lavaRivers, LAVA_RIVERS);

        list.addCategory("continents", font);
        list.addDouble("ocean_offset", -1, 0, 0.01, value -> state.continents.oceanOffset = value, state.continents.oceanOffset, OCEAN_OFFSET);
        list.addDouble("continents_scale", 0.01, 1, 0.01, value -> state.continents.continentsScale = value, state.continents.continentsScale, CONTINENTS_SCALE);
        list.addDouble("erosion_scale", 0.01, 1, 0.01, value -> state.continents.erosionScale = value, state.continents.erosionScale, EROSION_SCALE);
        list.addBoolean("underground_rivers", bool -> state.continents.undergroundRivers = bool, state.continents.undergroundRivers, UNDERGROUND_RIVERS);
        list.addBoolean("river_lanterns", bool -> state.continents.riverLanterns = bool, state.continents.riverLanterns, RIVER_LANTERNS);
        list.addDouble("flat_terrain_skew", -1, 1, 0.01, value -> state.continents.flatTerrainSkew = value, state.continents.flatTerrainSkew, FLAT_TERRAIN_SKEW);

        list.addCategory("islands", font);
        list.addBoolean("islands_enabled", bool -> state.islands.enabled = bool,  state.islands.enabled, true);

        list.addCategory("oceans", font);
        list.addDouble("ocean_depth", -0.75, -0.05, 0.01, value -> state.oceans.oceanDepth = value, state.oceans.oceanDepth, OCEAN_DEPTH);
        list.addDouble("deep_ocean_depth", -0.75, -0.05, 0.01, value -> state.oceans.deepOceanDepth = value, state.oceans.deepOceanDepth, DEEP_OCEAN_DEPTH);
        list.addInteger("monument_offset", -60, 0, value -> state.oceans.monumentOffset = value, state.oceans.monumentOffset, MONUMENT_OFFSET);
        list.addBoolean("remove_frozen_ocean_ice", bool -> state.oceans.removeFrozenOceanIce = bool, state.oceans.removeFrozenOceanIce, REMOVE_FROZEN_OCEAN_ICE);

        list.addCategory("biomes", font);
        list.addDouble("temperature_multiplier", 0.1, 5, 0.1, value -> state.biomes.temperatureMultiplier = value, state.biomes.temperatureMultiplier, TEMPERATURE_MULTIPLIER);
        list.addDouble("temperature_scale", 0.01, 1, 0.01, value -> state.biomes.temperatureScale = value, state.biomes.temperatureScale, TEMPERATURE_SCALE);
        list.addDouble("vegetation_multiplier", 0.1, 5, 0.1, value -> state.biomes.vegetationMultiplier = value, state.biomes.vegetationMultiplier, VEGETATION_MULTIPLIER);
        list.addDouble("vegetation_scale", 0.01, 1, 0.01, value -> state.biomes.vegetationScale = value, state.biomes.vegetationScale, VEGETATION_SCALE);


        LinearLayout footer = layout.addToFooter(LinearLayout.horizontal().spacing(8));

        footer.addChild(Button.builder(CommonComponents.GUI_DONE, button -> onDone()).build());

        layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
        if (this.list != null) {
            this.list.updateSize(this.width, this.layout);
        }
    }

    private void onDone() {
        ConfigHandler.save();
        this.onClose();
    }

    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }

    public static Component text(String name) {
        return Component.translatable("config.tectonic." + name);
    }

    public static Component option(String name) {
        return text("option." + name);
    }
}
