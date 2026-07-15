package com.acidui.skyblockahflipper.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import com.acidui.skyblockahflipper.client.gui.AHFlipperScreen;

@Environment(EnvType.CLIENT)
public class SkyblockAHFlipperClient implements ClientModInitializer {
    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.skyblock-ah-flipper.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F6,
                "category.skyblock-ah-flipper.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openGuiKey.wasPressed()) {
                client.setScreen(new AHFlipperScreen());
            }
        });
    }
}