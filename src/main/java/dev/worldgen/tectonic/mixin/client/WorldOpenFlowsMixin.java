package dev.worldgen.tectonic.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Dynamic;
import dev.worldgen.tectonic.config.ConfigHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldOpenFlows.class)
public abstract class WorldOpenFlowsMixin {
    @Shadow @Final
    private Minecraft minecraft;

    @Shadow
    protected abstract void openWorldCheckWorldStemCompatibility(LevelStorageSource.LevelStorageAccess levelStorage, WorldStem worldStem, PackRepository packRepository, Runnable onFail);

    @Inject(
            method = "openWorldLoadLevelStem(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lcom/mojang/serialization/Dynamic;ZLjava/lang/Runnable;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;openWorldCheckWorldStemCompatibility(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/server/WorldStem;Lnet/minecraft/server/packs/repository/PackRepository;Ljava/lang/Runnable;)V"
            ),
            cancellable = true
    )
    private void addGuardrailWarning(LevelStorageSource.LevelStorageAccess levelStorage, Dynamic<?> levelData, boolean safeMode, Runnable onFail, CallbackInfo ci, @Local(ordinal = 0) PackRepository packRepository, @Local(ordinal = 0) WorldStem worldStem) {
        if (ConfigHandler.getState().general.modEnabled && !ConfigHandler.getState().general.hideBetaWarning) {
            Component title = Component.literal("Warning: This world may be damaged if opened!").withStyle(ChatFormatting.YELLOW);

            MutableComponent description = Component.literal("This is a ").withStyle(ChatFormatting.GRAY);
            description.append(Component.literal("BETA").withStyle(ChatFormatting.RED));
            description.append(Component.literal(" version of Tectonic v3.").withStyle(ChatFormatting.GRAY));
            description.append(Component.literal("\nThis update is a work in progress and exists for collecting feedback.").withStyle(ChatFormatting.GRAY));
            description.append(Component.literal("\nAll features present are ").withStyle(ChatFormatting.GRAY));
            description.append(Component.literal("subject to change or removal").withStyle(ChatFormatting.RED));
            description.append(Component.literal(" in the full release.").withStyle(ChatFormatting.GRAY));
            description.append(Component.literal("\nChunk blending is currently ").withStyle(ChatFormatting.GRAY));
            description.append(Component.literal("DISABLED").withStyle(ChatFormatting.RED));
            description.append(Component.literal(", so backup this world if you care about it.").withStyle(ChatFormatting.GRAY));
            description.append(Component.literal("\nTo report issues or give feedback, please visit the Tectonic github page.").withStyle(ChatFormatting.GRAY));

            description.append(Component.literal("\n\nDo you wish to proceed?").withStyle(ChatFormatting.YELLOW));
            description.append(Component.literal("\n\nThis message can be disabled in the config.").withStyle(ChatFormatting.GRAY));

            Runnable start = () -> this.openWorldCheckWorldStemCompatibility(levelStorage, worldStem, packRepository, onFail);

            this.minecraft.setScreen(new BackupConfirmScreen(
                () -> {
                    worldStem.close();
                    levelStorage.safeClose();
                    onFail.run();
                },
                (backup, eraseCache) -> {
                    if (backup) {
                        EditWorldScreen.makeBackupAndShowToast(levelStorage);
                    }
                    start.run();
                },
                title, description, false
            ));

            ci.cancel();
        }
    }
}
