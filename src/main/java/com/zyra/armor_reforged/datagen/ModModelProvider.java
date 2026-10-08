package com.zyra.armor_reforged.datagen;

import com.zyra.armor_reforged.block.ModBlocks;
import com.zyra.armor_reforged.item.ModArmorMaterials;
import com.zyra.armor_reforged.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialCube(ModBlocks.STEEL_BLOCK);
        blockModelGenerators.createTrivialCube(ModBlocks.RHODONITE_CRYSTAL_BLOCK);
        blockModelGenerators.createTrivialCube(ModBlocks.JADE_CRYSTAL_BLOCK);


    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.STEEL_INGOT, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.JADE_CHUNK, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.RHODONITE_CHUNK, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.STEEL_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.STEEL_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.STEEL_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);


        itemModelGenerators.generateTrimmableItem(ModItems.STEEL_HELMET, ModArmorMaterials.STEEL_KEY,
                ItemModelGenerators.TRIM_PREFIX_HELMET,false);
        itemModelGenerators.generateTrimmableItem(ModItems.STEEL_CHESTPLATE, ModArmorMaterials.STEEL_KEY,
                ItemModelGenerators.TRIM_PREFIX_CHESTPLATE,false);
        itemModelGenerators.generateTrimmableItem(ModItems.STEEL_LEGGINGS, ModArmorMaterials.STEEL_KEY,
                ItemModelGenerators.TRIM_PREFIX_LEGGINGS,false);
        itemModelGenerators.generateTrimmableItem(ModItems.STEEL_BOOTS, ModArmorMaterials.STEEL_KEY,
                ItemModelGenerators.TRIM_PREFIX_BOOTS,false);

    }
}
