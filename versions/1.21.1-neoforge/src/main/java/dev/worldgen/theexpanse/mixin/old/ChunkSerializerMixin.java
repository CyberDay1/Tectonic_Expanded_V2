//? if 1.20.1 {
/*package dev.worldgen.theexpanse.mixin.old;

import com.cyberday1.theexpanse.world.WorldgenConstants;
import dev.worldgen.theexpanse.TheExpanse;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ChunkSerializer.class)
public class ChunkSerializerMixin {
    @Unique
    // Taken from the BlendingDataFix, don't blend this or the terrain will drop all the way to bedrock level
    private static final Set<String> STATUSES_TO_SKIP_BLENDING = Set.of(
            "minecraft:empty", "minecraft:structure_starts", "minecraft:structure_references", "minecraft:biomes"
    );

    @Inject(method = "read", at = @At("HEAD"))
    private static void theexpanse$read(ServerLevel level, PoiManager poiManager, ChunkPos chunkPos, CompoundTag nbt, CallbackInfoReturnable<ProtoChunk> cir) {
        // Safe cast unless some mod does weird bs
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        if (STATUSES_TO_SKIP_BLENDING.contains(ChunkStatus.byName(nbt.getString("Status")).toString())) return;
        if (nbt.getInt(TheExpanse.BLENDING_KEY) != TheExpanse.BLENDING_VERSION) {
            int min = 0, max = 0;
            int minSectionIndex = WorldgenConstants.getSectionIndex(WorldgenConstants.OVERWORLD_MIN_Y); // CUSTOM: extended vertical range (heightmap/sectionpos/structure)
            int maxSectionIndex = WorldgenConstants.getSectionIndex(WorldgenConstants.OVERWORLD_MAX_Y); // CUSTOM: extended vertical range (heightmap/sectionpos/structure)
            ListTag sections = nbt.getList("sections", ListTag.TAG_COMPOUND);
            for (Tag section : sections) {
                int y = section instanceof IntTag tag ? tag.getAsInt() : 0;
                min = Math.min(y, min);
                max = Math.max(y, max);
            }
            min = Math.min(min, minSectionIndex); // CUSTOM: extended vertical range (heightmap/sectionpos/structure)
            max = Math.max(max, maxSectionIndex); // CUSTOM: extended vertical range (heightmap/sectionpos/structure)
            CompoundTag blendingData = new CompoundTag();
            blendingData.putInt("min_section", min);
            blendingData.putInt("max_section", max);
            nbt.put("blending_data", blendingData);
            nbt.remove("Heightmaps");
            nbt.remove("isLightOn");
        }
    }

    @Inject(method = "write", at = @At("RETURN"), cancellable = true)
    private static void theexpanse$write(ServerLevel serverLevel, ChunkAccess chunkAccess, CallbackInfoReturnable<CompoundTag> cir) {
        if (TheExpanse.BLENDING_VERSION == 0) return;
        CompoundTag data = cir.getReturnValue();
        data.putInt(TheExpanse.BLENDING_KEY, TheExpanse.BLENDING_VERSION);
        cir.setReturnValue(data);
    }
}
*///?}
