package com.cyberday1.theexpanse.litho.registry;

import com.cyberday1.theexpanse.litho.LithoCommon;
import com.cyberday1.theexpanse.litho.worldgen.modifier.Modifier;
import com.cyberday1.theexpanse.litho.worldgen.placementcondition.PlacementCondition;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class LithoRegistryKeys {
    public static final ResourceKey<Registry<Modifier>> WORLDGEN_MODIFIER = create("worldgen_modifier");
    public static final ResourceKey<Registry<com.mojang.serialization.MapCodec<? extends Modifier>>> MODIFIER_TYPE = create("modifier_type");
    public static final ResourceKey<Registry<com.mojang.serialization.MapCodec<? extends PlacementCondition>>> PLACEMENT_CONDITION_TYPE = create("placement_condition_type");

    private LithoRegistryKeys() {
    }

    private static <T> ResourceKey<Registry<T>> create(String name) {
        return ResourceKey.createRegistryKey(LithoCommon.id(name));
    }
}
