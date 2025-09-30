//? if neoforge {
/*package dev.worldgen.theexpanse.loaders.neoforge;

import com.cyberday1.theexpanse.litho.LithoRegistries;
import com.mojang.serialization.MapCodec;
import dev.worldgen.theexpanse.TheExpanse;
import dev.worldgen.theexpanse.config.ConfigHandler;
import dev.worldgen.theexpanse.worldgen.densityfunction.ConfigConstant;
import dev.worldgen.theexpanse.worldgen.densityfunction.ConfigNoise;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.nio.file.Path;
import java.util.Optional;

import static dev.worldgen.theexpanse.TheExpanse.id;

@Mod(TheExpanse.MOD_ID)
public class TheExpanseNeoforge {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, TheExpanse.MOD_ID);
    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ConfigResourceCondition>> THE_EXPANSE = CONDITION_TYPES.register("config", () -> ConfigResourceCondition.CODEC);

    public TheExpanseNeoforge(IEventBus bus) {
        TheExpanse.init(FMLPaths.CONFIGDIR.get());

        LithoRegistries.init(bus);
        CONDITION_TYPES.register(bus);

        bus.addListener(this::registerDensityFunctionTypes);
        bus.addListener(this::registerEnabledPacks);
    }

    private void registerDensityFunctionTypes(final RegisterEvent event) {
        event.register(Registries.DENSITY_FUNCTION_TYPE, helper -> {
            helper.register(id("config_constant"), ConfigConstant.CODEC_HOLDER.codec());
            helper.register(id("config_noise"), ConfigNoise.CODEC_HOLDER.codec());
        });
    }

    private void registerEnabledPacks(final AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA && ConfigHandler.getState().general.modEnabled) {
            Path resourcePath = ModList.get().getModFileById("theexpanse").getFile().findResource("resourcepacks/theexpanse");

            Pack dataPack = Pack.readMetaAndCreate(
                new PackLocationInfo(
                    resourcePath.getFileName().toString(),
                    Component.literal("The Expanse"),
                    PackSource.BUILT_IN,
                    Optional.empty()
                ),
                new PathPackResources.PathResourcesSupplier(resourcePath),
                PackType.SERVER_DATA,
                new PackSelectionConfig(
                    true,
                    Pack.Position.TOP,
                    false
                )
            );

            event.addRepositorySource((packConsumer) -> packConsumer.accept(dataPack));
        }
    }
}
*///?}
