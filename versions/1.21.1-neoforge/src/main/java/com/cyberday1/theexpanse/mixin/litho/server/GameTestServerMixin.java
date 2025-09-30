package com.cyberday1.theexpanse.mixin.litho.server;

import com.cyberday1.theexpanse.litho.worldgen.modifier.Modifier;
import net.minecraft.gametest.framework.GameTestServer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameTestServer.class)
public class GameTestServerMixin {
    @Inject(method = "initServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/gametest/framework/GameTestServer;loadLevel()V", shift = At.Shift.BEFORE))
    private void theexpanse$applyModifiers(CallbackInfoReturnable<Boolean> cir) {
        Modifier.applyModifiers((MinecraftServer) (Object) this);
    }
}
