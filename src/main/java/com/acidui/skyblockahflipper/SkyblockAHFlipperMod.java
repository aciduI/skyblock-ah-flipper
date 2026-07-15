package com.acidui.skyblockahflipper;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyblockAHFlipperMod implements ModInitializer {
    public static final String MOD_ID = "skyblock-ah-flipper";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Skyblock AH Flipper initialized!");
    }
}