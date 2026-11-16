package com.zyra.armor_reforged.recipe.custom;


import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zyra.armor_reforged.recipe.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record AlloySmelterRecipe(
        Ingredient inputItem1,
        Ingredient inputItem2,
        ItemStackTemplate output
) implements Recipe<AlloySmelterRecipeInput> {


    public static final MapCodec<AlloySmelterRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Ingredient.CODEC.fieldOf("ingredient1").forGetter(AlloySmelterRecipe::inputItem1),
                    Ingredient.CODEC.fieldOf("ingredient2").forGetter(AlloySmelterRecipe::inputItem2),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(AlloySmelterRecipe::output)
            ).apply(instance, AlloySmelterRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AlloySmelterRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC,
                    AlloySmelterRecipe::inputItem1,

                    Ingredient.CONTENTS_STREAM_CODEC,
                    AlloySmelterRecipe::inputItem2,

                    ItemStackTemplate.STREAM_CODEC,
                    AlloySmelterRecipe::output,

                    AlloySmelterRecipe::new);

    @Override
    public boolean matches(AlloySmelterRecipeInput input, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return inputItem1.test(input.getItem(0))
                && inputItem2.test(input.getItem(1));
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
        return ModRecipes.ALLOY_SMELTER_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<AlloySmelterRecipeInput>> getType() {
        return ModRecipes.ALLOY_SMELTER_TYPE;
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
