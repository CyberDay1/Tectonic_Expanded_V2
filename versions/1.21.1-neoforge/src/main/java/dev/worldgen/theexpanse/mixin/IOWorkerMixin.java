package dev.worldgen.theexpanse.mixin;

import dev.worldgen.theexpanse.TheExpanse;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.chunk.storage.IOWorker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IOWorker.class)
public class IOWorkerMixin {
    @Inject(
        method = "isOldChunk",
        at = @At("HEAD"),
        cancellable = true
    )
    private void theexpanse$needsBlending(CompoundTag nbt, CallbackInfoReturnable<Boolean> cir) {
        int blendingVersion = nbt.contains(TheExpanse.BLENDING_KEY, 3) ? nbt.getInt(TheExpanse.BLENDING_KEY) : 0;
        if (blendingVersion != TheExpanse.BLENDING_VERSION) {
            cir.setReturnValue(true);
        }
    }
}
