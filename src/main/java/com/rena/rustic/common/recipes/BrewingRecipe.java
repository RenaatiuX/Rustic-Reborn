package com.rena.rustic.common.recipes;

import com.google.gson.JsonObject;
import com.rena.rustic.common.block_entity.BrewingBarrelTileEntity;
import com.rena.rustic.core.RecipeInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class BrewingRecipe implements Recipe<BrewingBarrelTileEntity> {

    public static final Serializer SERIALIZER = new Serializer();

    private final FluidStack input, auxiliary, output;
    private final ResourceLocation id;
    private final int brewTime;

    public BrewingRecipe(FluidStack input, FluidStack auxiliary, FluidStack output, ResourceLocation id, int brewTime) {
        this.input = input;
        this.auxiliary = auxiliary;
        this.output = output;
        this.id = id;
        this.brewTime = brewTime;
    }

    @Override
    public boolean matches(BrewingBarrelTileEntity pContainer, Level pLevel) {
        if (pContainer.getAuxiliary().getFluid().containsFluid(this.auxiliary) &&
                pContainer.getInput().getFluid().containsFluid(this.input)) {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(BrewingBarrelTileEntity pContainer) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    public FluidStack getOutput() {
        return output;
    }

    public int getBrewTime() {
        return brewTime;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
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
        return RecipeInit.BREWING_RECIPE;
    }

    private static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<BrewingRecipe> {

        @Override
        public BrewingRecipe fromJson(ResourceLocation id, JsonObject json) {
            FluidStack input = FluidStackHelper.getFluidStack(json.getAsJsonObject("input"), true);
            FluidStack auxiliary = FluidStackHelper.getFluidStack(json.getAsJsonObject("auxiliary"), true);
            FluidStack output = FluidStackHelper.getFluidStack(json.getAsJsonObject("output"), true);
            int brewTime = GsonHelper.getAsInt(json, "brewTime", 6000);
            return new BrewingRecipe(input, auxiliary, output, id, brewTime);
        }

        @Nullable
        @Override
        public BrewingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            FluidStack input = FluidStack.readFromPacket(buffer);
            FluidStack auxiliary = FluidStack.readFromPacket(buffer);
            FluidStack output = FluidStack.readFromPacket(buffer);
            int brewTime = buffer.readInt();
            return new BrewingRecipe(input, auxiliary, output, id, brewTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BrewingRecipe recipe) {
            recipe.input.writeToPacket(buffer);
            recipe.auxiliary.writeToPacket(buffer);
            recipe.output.writeToPacket(buffer);
            buffer.writeInt(recipe.brewTime);
        }
    }
}
