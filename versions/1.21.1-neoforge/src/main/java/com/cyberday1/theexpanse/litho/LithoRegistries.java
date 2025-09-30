package com.cyberday1.theexpanse.litho;

import com.cyberday1.theexpanse.litho.registry.LithoNeoforgeBiomeModifiers;
import com.cyberday1.theexpanse.litho.registry.LithoRegistryKeys;
import com.cyberday1.theexpanse.litho.worldgen.densityfunction.MergedDensityFunction;
import com.cyberday1.theexpanse.litho.worldgen.densityfunction.OriginalMarkerDensityFunction;
import com.cyberday1.theexpanse.litho.worldgen.densityfunction.WrappedMarkerDensityFunction;
import com.cyberday1.theexpanse.litho.worldgen.modifier.AddFeaturesModifier;
import com.cyberday1.theexpanse.litho.worldgen.modifier.Modifier;
import com.cyberday1.theexpanse.litho.worldgen.modifier.ReplaceClimateModifier;
import com.cyberday1.theexpanse.litho.worldgen.modifier.WrapDensityFunctionModifier;
import com.cyberday1.theexpanse.litho.worldgen.placementcondition.PlacementCondition;
import com.cyberday1.theexpanse.litho.worldgen.placementcondition.SampleDensityPlacementCondition;
import com.cyberday1.theexpanse.litho.worldgen.placementmodifier.ConditionPlacement;
import com.cyberday1.theexpanse.litho.worldgen.placementmodifier.NoiseSlopePlacement;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.BiConsumer;

public final class LithoRegistries {
    private static final DeferredRegister<MapCodec<? extends Modifier>> MODIFIER_TYPES = DeferredRegister.create(LithoRegistryKeys.MODIFIER_TYPE, LithoCommon.NAMESPACE);
    private static final DeferredRegister<MapCodec<? extends PlacementCondition>> PLACEMENT_CONDITION_TYPES = DeferredRegister.create(LithoRegistryKeys.PLACEMENT_CONDITION_TYPE, LithoCommon.NAMESPACE);
    private static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, LithoCommon.NAMESPACE);

    private LithoRegistries() {
    }

    public static void init(IEventBus bus) {
        bus.addListener(LithoRegistries::registerVanillaRegistries);
        bus.addListener((DataPackRegistryEvent.NewRegistry event) -> event.dataPackRegistry(LithoRegistryKeys.WORLDGEN_MODIFIER, Modifier.CODEC));

        registerModifiers((name, codec) -> MODIFIER_TYPES.register(name, () -> codec));
        registerPlacementConditions((name, codec) -> PLACEMENT_CONDITION_TYPES.register(name, () -> codec));
        registerBiomeModifiers((name, codec) -> BIOME_MODIFIER_TYPES.register(name, () -> codec));

        MODIFIER_TYPES.register(bus);
        PLACEMENT_CONDITION_TYPES.register(bus);
        BIOME_MODIFIER_TYPES.register(bus);
    }

    private static void registerModifiers(BiConsumer<String, MapCodec<? extends Modifier>> consumer) {
        consumer.accept("wrap_density_function", WrapDensityFunctionModifier.CODEC);
        consumer.accept("add_features", AddFeaturesModifier.CODEC);
        consumer.accept("replace_climate", ReplaceClimateModifier.CODEC);
    }

    private static void registerPlacementConditions(BiConsumer<String, MapCodec<? extends PlacementCondition>> consumer) {
        consumer.accept("sample_density", SampleDensityPlacementCondition.CODEC);
    }

    private static void registerBiomeModifiers(BiConsumer<String, MapCodec<? extends BiomeModifier>> consumer) {
        consumer.accept("replace_climate", LithoNeoforgeBiomeModifiers.ReplaceClimateBiomeModifier.CODEC);
    }

    private static void registerVanillaRegistries(RegisterEvent event) {
        event.register(Registries.PLACEMENT_MODIFIER_TYPE, helper -> {
            helper.register(createKey(Registries.PLACEMENT_MODIFIER_TYPE, "condition"), ConditionPlacement.TYPE);
            helper.register(createKey(Registries.PLACEMENT_MODIFIER_TYPE, "noise_slope"), NoiseSlopePlacement.TYPE);
        });

        event.register(Registries.DENSITY_FUNCTION_TYPE, helper -> {
            helper.register(createKey(Registries.DENSITY_FUNCTION_TYPE, "internal/merged"), MergedDensityFunction.CODEC.codec());
            helper.register(createKey(Registries.DENSITY_FUNCTION_TYPE, "wrapped_marker"), WrappedMarkerDensityFunction.CODEC.codec());
            helper.register(createKey(Registries.DENSITY_FUNCTION_TYPE, "original_marker"), OriginalMarkerDensityFunction.CODEC.codec());
        });
    }

    private static <T> ResourceKey<T> createKey(ResourceKey<? extends Registry<T>> registry, String name) {
        return LithoCommon.createResourceKey(registry, name);
    }
}
