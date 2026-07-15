package com.acidui.skyblockahflipper.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMixin {
    @Inject(method = "onGameMessage", at = @At("HEAD"))
    private void onChatMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
        // Chat message processing hook for future AH notifications
    }
}