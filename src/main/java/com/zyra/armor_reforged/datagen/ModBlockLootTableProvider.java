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

        add(ModBlocks.JADE_CRYSTAL_BLOCK, createMultipleOreDrops(ModBlocks.JADE_CRYSTAL_BLOCK, ModItems.JADE_CHUNK,3,5));
        add(ModBlocks.RHODONITE_CRYSTAL_BLOCK, createMultipleOreDrops(ModBlocks.RHODONITE_CRYSTAL_BLOCK, ModItems.RHODONITE_CHUNK,3,5));
    }



        public LootTable.Builder createMultipleOreDrops(final Block block, Item item, float minDrops, float maxDrops) {
            HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

            return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(
                    block, LootItem.lootTableItem(item)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                            .apply(ApplyBonusCount.addOreBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE)))));

    }
}
