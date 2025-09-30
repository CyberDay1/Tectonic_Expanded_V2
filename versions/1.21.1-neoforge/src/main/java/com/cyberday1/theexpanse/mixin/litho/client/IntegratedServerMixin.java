package com.cyberday1.theexpanse.mixin.litho.client;

import com.cyberday1.theexpanse.litho.worldgen.modifier.Modifier;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {
    @Inject(method = "initServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/server/IntegratedServer;loadLevel()V", shift = At.Shift.BEFORE))
    private void theexpanse$applyModifiers(CallbackInfoReturnable<Boolean> cir) {
        Modifier.applyModifiers((MinecraftServer) (Object) this);
    }
}
