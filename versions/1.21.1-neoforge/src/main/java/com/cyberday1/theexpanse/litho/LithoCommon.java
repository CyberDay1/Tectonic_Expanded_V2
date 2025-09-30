package com.cyberday1.theexpanse.litho;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LithoCommon {
    public static final String NAMESPACE = "lithostitched";
    private static final Logger LOGGER = LoggerFactory.getLogger("TheExpanseLitho");

    private LithoCommon() {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(NAMESPACE, path);
    }

    public static <T> ResourceKey<T> createResourceKey(ResourceKey<? extends Registry<T>> registry, String name) {
        return ResourceKey.create(registry, id(name));
    }

    public static void debug(String message, Object... arguments) {
        LOGGER.debug(message, arguments);
    }
}
