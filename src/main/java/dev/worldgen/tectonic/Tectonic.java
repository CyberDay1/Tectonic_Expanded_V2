package dev.worldgen.tectonic;

import dev.worldgen.tectonic.config.ConfigHandler;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Tectonic {
    public static final String MOD_ID = "tectonic";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * Value saved in chunks used for blending between Tectonic versions.
     * 0: Vanilla, Tectonic Pre-3.0
     * 1: 3.0+
     */
    public static int BLENDING_VERSION = 1;
    public static String BLENDING_KEY = "tectonic:blending_version";

    public static void init(Path folder) {
        ConfigHandler.load(folder.resolve("tectonic.json"));
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
