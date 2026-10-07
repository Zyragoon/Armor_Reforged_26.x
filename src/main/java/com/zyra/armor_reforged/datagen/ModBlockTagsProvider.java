package com.zyra.armor_reforged.datagen;

import com.zyra.armor_reforged.block.ModBlocks;
import com.zyra.armor_reforged.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.STEEL_BLOCK)
                .add(ModBlocks.JADE_CRYSTAL_BLOCK)
                .add(ModBlocks.RHODONITE_CRYSTAL_BLOCK);


        valueLookupBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.RHODONITE_CRYSTAL_BLOCK)
                .add(ModBlocks.JADE_CRYSTAL_BLOCK);


        valueLookupBuilder(ModTags.Blocks.INCORRECT_FOR_STEEL_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        valueLookupBuilder(BlockTags.BEACON_BASE_BLOCKS)
                .add(ModBlocks.STEEL_BLOCK);
    }
}
