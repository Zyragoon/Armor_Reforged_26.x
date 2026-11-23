package com.zyra.armor_reforged.datagen;

import com.zyra.armor_reforged.block.ModBlocks;
import com.zyra.armor_reforged.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {
    public ModBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {

        dropSelf(ModBlocks.STEEL_BLOCK);
        dropSelf(ModBlocks.ALLOY_SMELTER);
        dropSelf(ModBlocks.OCEANIC_BLOCK);
        dropSelf(ModBlocks.JADE_CRYSTAL_BLOCK);
        dropSelf(ModBlocks.RHODONITE_CRYSTAL_BLOCK);

        add(ModBlocks.JADE_CLUSTER, createMultipleOreDrops(ModBlocks.JADE_CLUSTER,ModItems.JADE_CHUNK,4,4));
        add(ModBlocks.RHODONITE_CLUSTER, createMultipleOreDrops(ModBlocks.RHODONITE_CLUSTER, ModItems.RHODONITE_CHUNK,4,4));

        dropWhenSilkTouch(ModBlocks.LARGE_RHODONITE_BUD);
        dropWhenSilkTouch(ModBlocks.MEDIUM_RHODONITE_BUD);
        dropWhenSilkTouch(ModBlocks.SMALL_RHODONITE_BUD);
        dropWhenSilkTouch(ModBlocks.BUDDING_RHODONITE);

        dropWhenSilkTouch(ModBlocks.LARGE_JADE_BUD);
        dropWhenSilkTouch(ModBlocks.MEDIUM_JADE_BUD);
        dropWhenSilkTouch(ModBlocks.SMALL_JADE_BUD);
        dropWhenSilkTouch(ModBlocks.BUDDING_JADE);
        dropWhenSilkTouch(Blocks.BUDDING_AMETHYST);
    }



        public LootTable.Builder createMultipleOreDrops(final Block block, Item item, float minDrops, float maxDrops) {
            HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

            return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(
                    block, LootItem.lootTableItem(item)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                            .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))));

    }
}
