package com.zyra.armor_reforged.item;

import com.zyra.armor_reforged.ArmorReforged;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class Moditems {

    public static final Item STEEL_INGOT = registerItem("steel_ingot", Item::new);

    public static final Item STEEL_AXE = registerItem("steel_axe",
            properties -> new AxeItem(ModToolMaterials.STEEL,6f,-3.2f,properties));

    private static Item registerItem(String name, Function<Item.Properties, Item> function){
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name)))));
    }


    public static void registerModItems() {
        ArmorReforged.LOGGER.info("Registering Mod Items for"+ ArmorReforged.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(STEEL_INGOT));
    }
}
