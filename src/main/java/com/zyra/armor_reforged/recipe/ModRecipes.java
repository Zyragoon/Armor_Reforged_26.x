package com.zyra.armor_reforged.recipe;

import com.zyra.armor_reforged.ArmorReforged;
import com.zyra.armor_reforged.recipe.custom.AlloySmelterRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipes {
    public static final RecipeSerializer<AlloySmelterRecipe> ALLOY_SMELTER_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, "alloying"),
            new RecipeSerializer<>(AlloySmelterRecipe.CODEC, AlloySmelterRecipe.STREAM_CODEC));
    public static final RecipeType<AlloySmelterRecipe> ALLOY_SMELTER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE,
            Identifier.fromNamespaceAndPath(ArmorReforged.MOD_ID, "alloying"),
            new RecipeType<AlloySmelterRecipe>() {
            @Override
            public String toString(){
                return "alloying";
            }
    });

    public static void registerModRecipes() {
        ArmorReforged.LOGGER.info("Registering ModRecipes for " + ArmorReforged.MOD_ID);
    }
}
