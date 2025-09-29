package dev.worldgen.theexpanse.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.JsonOps;
import dev.worldgen.theexpanse.TheExpanse;
import dev.worldgen.theexpanse.config.state.ConfigState;
import net.minecraft.util.GsonHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;

public class ConfigHandler {
    private static ConfigState LOADED_STATE = new ConfigState(
        ConfigState.General.DEFAULT,
        ConfigState.GlobalTerrain.DEFAULT,
        ConfigState.Continents.DEFAULT,
        ConfigState.Islands.DEFAULT,
        ConfigState.Oceans.DEFAULT,
        ConfigState.Biomes.DEFAULT
    );
    private static Path PATH;

    public static ConfigState getState() {
        return LOADED_STATE;
    }

    public static void load(Path path) {
        PATH = path;

        if (!Files.isRegularFile(path)) {
            save();
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            JsonElement json = JsonParser.parseReader(reader);
            Optional<ConfigState> result = ConfigState.CODEC.parse(JsonOps.INSTANCE, json).result();
            if (result.isPresent()) {
                LOADED_STATE = result.get();
            } else {
                throw new JsonParseException("Invalid codec");
            }
        } catch (JsonParseException e) {
            TheExpanse.LOGGER.error("Couldn't parse config file, resetting to default config");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        save();
    }

    public static void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(PATH)) {
            JsonElement element = ConfigState.CODEC.encodeStart(JsonOps.INSTANCE, LOADED_STATE).result().get();
            StringWriter stringWriter = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(stringWriter);
            jsonWriter.setIndent("  ");
            GsonHelper.writeValue(jsonWriter, element, Comparator.naturalOrder());
            writer.write(stringWriter.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}