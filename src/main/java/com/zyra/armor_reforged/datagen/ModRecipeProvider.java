package com.zyra.armor_reforged.datagen;

import com.zyra.armor_reforged.block.ModBlocks;
import com.zyra.armor_reforged.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                List<ItemLike> STEEL_SMELTABLES = List.of(ModItems.STEEL_AXE, ModItems.STEEL_CHESTPLATE, ModItems.STEEL_HOE, ModItems.STEEL_PICKAXE,
                        ModItems.STEEL_BOOTS, ModItems.STEEL_HELMET, ModItems.STEEL_LEGGINGS, ModItems.STEEL_SHOVEL, ModItems.STEEL_SPEAR, ModItems.STEEL_SWORD);

                oreSmelting(STEEL_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.STEEL_NUGGET, 0.25f,200, "steel");
                oreBlasting(STEEL_SMELTABLES, RecipeCategory.MISC, CookingBookCategory.MISC, ModItems.STEEL_NUGGET, 0.25f,100, "steel");

                nineBlockStorageRecipes(RecipeCategory.MISC, ModItems.STEEL_INGOT, RecipeCategory.BUILDING_BLOCKS, ModBlocks.STEEL_BLOCK);



                shapeless(RecipeCategory.MISC, ModItems.STEEL_INGOT, 9)
                        .requires(ModBlocks.STEEL_BLOCK)
                        .unlockedBy(getHasName(ModBlocks.STEEL_BLOCK), has(ModBlocks.STEEL_BLOCK))
                        .group("steel")
                        .save(output,"steel_ingots_from_steel_block");

                shapeless(RecipeCategory.MISC, ModItems.STEEL_NUGGET, 9)
                        .requires(ModItems.STEEL_INGOT)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT), has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output);


                shaped(RecipeCategory.MISC,ModItems.STEEL_INGOT,1)
                        .pattern("NNN")
                        .pattern("NNN")
                        .pattern("NNN")
                        .define('N', ModItems.STEEL_NUGGET)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT), has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output, "steel_nuggets_to_steel_ingot");


                shaped(RecipeCategory.COMBAT,ModItems.STEEL_SWORD)
                        .pattern("I")
                        .pattern("I")
                        .pattern("S")
                        .define('I',ModItems.STEEL_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT),has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output);

                shaped(RecipeCategory.TOOLS,ModItems.STEEL_PICKAXE)
                        .pattern("III")
                        .pattern(" S ")
                        .pattern(" S ")
                        .define('I',ModItems.STEEL_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT),has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output);

                shaped(RecipeCategory.TOOLS,ModItems.STEEL_SHOVEL)
                        .pattern("I")
                        .pattern("S")
                        .pattern("S")
                        .define('I',ModItems.STEEL_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT),has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output);

                shaped(RecipeCategory.TOOLS,ModItems.STEEL_AXE)
                        .pattern("II")
                        .pattern("SI")
                        .pattern("S ")
                        .define('I',ModItems.STEEL_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT),has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output);

                shaped(RecipeCategory.TOOLS,ModItems.STEEL_HOE)
                        .pattern("II")
                        .pattern("S ")
                        .pattern("S ")
                        .define('I',ModItems.STEEL_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT),has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output);

                shaped(RecipeCategory.COMBAT,ModItems.STEEL_SPEAR)
                        .pattern("  I")
                        .pattern(" S ")
                        .pattern("S  ")
                        .define('I',ModItems.STEEL_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT),has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output);

                shaped(RecipeCategory.COMBAT,ModItems.STEEL_HELMET)
                        .pattern("III")
                        .pattern("I I")
                        .define('I',ModItems.STEEL_INGOT)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT),has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output);

                shaped(RecipeCategory.COMBAT,ModItems.STEEL_CHESTPLATE)
                        .pattern("I I")
                        .pattern("III")
                        .pattern("III")
                        .define('I',ModItems.STEEL_INGOT)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT),has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output);

                shaped(RecipeCategory.COMBAT,ModItems.STEEL_LEGGINGS)
                        .pattern("III")
                        .pattern("I I")
                        .pattern("I I")
                        .define('I',ModItems.STEEL_INGOT)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT),has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output);

                shaped(RecipeCategory.COMBAT,ModItems.STEEL_BOOTS)
                        .pattern("I I")
                        .pattern("I I")
                        .define('I',ModItems.STEEL_INGOT)
                        .unlockedBy(getHasName(ModItems.STEEL_INGOT),has(ModItems.STEEL_INGOT))
                        .group("steel")
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "Armor Reforged Recipes";
    }
}
