package com.cyberday1.theexpanse.litho.worldgen.modifier;

import com.cyberday1.theexpanse.litho.LithoCommon;
import com.cyberday1.theexpanse.litho.registry.LithoRegistryKeys;
import com.cyberday1.theexpanse.mixin.litho.access.ChunkGeneratorAccessor;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.dimension.LevelStem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public interface Modifier {
    @SuppressWarnings("unchecked")
    com.mojang.serialization.Codec<Modifier> CODEC = com.mojang.serialization.Codec.lazyInitialized(() -> {
        var modifierRegistry = BuiltInRegistries.REGISTRY.get(LithoRegistryKeys.MODIFIER_TYPE.location());
        if (modifierRegistry == null) throw new NullPointerException("Worldgen modifier registry does not exist yet!");
        return ((Registry<com.mojang.serialization.MapCodec<? extends Modifier>>) modifierRegistry).byNameCodec();
    }).dispatch(Modifier::codec, Function.identity());

    default void applyModifier(RegistryAccess registryAccess) {
        this.applyModifier();
    }

    void applyModifier();

    ModifierPhase getPhase();

    com.mojang.serialization.MapCodec<? extends Modifier> codec();

    static void applyModifiers(MinecraftServer server) {
        boolean updatedFeatures = false;
        RegistryAccess registries = server.registryAccess();
        HolderLookup.RegistryLookup<Modifier> modifiers = registries.lookupOrThrow(LithoRegistryKeys.WORLDGEN_MODIFIER);

        for (ModifierPhase phase : ModifierPhase.values()) {
            if (phase == ModifierPhase.NONE) continue;
            List<Holder.Reference<Modifier>> phaseModifiers = modifiers.listElements()
                .filter(holder -> holder.value().getPhase() == phase)
                .toList();
            applyPhaseModifiers(registries, phaseModifiers);

            if (phaseModifiers.stream().anyMatch(holder -> holder.value().internal$modifiesFabricFeatures())) {
                updatedFeatures = true;
            }
        }

        if (updatedFeatures) {
            Registry<LevelStem> dimensions = registries.registryOrThrow(Registries.LEVEL_STEM);
            for (LevelStem dimension : dimensions) {
                ChunkGeneratorAccessor accessor = (ChunkGeneratorAccessor) dimension.generator();
                BiomeSource source = accessor.getBiomeSource();
                accessor.setFeaturesPerStep(() -> FeatureSorter.buildFeaturesPerStep(List.copyOf(source.possibleBiomes()),
                    biome -> accessor.getGetter().apply(biome).features(), true));
            }
        }
    }

    private static void applyPhaseModifiers(RegistryAccess registries, List<Holder.Reference<Modifier>> phaseModifiers) {
        List<Holder.Reference<PriorityBasedModifier>> priorityBased = new ArrayList<>();

        for (Holder.Reference<Modifier> reference : phaseModifiers) {
            if (reference.value() instanceof PriorityBasedModifier priority) {
                priorityBased.add((Holder.Reference<PriorityBasedModifier>) (Object) reference);
            } else {
                LithoCommon.debug("Applying modifier with id: {}", reference.key().location());
                reference.value().applyModifier(registries);
            }
        }

        for (Holder.Reference<PriorityBasedModifier> reference : sortByPriority(priorityBased)) {
            LithoCommon.debug("Applying modifier with id: {}", reference.key().location());
            reference.value().applyModifier(registries);
        }
    }

    static List<Holder.Reference<PriorityBasedModifier>> sortByPriority(List<Holder.Reference<PriorityBasedModifier>> modifiers) {
        return modifiers.stream().sorted(Comparator.comparingInt(reference -> reference.value().getPriority())).toList();
    }

    default boolean internal$modifiesFabricFeatures() {
        return false;
    }

    enum ModifierPhase implements StringRepresentable {
        NONE("none"),
        BEFORE_ALL("before_all"),
        REPLACE("replace"),
        ADD("add"),
        REMOVE("remove"),
        MODIFY("modify"),
        AFTER_ALL("after_all");

        private final String name;

        ModifierPhase(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
