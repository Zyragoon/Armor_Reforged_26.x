package com.zyra.armor_reforged.creativemodetab;

import com.zyra.armor_reforged.ArmorReforged;
import com.zyra.armor_reforged.block.ModBlocks;
import com.zyra.armor_reforged.item.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static final CreativeModeTab ARMOR_REFORGED = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, "armor_reforged"),
            FabricCreativeModeTab.builder().icon(() -> new ItemStack(ModItems.STEEL_INGOT))
                    .title(Component.translatable("creativemodetab.armorreforged.armorreforged"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.STEEL_INGOT);
                        output.accept(ModItems.JADE_CHUNK);
                        output.accept(ModItems.RHODONITE_CHUNK);
                        output.accept(ModItems.STEEL_AXE);
                        output.accept(ModBlocks.STEEL_BLOCK);
                        output.accept(ModBlocks.RHODONITE_CRYSTAL_BLOCK);
                        output.accept(ModBlocks.JADE_CRYSTAL_BLOCK);
                        output.accept(ModItems.STEEL_HELMET);
                        output.accept(ModItems.STEEL_CHESTPLATE);
                        output.accept(ModItems.STEEL_LEGGINGS);
                        output.accept(ModItems.STEEL_BOOTS);


                    })

                    .build());



    public static void registerModCreativeModeTabs() {
        ArmorReforged.LOGGER.info("Registering creative mode tabs for " + ArmorReforged.MOD_ID);

    }
}
