package com.rena.rustic.common.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class FluidStackHelper {


    public static FluidStack getFluidStack(JsonObject json, boolean readNbt){
        String fluidName = GsonHelper.getAsString(json, "fluid");
        Fluid fluid = getFluid(fluidName);
        if (readNbt && json.has("nbt")){
            CompoundTag nbt = CraftingHelper.getNBT(json.get("nbt"));
            CompoundTag tmp = new CompoundTag();
            if (nbt.contains("ForgeCaps"))
            {
                tmp.put("ForgeCaps", nbt.get("ForgeCaps"));
                nbt.remove("ForgeCaps");
            }

            tmp.put("Tag", nbt);
            tmp.putString("FluidName", fluidName);
            tmp.putInt("Amount", GsonHelper.getAsInt(json, "count", 1));
            return FluidStack.loadFluidStackFromNBT(tmp);
        }
        return new FluidStack(fluid, GsonHelper.getAsInt(json, "count", 1));
    }

    public static JsonElement toJson(FluidStack stack){
        JsonObject obj = new JsonObject();
        obj.addProperty("fluid", stack.getFluid().getRegistryName().toString());
        if (stack.getAmount() > 1){
            obj.addProperty("count", stack.getAmount());
        }
        if (stack.hasTag()){
            obj.addProperty("nbt", stack.getTag().toString());
        }
        return obj;
    }

    public static Fluid getFluid(String name){
        ResourceLocation key = new ResourceLocation(name);
        if (!ForgeRegistries.FLUIDS.containsKey(key)){
            throw new JsonSyntaxException("Unknown fluid '" + name + "'");
        }
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(key);
        return Objects.requireNonNull(fluid);
    }
}
