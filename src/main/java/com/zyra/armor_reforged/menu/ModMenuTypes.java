package com.zyra.armor_reforged.menu;

import com.zyra.armor_reforged.ArmorReforged;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {

    public static final MenuType<AlloySmelterMenu> ALLOY_SMELTER =
            register("alloy_smelter", AlloySmelterMenu::new);

    private static <T extends AbstractContainerMenu> MenuType<T> register(
            String name,
            MenuType.MenuSupplier<T> constructor
    ) {
        return Registry.register(
                BuiltInRegistries.MENU,
                Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name),
                new MenuType<>(constructor, FeatureFlagSet.of())
        );
    }

    public static void initialize() {
        ArmorReforged.LOGGER.info("Registering Menu Types for " + ArmorReforged.MOD_ID);
    }
}