package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.block.fluid.FluidDrinkable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class FluidInit {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, RusticReborn.MOD_ID);

    public static final RegistryObject<FluidDrinkable> OLIVE_OIL_SOURCE = FLUIDS.register("olive_oil_still", () -> new FluidDrinkable.Source(FluidInit.OLIVE_PROPERTIES){
        @Override
        public void onDrank(@NotNull Level world, @NotNull Player player, @NotNull ItemStack stack, @NotNull FluidStack fluid) {
            player.getFoodData().eat(1, 0.4F);
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 1));
        }
    });
    public static final RegistryObject<FluidDrinkable> OLIVE_OIL_FLOWING = FLUIDS.register("olive_oil_flowing", () -> new FluidDrinkable.Flowing(FluidInit.OLIVE_PROPERTIES){
        @Override
        public void onDrank(@NotNull Level world, @NotNull Player player, @NotNull ItemStack stack, @NotNull FluidStack fluid) {
            player.getFoodData().eat(1, 0.4F);
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 1));
        }
    });
    public static final ForgeFlowingFluid.Properties OLIVE_PROPERTIES = new ForgeFlowingFluid.Properties(() -> OLIVE_OIL_SOURCE.get(), () -> OLIVE_OIL_FLOWING.get(), FluidAttributes.builder(RusticReborn.modLoc("block/fluids/olive_oil_still"), RusticReborn.modLoc("block/fluids/olive_oil_flow")).density(920).viscosity(2000).overlay(RusticReborn.modLoc("block/fluids/olive_oil_still"))).block(() -> FluidInit.OLIVE_OIL.get()).bucket(() -> ItemInit.OLIVE_OIL_BUCKET.get()).slopeFindDistance(2);
    public static final RegistryObject<LiquidBlock> OLIVE_OIL = BlockInit.BLOCKS.register("olive_oil", () -> new LiquidBlock(() -> OLIVE_OIL_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));

    public static final RegistryObject<FluidDrinkable> IRONBERRY_JUICE_STILL = FLUIDS.register("ironberry_juice_still", () -> new FluidDrinkable.Source(FluidInit.IRONBERRY_PROPERTIES){
        @Override
        public void onDrank(@NotNull Level world, @NotNull Player player, @NotNull ItemStack stack, @NotNull FluidStack fluid) {
            player.getFoodData().eat(1, 0.8F);
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 15, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 15, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 15, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 15, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 15, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 250, false, false));
        }
    });
    public static final RegistryObject<FluidDrinkable> IRONBERRY_JUICE_FLOWING = FLUIDS.register("ironberry_juice_flowing", () -> new FluidDrinkable.Flowing(FluidInit.IRONBERRY_PROPERTIES){
        @Override
        public void onDrank(@NotNull Level world, @NotNull Player player, @NotNull ItemStack stack, @NotNull FluidStack fluid) {
            player.getFoodData().eat(1, 0.8F);
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 15, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 15, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 15, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 15, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 15, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 250, false, false));
        }
    });
    public static final ForgeFlowingFluid.Properties IRONBERRY_PROPERTIES = new ForgeFlowingFluid.Properties(() -> IRONBERRY_JUICE_STILL.get(), () -> IRONBERRY_JUICE_FLOWING.get(), FluidAttributes.builder(RusticReborn.modLoc("block/fluids/ironberry_juice_still"), RusticReborn.modLoc("block/fluids/ironberry_juice_flow")).density(1100).viscosity(1100).overlay(RusticReborn.modLoc("block/fluids/ironberry_juice_still"))).block(() -> FluidInit.IRONBERRY_JUICE.get()).bucket(() -> ItemInit.IRONBERRY_JUICE_BUCKET.get());
    public static final RegistryObject<LiquidBlock> IRONBERRY_JUICE = BlockInit.BLOCKS.register("ironberry_juice", () -> new LiquidBlock(() -> IRONBERRY_JUICE_STILL.get(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));


    public static final RegistryObject<FluidDrinkable> APPLE_JUICE_SOURCE = FLUIDS.register("apple_juice_still", () -> new FluidDrinkable.Source(FluidInit.APPLE_JUICE_PROPERTIES){
        @Override
        public void onDrank(@NotNull Level world, @NotNull Player player, @NotNull ItemStack stack, @NotNull FluidStack fluid) {
            player.getFoodData().eat(1, 1.2F);
        }
    });
    public static final RegistryObject<FluidDrinkable> APPLE_JUICE_FLOWING = FLUIDS.register("apple_juice_flowing", () -> new FluidDrinkable.Flowing(FluidInit.APPLE_JUICE_PROPERTIES){
        @Override
        public void onDrank(@NotNull Level world, @NotNull Player player, @NotNull ItemStack stack, @NotNull FluidStack fluid) {
            player.getFoodData().eat(1, 1.2F);
        }
    });
    public static final ForgeFlowingFluid.Properties APPLE_JUICE_PROPERTIES = new ForgeFlowingFluid.Properties(() -> APPLE_JUICE_SOURCE.get(), () -> APPLE_JUICE_FLOWING.get(), FluidAttributes.builder(RusticReborn.modLoc("block/fluids/apple_juice_still"), RusticReborn.modLoc("block/fluids/apple_juice_flow")).density(1050).viscosity(1100).overlay(RusticReborn.modLoc("block/fluids/apple_juice_still"))).block(() -> FluidInit.APPLE_JUICE.get()).bucket(() -> ItemInit.APPLE_JUICE_BUCKET.get()).slopeFindDistance(2);
    public static final RegistryObject<LiquidBlock> APPLE_JUICE = BlockInit.BLOCKS.register("apple_juice", () -> new LiquidBlock(() -> APPLE_JUICE_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));





}
