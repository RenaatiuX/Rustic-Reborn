package com.rena.rustic.common.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rena.rustic.core.RecipeInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class CrushingTubRecipe implements Recipe<Container> {

    public static final Serializer SERIALIZER = new Serializer();

    private final Ingredient input;
    private final ItemStack byProduct;
    private final FluidStack output;
    private final ResourceLocation id;

    public CrushingTubRecipe(Ingredient input, ItemStack byProduct, FluidStack output, ResourceLocation id) {
        this.input = input;
        this.byProduct = byProduct;
        this.output = output;
        this.id = id;
    }


    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            if (this.input.test(pContainer.getItem(i)))
                return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }

    public FluidStack getOutput() {
        return output.copy();
    }

    public ItemStack getByProduct() {
        return byProduct.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.CRUSHING_TUB_RECIPE;
    }

    public static JsonElement getJsonElement(JsonObject obj, String name) {
        return GsonHelper.isArrayNode(obj, name) ? GsonHelper.getAsJsonArray(obj, name)
                : GsonHelper.getAsJsonObject(obj, name);
    }

    private static final class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CrushingTubRecipe>{

        @Override
        public CrushingTubRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(getJsonElement(json, "fruit"));
            FluidStack output = FluidStackHelper.getFluidStack(json.getAsJsonObject("fluid"), true);
            ItemStack byProduct;
            if (json.has("by_product"))
                byProduct = CraftingHelper.getItemStack(json.getAsJsonObject("by_product"), true);
            else byProduct = ItemStack.EMPTY;
            return new CrushingTubRecipe(input, byProduct, output,id);
        }

        @Nullable
        @Override
        public CrushingTubRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            FluidStack output = FluidStack.readFromPacket(buffer);
            ItemStack byProduct = buffer.readItem();
            return new CrushingTubRecipe(input, byProduct, output,pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CrushingTubRecipe pRecipe) {
            pRecipe.input.toNetwork(pBuffer);
            pRecipe.output.writeToPacket(pBuffer);
            pBuffer.writeItem(pRecipe.byProduct);
        }
    }
}
