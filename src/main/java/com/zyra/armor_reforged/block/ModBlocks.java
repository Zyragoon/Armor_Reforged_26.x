package com.zyra.armor_reforged.block;

import com.zyra.armor_reforged.ArmorReforged;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

public class ModBlocks {
    public static final Block STEEL_BLOCK = registerBlock("steel_block",
            properties -> new Block(properties.strength(5f,8f).sound(SoundType.METAL).requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_BLACK)));


    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> function){
        Block toRegister = function.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name), toRegister);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name),
                new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix()
                        .setId(ResourceKey.create(Registries.ITEM ,Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name)))));
    }

    public static void registerModBlocks(){
        ArmorReforged.LOGGER.info("Registering Mod Blocks for " +ArmorReforged.MOD_ID);
    }
}
