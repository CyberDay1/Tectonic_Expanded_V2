package dev.worldgen.tectonic;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import dev.worldgen.tectonic.config.ConfigHandler;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.function.Function;

public class Tectonic {
    public static final String MOD_ID = "tectonic";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    /**
     * Value saved in chunks used for blending between Tectonic versions.
     * <p>
     * 0: Vanilla, Tectonic Pre-3.0
     * <p>
     * 1: 3.0+
     */
    public static int BLENDING_VERSION = 1;
    public static String BLENDING_KEY = "tectonic:blending_version";
    public static final int OVERWORLD_MIN_Y = -256; // CUSTOM: extended vertical range
    public static final int OVERWORLD_HEIGHT = 2000; // CUSTOM: extended vertical range
    public static final int OVERWORLD_MAX_Y = OVERWORLD_MIN_Y + OVERWORLD_HEIGHT - 1; // CUSTOM: extended vertical range
    public static final int OVERWORLD_MIN_SECTION = OVERWORLD_MIN_Y / 16; // CUSTOM: extended vertical range
    public static final int OVERWORLD_MAX_SECTION = OVERWORLD_MAX_Y / 16; // CUSTOM: extended vertical range
    public static final ResourceLocation WIDE_LONG_RAVINE = id("wide_long_ravine"); // CUSTOM: custom ravine carver

    public static void init(Path folder) {
        ConfigHandler.load(folder.resolve("tectonic.json"));
    }

    public static ResourceLocation id(String name) {
        //? if 1.20.1 {
        /*return new ResourceLocation(MOD_ID, name);
        *///?} else {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
        //?}
    }

    public static <T, U> Codec<T> withAlternative(final Codec<T> primary, final Codec<U> alternative, final Function<U, T> converter) {
        //? if 1.20.1 {
        /*return Codec
                .either(primary, alternative)
                .xmap(either -> either.map(v -> v, converter), Either::left);
        *///?} else {
        return Codec.withAlternative(primary, alternative, converter);
         //?}
    }
}
