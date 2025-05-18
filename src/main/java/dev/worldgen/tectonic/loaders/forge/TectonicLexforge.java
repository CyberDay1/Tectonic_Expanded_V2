//? if forge {
package dev.worldgen.tectonic.loaders.forge;

import dev.worldgen.tectonic.Tectonic;
import dev.worldgen.tectonic.client.old.gui.ConfigScreen;
import dev.worldgen.tectonic.config.ConfigHandler;
import dev.worldgen.tectonic.worldgen.densityfunction.ConfigConstant;
import dev.worldgen.tectonic.worldgen.densityfunction.ConfigNoise;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.RegisterEvent;

import java.nio.file.Path;

import static dev.worldgen.tectonic.Tectonic.id;

@Mod("tectonic")
public class TectonicLexforge {
    public TectonicLexforge() {
        Tectonic.init(FMLPaths.CONFIGDIR.get());

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerDensityFunctionTypes);
        modEventBus.addListener(this::registerEnabledPacks);
    }

    private void registerDensityFunctionTypes(final RegisterEvent event) {
        event.register(Registries.DENSITY_FUNCTION_TYPE, helper -> {
            helper.register(id("config_constant"), ConfigConstant.CODEC_HOLDER.codec());
            helper.register(id("config_noise"), ConfigNoise.CODEC_HOLDER.codec());
        });
    }

    private void registerEnabledPacks(final AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && ConfigHandler.getState().general.modEnabled) {
            boolean terralith = ModList.get().isLoaded("terralith");
            boolean increasedHeight = ConfigHandler.getState().globalTerrain.increasedHeight;
            addPack(event, "tectonic", "aa");
            addPack(event, "tectonic/overlay.mod", "ab");
            if (terralith) addPack(event, "tectonic/overlay.terratonic", "ac");
            if (increasedHeight) addPack(event, "tectonic/overlay.increased_height" + (terralith ? "_terratonic" : ""), "ad");
        }
    }

    private void addPack(final AddPackFindersEvent event, String packName, String prefix) {
        Path resourcePath = ModList.get().getModFileById("tectonic").getFile().findResource("resourcepacks/" + packName.toLowerCase());
        Pack dataPack = Pack.readMetaAndCreate(
            "tectonic/" + prefix + packName.toLowerCase(),
            Component.translatable("pack_name.tectonic."+packName),
            false,
            string -> new PathPackResources(resourcePath.getFileName().toString(), resourcePath, false),
            PackType.SERVER_DATA,
            Pack.Position.TOP,
            PackSource.BUILT_IN
        );
        event.addRepositorySource((packConsumer) -> packConsumer.accept(dataPack));
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {


        @SubscribeEvent
        public static void onLoadComplete(FMLLoadCompleteEvent event) {
            ModContainer container = ModList.get().getModContainerById(Tectonic.MOD_ID).orElseThrow(() -> new IllegalStateException("Create mod container missing on LoadComplete"));
            container.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> new ConfigScreen(parent))
            );
        }
    }
}
//?}
