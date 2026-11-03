package com.zyra.armor_reforged.block.entity;

import com.zyra.armor_reforged.ArmorReforged;
import com.zyra.armor_reforged.block.ModBlocks;
import com.zyra.armor_reforged.block.entity.custom.AlloySmelterBlockEntity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static final BlockEntityType<AlloySmelterBlockEntity> ALLOY_SMELTER_BE =
            register("alloy_smelter", AlloySmelterBlockEntity::new, ModBlocks.ALLOY_SMELTER);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name);

        return Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                id,
                FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build()
        );
    }

    public static void initialize() {
        // Forces class initialization.
    }
}