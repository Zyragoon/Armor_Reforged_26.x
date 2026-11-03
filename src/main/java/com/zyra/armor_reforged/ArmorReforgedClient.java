package com.zyra.armor_reforged;

import com.zyra.armor_reforged.menu.AlloySmelterScreen;
import com.zyra.armor_reforged.menu.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class ArmorReforgedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MenuScreens.register(
                ModMenuTypes.ALLOY_SMELTER,
                AlloySmelterScreen::new
        );
    }
}