package com.rena.rustic.common.datagen;

import com.google.gson.JsonObject;
import com.rena.rustic.common.recipes.CrushingTubRecipe;
import com.rena.rustic.common.recipes.FluidStackHelper;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CrushingTubRecipeBuilder {

    private final Ingredient input;
    private FluidStack output;
    private ItemStack byProduct;
    private ResourceLocation id;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public CrushingTubRecipeBuilder(ItemLike item) {
        this.input = Ingredient.of(item);
    }

    public CrushingTubRecipeBuilder(TagKey<Item> tag) {
        this.input = Ingredient.of(tag);
    }

    public static CrushingTubRecipeBuilder recipe(TagKey<Item> tag){
        return new CrushingTubRecipeBuilder(tag);
    }

    public static CrushingTubRecipeBuilder recipe(ItemLike item){
        return new CrushingTubRecipeBuilder(item);
    }

    public CrushingTubRecipeBuilder byProduct(ItemLike byProduct){
        return byProduct(byProduct, 1);
    }

    public CrushingTubRecipeBuilder byProduct(ItemLike byProduct, int count){
        if (byProduct != null)
            throw new IllegalStateException("by Product can only be set once");
        this.byProduct = new ItemStack(byProduct, count);
        return this;
    }

    /**
     * this can only be called once per recipe to make a fluid, otherwise this will throw an exception
     */
    public CrushingTubRecipeBuilder fluid(Fluid fluid) {
        return fluid(fluid, 1);
    }
    /**
     * this can only be called once per recipe to make a fluid, otherwise this will throw an exception
     */
    public CrushingTubRecipeBuilder fluid(Fluid fluid, int amount) {
        return fluid(fluid, amount, null);
    }
    /**
     * this can only be called once per recipe to make a fluid, otherwise this will throw an exception
     */
    public CrushingTubRecipeBuilder fluid(Fluid fluid, int amount, CompoundTag tag) {
        if (this.output != null) {
            throw new IllegalStateException("cant define more then one fluidSTack u have already: " + this.input.toString() + " and u want ot add another: " + fluid.getRegistryName().toString());
        }
        this.output = new FluidStack(fluid, amount, tag);
        this.id = ForgeRegistries.FLUIDS.getKey(fluid);
        return this;
    }

    public CrushingTubRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        if (id != null)
            save(consumer, this.id);
        else
            save(consumer, ForgeRegistries.ITEMS.getKey(this.input.getItems()[0].getItem()));
    }

    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        if (this.advancement.getCriteria().isEmpty()) {
            pFinishedRecipeConsumer.accept(new Result(this.input, this.output,this.byProduct,  pRecipeId));
        } else {
            this.advancement.parent(new ResourceLocation(pRecipeId.getNamespace(), "recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
            pFinishedRecipeConsumer.accept(new Result(this.input, this.output,this.byProduct, pRecipeId, new ResourceLocation(pRecipeId.getNamespace(), "recipes/crushing_tub/" + pRecipeId.getPath()), this.advancement));
        }
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.output == null) {
            throw new IllegalStateException("cant create a recipe with output null, recipe:" + pId.toString());
        }
    }

    public static final class Result implements FinishedRecipe {

        private final Ingredient input;
        private final FluidStack output;
        private final ItemStack byProduct;
        private final ResourceLocation id, advancementId;
        private final Advancement.Builder advancement;

        public Result(Ingredient input, FluidStack output,ItemStack byProduct,  ResourceLocation id, ResourceLocation advancementId, Advancement.Builder advancement) {
            this.input = input;
            this.output = output;
            this.id = id;
            this.advancementId = advancementId;
            this.advancement = advancement;
            this.byProduct = byProduct;
        }

        public Result(Ingredient input, FluidStack output,ItemStack byProduct, ResourceLocation id) {
            this(input, output,byProduct, id, null, null);
        }


        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("fruit", this.input.toJson());
            json.add("fluid", FluidStackHelper.toJson(this.output));
            if (this.byProduct != null){
                JsonObject obj = new JsonObject();
                obj.addProperty("item", this.byProduct.getItem().getRegistryName().toString());
                if (this.byProduct.getCount() > 1){
                    obj.addProperty("count", this.byProduct.getCount());
                }
                json.add("by_product", obj);
            }
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return CrushingTubRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            if (this.advancement != null)
                return this.advancement.serializeToJson();
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }


}
