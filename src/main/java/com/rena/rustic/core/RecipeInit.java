package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.recipes.CrushingTubRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.RegistryEvent;

public class RecipeInit {

    public static final RecipeType<CrushingTubRecipe> CRUSHING_TUB_RECIPE = register("crushing_tub");


    public static void registerRecipes(RegistryEvent.Register<RecipeSerializer<?>> event) {
        registerRecipe(event, CRUSHING_TUB_RECIPE, CrushingTubRecipe.SERIALIZER);
    }


    private static void registerRecipe(RegistryEvent.Register<RecipeSerializer<?>> event, RecipeType<?> type, RecipeSerializer<?> serializer) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
        event.getRegistry().register(serializer.setRegistryName(new ResourceLocation(type.toString())));
    }


    protected static <T extends Recipe<?>> RecipeType<T> register(String name){
        return new RecipeType<T>() {
            @Override
            public String toString() {
                return RusticReborn.modLoc(name).toString();
            }
        };
    }
}
