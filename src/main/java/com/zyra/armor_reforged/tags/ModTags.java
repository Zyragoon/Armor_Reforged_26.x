package com.zyra.armor_reforged.tags;

import com.zyra.armor_reforged.ArmorReforged;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> INCORRECT_FOR_STEEL_TOOL = createTag("incorrect_for_steel_tool");

        private static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name));
        }
    }


    public static class Items {

        public static final TagKey<Item> STEEL_REPAIR = createTag("steel_repair");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name));
        }
    }
}




