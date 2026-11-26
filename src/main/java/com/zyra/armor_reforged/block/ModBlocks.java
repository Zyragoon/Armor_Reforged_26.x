package com.zyra.armor_reforged.block;

import com.zyra.armor_reforged.ArmorReforged;
import com.zyra.armor_reforged.block.budding.BuddingCrystalBlock;
import com.zyra.armor_reforged.block.budding.CrystalSet;
import com.zyra.armor_reforged.block.crystal.CrystalBlock;
import com.zyra.armor_reforged.block.custom.AlloySmelterBlock;
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

    public static final Block RHODONITE_CRYSTAL_BLOCK = registerBlock("rhodonite_crystal_block",
            properties -> new Block(properties.strength(1.5f).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_RED)));

    public static final Block JADE_CRYSTAL_BLOCK = registerBlock("jade_crystal_block",
            properties -> new Block(properties.strength(1.5f).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_GREEN)));

    public static final Block OCEANIC_BLOCK = registerBlock("oceanic_block",
            properties -> new Block(properties.strength(50f,1200).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops().mapColor(MapColor.COLOR_BLUE)));

    public static final Block ALLOY_SMELTER = registerBlock("alloy_smelter",
            properties -> new AlloySmelterBlock(properties.strength(3f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(AlloySmelterBlock.LIT) ? 15:0 ).mapColor(MapColor.COLOR_GRAY)));


    private static BlockBehaviour.Properties crystalProperties(
            BlockBehaviour.Properties properties
    ) {
        return properties
                .noOcclusion()
                .forceSolidOn()
                .strength(0.5f)
                .sound(SoundType.AMETHYST);

    }

    public static final CrystalBlock SMALL_JADE_BUD =
            registerBlock("small_jade_bud",
                    properties -> new CrystalBlock(
                            crystalProperties(properties).noOcclusion().forceSolidOn(), 0));

    public static final CrystalBlock MEDIUM_JADE_BUD =
            registerBlock("medium_jade_bud",
                    properties -> new CrystalBlock(
                            crystalProperties(properties).noOcclusion().forceSolidOn(), 1));

    public static final CrystalBlock LARGE_JADE_BUD =
            registerBlock("large_jade_bud",
                    properties -> new CrystalBlock(
                            crystalProperties(properties).noOcclusion().forceSolidOn(), 2));

    public static final CrystalBlock JADE_CLUSTER =
            registerBlock("jade_cluster",
                    properties -> new CrystalBlock(
                            crystalProperties(properties).noOcclusion().forceSolidOn(), 3));



    public static final CrystalSet JADE_SET =
            new CrystalSet(
                    SMALL_JADE_BUD,
                    MEDIUM_JADE_BUD,
                    LARGE_JADE_BUD,
                    JADE_CLUSTER
            );



    public static final Block BUDDING_JADE =
            registerBlock("budding_jade",
                    properties -> new BuddingCrystalBlock(
                            properties
                                    .strength(1.5f)
                                    .sound(SoundType.AMETHYST)
                                    .mapColor(MapColor.COLOR_GREEN),
                            JADE_SET
                    ));


    public static final CrystalBlock SMALL_RHODONITE_BUD =
            registerBlock("small_rhodonite_bud",
                    properties -> new CrystalBlock(
                            crystalProperties(properties).noOcclusion().forceSolidOn(), 0));

    public static final CrystalBlock MEDIUM_RHODONITE_BUD =
            registerBlock("medium_rhodonite_bud",
                    properties -> new CrystalBlock(
                            crystalProperties(properties).noOcclusion().forceSolidOn(), 1));

    public static final CrystalBlock LARGE_RHODONITE_BUD =
            registerBlock("large_rhodonite_bud",
                    properties -> new CrystalBlock(
                            crystalProperties(properties).noOcclusion().forceSolidOn(), 2));

    public static final CrystalBlock RHODONITE_CLUSTER =
            registerBlock("rhodonite_cluster",
                    properties -> new CrystalBlock(
                            crystalProperties(properties).noOcclusion().forceSolidOn(), 3));



    public static final CrystalSet RHODONITE_SET =
            new CrystalSet(
                    SMALL_RHODONITE_BUD,
                    MEDIUM_RHODONITE_BUD,
                    LARGE_RHODONITE_BUD,
                    RHODONITE_CLUSTER
            );



    public static final Block BUDDING_RHODONITE =
            registerBlock("budding_rhodonite",
                    properties -> new BuddingCrystalBlock(
                            properties
                                    .strength(1.5f)
                                    .sound(SoundType.AMETHYST)
                                    .mapColor(MapColor.COLOR_PINK),
                            RHODONITE_SET
                    ));


    private static <T extends Block> T registerBlock(String name, Function<BlockBehaviour.Properties, T> function) {
        Identifier id = Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, name);
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
        T block = function.apply(BlockBehaviour.Properties.of().setId(blockKey));
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK, id, block);
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
