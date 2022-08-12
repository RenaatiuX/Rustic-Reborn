package com.rena.rustic.common.datagen;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.core.FluidInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        makeCrushingTubRecipes(consumer);
    }

    private void makeCrushingTubRecipes(Consumer<FinishedRecipe> consumer){
        CrushingTubRecipeBuilder.recipe(Items.APPLE).fluid(FluidInit.APPLE_JUICE_SOURCE.get(), 200).unlockedBy("penis", has(Items.APPLE)).save(consumer);
    }
}
