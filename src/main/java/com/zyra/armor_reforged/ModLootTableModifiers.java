package com.zyra.armor_reforged;

import com.zyra.armor_reforged.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class ModLootTableModifiers {
    public static void modifyLootTables() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            if (source.isBuiltin() && BuiltInLootTables.BURIED_TREASURE.equals(key)) {

                LootPool.Builder pool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.OCEANIC_SMITHING_TEMPLATE))
                        .when(LootItemRandomChanceCondition.randomChance(0.075f));

                tableBuilder.withPool(pool);
            }
        });
    }
}
