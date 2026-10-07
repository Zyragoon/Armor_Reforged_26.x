package com.zyra.armor_reforged.datagen;

import com.zyra.armor_reforged.item.ModItems;
import com.zyra.armor_reforged.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;


import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {

    public ModItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        valueLookupBuilder(ModTags.Items.STEEL_REPAIR)
                .add(ModItems.STEEL_INGOT);



        valueLookupBuilder(ItemTags.AXES)
                .add(ModItems.STEEL_AXE);


        valueLookupBuilder(ItemTags.HEAD_ARMOR).add(ModItems.STEEL_HELMET);
        valueLookupBuilder(ItemTags.CHEST_ARMOR).add(ModItems.STEEL_CHESTPLATE);
        valueLookupBuilder(ItemTags.LEG_ARMOR).add(ModItems.STEEL_LEGGINGS);
        valueLookupBuilder(ItemTags.FOOT_ARMOR).add(ModItems.STEEL_BOOTS);


        valueLookupBuilder(ItemTags.BEACON_PAYMENT_ITEMS)
                .add(ModItems.STEEL_INGOT);
    }
}