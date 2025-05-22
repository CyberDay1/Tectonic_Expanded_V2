//? if fabric {
package dev.worldgen.tectonic.loaders.fabric;

import dev.worldgen.lithostitched.registry.LithostitchedBuiltInRegistries;
import dev.worldgen.tectonic.Tectonic;
import dev.worldgen.tectonic.config.ConfigHandler;
//? if 1.20.1 {
//?}
import dev.worldgen.tectonic.worldgen.densityfunction.ConfigConstant;
import dev.worldgen.tectonic.worldgen.densityfunction.ConfigNoise;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static dev.worldgen.tectonic.Tectonic.id;

public class TectonicFabric implements ModInitializer {
    /**
     * Used in 1.20 for {@code TectonicRepositorySource} bullshit.
     */
    public static final List<Pack> PACKS = new ArrayList<>();

    @Override
    public void onInitialize() {
        Tectonic.init(FabricLoader.getInstance().getConfigDir());

        //? if >1.20.1 {
        ResourceConditions.register(ConfigResourceCondition.TYPE);
        //?}

        Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE, id("config_constant"), ConfigConstant.CODEC_HOLDER.codec());
        Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE, id("config_noise"), ConfigNoise.CODEC_HOLDER.codec());

        //? if >1.20.1 {
        if (ConfigHandler.getState().general.modEnabled) {
            ResourceManagerHelper.registerBuiltinResourcePack(
                id("tectonic"),
                FabricLoader.getInstance().getModContainer("tectonic").get(),
                Component.literal("Tectonic"),
                ResourcePackActivationType.ALWAYS_ENABLED
            );
        }
        //?} else {
        /*Registry.register(LithostitchedBuiltInRegistries.MODIFIER_PREDICATE_TYPE, id("config"), TectonicModifierPredicate.CODEC);
        if (ConfigHandler.getState().general.modEnabled) {
            ResourceManagerHelper.registerBuiltinResourcePack(
                id("tectonic"),
                FabricLoader.getInstance().getModContainer("tectonic").get(),
                Component.literal("Tectonic"),
                ResourcePackActivationType.ALWAYS_ENABLED
            );

            // Loads the pack overlays as separate packs

            boolean terralith = FabricLoader.getInstance().isModLoaded("terralith");
            boolean increasedHeight = ConfigHandler.getState().globalTerrain.increasedHeight;
            addPack("tectonic/overlay.mod");
            if (terralith) addPack("tectonic/overlay.terratonic");
            if (increasedHeight) addPack("tectonic/overlay.increased_height" + (terralith ? "_terratonic" : ""));
        }
        *///?}
    }

    //? if 1.20.1 {
    /*private static void addPack(String packName) {
        Path resourcePath = FabricLoader.getInstance().getModContainer("tectonic").get().findPath("resourcepacks/"+packName).get();
        PACKS.add(Pack.readMetaAndCreate(
            "tectonic/" + packName.toLowerCase(),
            Component.translatable("pack_name.tectonic."+packName),
            false,
            string -> new PathPackResources(resourcePath.getFileName().toString(), resourcePath, false),
            PackType.SERVER_DATA,
            Pack.Position.TOP,
            PackSource.BUILT_IN
        ));
    }
    *///?}
}
//?}
