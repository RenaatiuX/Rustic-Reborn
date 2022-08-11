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

    public static final RegistryObject<FluidDrinkable> OLIVE_OIL_SOURCE = FLUIDS.register("olive_oils_still", () -> new FluidDrinkable.Source(FluidInit.OLIVE_PROPERTIES){
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
    public static final ForgeFlowingFluid.Properties OLIVE_PROPERTIES = new ForgeFlowingFluid.Properties(() -> OLIVE_OIL_SOURCE.get(), () -> OLIVE_OIL_FLOWING.get(), FluidAttributes.builder(RusticReborn.modLoc("blocks/fluids/olive_oil_still"), RusticReborn.modLoc("blocks/fluids/olive_oil_flow")).density(920).viscosity(2000));
    public static final RegistryObject<Block> OLIVE_OIL = BlockInit.BLOCKS.register("olive_oil", () -> new LiquidBlock(() -> OLIVE_OIL_SOURCE.get(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));

    public static final RegistryObject<FluidDrinkable> IRONBERRY_JUICE_STILL = FLUIDS.register("olive_oils_still", () -> new FluidDrinkable.Source(FluidInit.IRONBERRY_PROPERTIES){
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
    public static final RegistryObject<FluidDrinkable> IRONBERRY_JUICE_FLOWING = FLUIDS.register("olive_oil_flowing", () -> new FluidDrinkable.Flowing(FluidInit.IRONBERRY_PROPERTIES){
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
    public static final ForgeFlowingFluid.Properties IRONBERRY_PROPERTIES = new ForgeFlowingFluid.Properties(() -> IRONBERRY_JUICE_STILL.get(), () -> IRONBERRY_JUICE_FLOWING.get(), FluidAttributes.builder(RusticReborn.modLoc("blocks/fluids/ironberry_juice_still"), RusticReborn.modLoc("blocks/fluids/ironberry_juice_flow")).density(1100).viscosity(1100));
    public static final RegistryObject<Block> IRONBERRY_JUICE = BlockInit.BLOCKS.register("ironberry_juice", () -> new LiquidBlock(() -> IRONBERRY_JUICE_STILL.get(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));



}
