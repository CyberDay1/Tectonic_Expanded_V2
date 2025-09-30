package com.cyberday1.theexpanse.mixin.litho.server;

import com.cyberday1.theexpanse.litho.worldgen.modifier.Modifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedServer.class)
public class DedicatedServerMixin {
    @Inject(method = "initServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/dedicated/DedicatedServer;loadLevel()V", shift = At.Shift.BEFORE), allow = 1)
    private void theexpanse$applyModifiers(CallbackInfoReturnable<Boolean> cir) {
        Modifier.applyModifiers((MinecraftServer) (Object) this);
    }
}
