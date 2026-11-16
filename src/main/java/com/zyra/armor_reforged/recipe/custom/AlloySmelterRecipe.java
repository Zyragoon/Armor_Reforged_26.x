package com.zyra.armor_reforged.recipe.custom;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record AlloySmelterRecipe (Ingredient inputItem, ItemStackTemplate output) implements Recipe<AlloySmelterRecipeInput> {


    @Override
    public boolean matches(AlloySmelterRecipeInput input, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        return inputItem.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(AlloySmelterRecipeInput input) {
        return output.create().copy();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "alloying";
    }

    @Override
    public RecipeSerializer<? extends Recipe<AlloySmelterRecipeInput>> getSerializer() {
        return
    }

    @Override
    public RecipeType<? extends Recipe<AlloySmelterRecipeInput>> getType() {
        return
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
