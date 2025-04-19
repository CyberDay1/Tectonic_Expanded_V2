package dev.worldgen.tectonic.client.gui;

import dev.worldgen.tectonic.config.ConfigHandler;
import dev.worldgen.tectonic.config.state.ConfigState;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

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
        list.addBoolean("mod_enabled", state.general.modEnabled, bool -> state.general.modEnabled = bool, false);
        list.addBoolean("hide_beta_warning", state.general.hideBetaWarning, bool -> state.general.hideBetaWarning = bool, false);
        list.addInteger("snow_start_offset", 0, 256, value -> state.general.snowStartOffset = value, state.general.snowStartOffset, true);

        list.addCategory("global_terrain", font);
        list.addDouble("vertical_scale", 0.75, 4, 0.005, value -> state.globalTerrain.verticalScale = value, state.globalTerrain.verticalScale, false);
        list.addBoolean("increased_height", state.globalTerrain.increasedHeight, bool -> state.globalTerrain.increasedHeight = bool, true);
        //list.addBoolean("lava_rivers", state.globalTerrain.lavaRivers, bool -> state.globalTerrain.lavaRivers = bool, true);

        list.addCategory("continents", font);
        list.addDouble("ocean_offset", -1, 0, 0.01, value -> state.continents.oceanOffset = value, state.continents.oceanOffset, true);
        list.addDouble("continents_scale", 0.01, 1, 0.01, value -> state.continents.continentsScale = value, state.continents.continentsScale, true);
        list.addDouble("erosion_scale", 0.01, 1, 0.01, value -> state.continents.erosionScale = value, state.continents.erosionScale, true);
        list.addBoolean("underground_rivers", state.continents.undergroundRivers, bool -> state.continents.undergroundRivers = bool, false);
        list.addDouble("flat_terrain_skew", -1, 1, 0.01, value -> state.continents.flatTerrainSkew = value, state.continents.flatTerrainSkew, true);

        list.addCategory("islands", font);
        list.addBoolean("islands_enabled", state.islands.enabled, bool -> state.islands.enabled = bool, false);

        list.addCategory("oceans", font);
        //list.addBoolean("use_vanilla_shaping", state.oceans.useVanillaShaping, bool -> state.oceans.useVanillaShaping = bool, true);
        list.addDouble("ocean_depth", -0.75, -0.05, 0.01, value -> state.oceans.oceanDepth = value, state.oceans.oceanDepth, true);
        list.addDouble("deep_ocean_depth", -0.75, -0.05, 0.01, value -> state.oceans.deepOceanDepth = value, state.oceans.deepOceanDepth, true);
        list.addInteger("monument_offset", -60, 0, value -> state.oceans.monumentOffset = value, state.oceans.monumentOffset, true);
        list.addBoolean("remove_frozen_ocean_ice", state.oceans.removeFrozenOceanIce, bool -> state.oceans.removeFrozenOceanIce = bool, true);

        list.addCategory("biomes", font);
        list.addDouble("temperature_multiplier", 0.1, 5, 0.1, value -> state.biomes.temperatureMultiplier = value, state.biomes.temperatureMultiplier, false);
        list.addDouble("temperature_scale", 0.01, 1, 0.01, value -> state.biomes.temperatureScale = value, state.biomes.temperatureScale, false);
        list.addDouble("vegetation_multiplier", 0.1, 5, 0.1, value -> state.biomes.vegetationMultiplier = value, state.biomes.vegetationMultiplier, false);
        list.addDouble("vegetation_scale", 0.01, 1, 0.01, value -> state.biomes.vegetationScale = value, state.biomes.vegetationScale, false);


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
