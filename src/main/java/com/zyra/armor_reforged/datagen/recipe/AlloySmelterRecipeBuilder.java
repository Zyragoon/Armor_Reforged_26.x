
package com.zyra.armor_reforged.datagen.recipe;

import com.zyra.armor_reforged.recipe.custom.AlloySmelterRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

public class AlloySmelterRecipeBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final Ingredient ingredient1;
    private final Ingredient ingredient2;

    private final RecipeUnlockAdvancementBuilder advancementBuilder =
            new RecipeUnlockAdvancementBuilder();

    private @Nullable String group;

    private AlloySmelterRecipeBuilder(
            RecipeCategory category,
            Ingredient ingredient1,
            Ingredient ingredient2,
            ItemStackTemplate result
    ) {
        this.category = category;
        this.result = result;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
    }


    public static AlloySmelterRecipeBuilder alloySmelterRecipe(
            RecipeCategory category,
            Ingredient ingredient1,
            Ingredient ingredient2,
            ItemLike item,
            int count
    ) {
        return new AlloySmelterRecipeBuilder(
                category,
                ingredient1,
                ingredient2,
                new ItemStackTemplate(item.asItem(), count)
        );
    }

    public static AlloySmelterRecipeBuilder alloySmelterRecipe(
            RecipeCategory category,
            Ingredient ingredient1,
            Ingredient ingredient2,
            ItemLike item
    ) {
        return new AlloySmelterRecipeBuilder(
                category,
                ingredient1,
                ingredient2,
                new ItemStackTemplate(item.asItem())
        );
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        AlloySmelterRecipe recipe = new AlloySmelterRecipe(
                this.ingredient1,
                this.ingredient2,
                this.result
        );

        output.accept(id, recipe,
                this.advancementBuilder.build(output, id, this.category));
    }
}
