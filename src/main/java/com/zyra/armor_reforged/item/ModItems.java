package com.zyra.armor_reforged.item;

import com.zyra.armor_reforged.ArmorReforged;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Function;

public class ModItems {

    public static final Item STEEL_INGOT = registerItem("steel_ingot", Item::new);
    public static final Item JADE_CHUNK = registerItem("jade_chunk", Item::new);
    public static final Item RHODONITE_CHUNK = registerItem("rhodonite_chunk", Item::new);


    public static final Item STEEL_SWORD = registerItem("steel_sword",
            properties -> new Item(properties.sword(ModToolMaterials.STEEL, 3f,-2.4f)));
    public static final Item STEEL_PICKAXE = registerItem("steel_pickaxe",
            properties -> new Item(properties.pickaxe(ModToolMaterials.STEEL,1f,-2.8f)));
    public static final Item STEEL_AXE = registerItem("steel_axe",
            properties -> new AxeItem(ModToolMaterials.STEEL,6f,-3.1f,properties));

    public static final Item STEEL_HELMET = registerItem("steel_helmet",
            properties -> new Item(properties.humanoidArmor(ModArmorMaterials.STEEL_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item STEEL_CHESTPLATE = registerItem("steel_chestplate",
            properties -> new Item(properties.humanoidArmor(ModArmorMaterials.STEEL_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item STEEL_LEGGINGS = registerItem("steel_leggings",
            properties -> new Item(properties.humanoidArmor(ModArmorMaterials.STEEL_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item STEEL_BOOTS = registerItem("steel_boots",
            properties -> new Item(properties.humanoidArmor(ModArmorMaterials.STEEL_ARMOR_MATERIAL, ArmorType.BOOTS)));









    private static Item registerItem(String name, Function<Item.Properties, Item> function){
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name)))));
    }


    public static void registerModItems() {
        ArmorReforged.LOGGER.info("Registering Mod Items for"+ ArmorReforged.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(STEEL_INGOT));
    }
}
