//? if 1.20.1 {
/*package dev.worldgen.theexpanse.client.old.gui;

import dev.worldgen.theexpanse.config.ConfigHandler;
import dev.worldgen.theexpanse.config.state.ConfigState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import static dev.worldgen.theexpanse.config.state.ConfigState.Biomes.*;
import static dev.worldgen.theexpanse.config.state.ConfigState.Continents.*;
import static dev.worldgen.theexpanse.config.state.ConfigState.General.*;
import static dev.worldgen.theexpanse.config.state.ConfigState.GlobalTerrain.*;
import static dev.worldgen.theexpanse.config.state.ConfigState.Islands.*;
import static dev.worldgen.theexpanse.config.state.ConfigState.Oceans.*;

public class ConfigScreen extends Screen {
    private final Screen parent;

    private ConfigList list;

    public ConfigScreen(Screen parent) {
        super(text("title"));
        this.parent = parent;
    }

    @Override
    public void init() {
        ConfigState state = ConfigHandler.getState();

        list = new ConfigList(minecraft, this);

        list.addCategory("general", font);
        list.addBoolean("mod_enabled", bool -> state.general.modEnabled = bool, state.general.modEnabled, MOD_ENABLED);
        list.addInteger("snow_start_offset", 0, 256, value -> state.general.snowStartOffset = value, state.general.snowStartOffset, SNOW_START_OFFSET);

        list.addCategory("global_terrain", font);
        list.addDouble("vertical_scale", 0.75, 4, 0.005, value -> state.globalTerrain.verticalScale = value, state.globalTerrain.verticalScale, VERTICAL_SCALE);
        list.addBoolean("increased_height", bool -> state.globalTerrain.increasedHeight = bool, state.globalTerrain.increasedHeight, INCREASED_HEIGHT);
        list.addBoolean("lava_tunnels", bool -> state.globalTerrain.lavaTunnels = bool, state.globalTerrain.lavaTunnels, LAVA_TUNNELS);

        list.addCategory("continents", font);
        list.addDouble("ocean_offset", -1, 0, 0.01, value -> state.continents.oceanOffset = value, state.continents.oceanOffset, OCEAN_OFFSET);
        list.addDouble("continents_scale", 0.01, 1, 0.01, value -> state.continents.continentsScale = value, state.continents.continentsScale, CONTINENTS_SCALE);
        list.addDouble("erosion_scale", 0.01, 1, 0.01, value -> state.continents.erosionScale = value, state.continents.erosionScale, EROSION_SCALE);
        list.addDouble("ridge_scale", 0.01, 2, 0.01, value -> state.continents.ridgeScale = value, state.continents.ridgeScale, RIDGE_SCALE);
        list.addBoolean("underground_rivers", bool -> state.continents.undergroundRivers = bool, state.continents.undergroundRivers, UNDERGROUND_RIVERS);
        list.addBoolean("river_lanterns", bool -> state.continents.riverLanterns = bool, state.continents.riverLanterns, RIVER_LANTERNS);
        list.addDouble("flat_terrain_skew", -1, 1, 0.01, value -> state.continents.flatTerrainSkew = value, state.continents.flatTerrainSkew, FLAT_TERRAIN_SKEW);
        list.addBoolean("rolling_hills", bool -> state.continents.rollingHills = bool, state.continents.rollingHills, ROLLING_HILLS);
        list.addBoolean("jungle_pillars", bool -> state.continents.junglePillars = bool, state.continents.junglePillars, JUNGLE_PILLARS);

        list.addCategory("islands", font);
        list.addBoolean("enabled", bool -> state.islands.enabled = bool,  state.islands.enabled, ENABLED);
        list.addDouble("noise_scale", 0.01, 0.5, 0.01, value -> state.islands.noiseScale = value,  state.islands.noiseScale, NOISE_SCALE);

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

        this.addWidget(list);
        this.addRenderableWidget(Button.builder(
            CommonComponents.GUI_DONE,
            button -> this.onDone()
        ).pos(width / 2 - 100, height - 28).size(200, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        list.render(guiGraphics, mouseX, mouseY, delta);

        guiGraphics.drawCenteredString(this.font, title, width / 2, 12, 0xffffff);
    }

    @Override
    public void renderBackground(GuiGraphics context) {
        this.renderDirtBackground(context);
    }

    private void onDone() {
        ConfigHandler.save();
        this.onClose();
    }

    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }

    public static Component text(String name) {
        return Component.translatable("config.theexpanse." + name);
    }

    public static Component option(String name) {
        return text("option." + name);
    }
}
*///?}