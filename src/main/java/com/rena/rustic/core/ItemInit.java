package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.item.ChiliPepperItem;
import com.rena.rustic.common.item.ItemStakeCropSeed;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RusticReborn.MOD_ID);

    public static final RegistryObject<Item> BEE = ITEMS.register("bee", () -> new Item(new Item.Properties().tab(ModTabs.FARMING_TAB)));
    public static final RegistryObject<Item> HONEYCOMB = ITEMS.register("honeycomb", () -> new Item(new Item.Properties().tab(ModTabs.FARMING_TAB)));

    public static final RegistryObject<BucketItem> OLIVE_OIL_BUCKET = ITEMS.register("olive_oil_bucket", () -> new BucketItem(() -> FluidInit.OLIVE_OIL_SOURCE.get(), new Item.Properties().tab(ModTabs.FARMING_TAB).stacksTo(1)));
    public static final RegistryObject<BucketItem> IRONBERRY_JUICE_BUCKET = ITEMS.register("ironberry_juice_bucket", () -> new BucketItem(() -> FluidInit.IRONBERRY_JUICE_STILL.get(), new Item.Properties().tab(ModTabs.FARMING_TAB).stacksTo(1)));
    public static final RegistryObject<BucketItem> APPLE_JUICE_BUCKET = ITEMS.register("apple_juice_bucket", () -> new BucketItem(() -> FluidInit.APPLE_JUICE_SOURCE.get(), new Item.Properties().tab(ModTabs.FARMING_TAB).stacksTo(1)));

    public static final RegistryObject<BucketItem> WILDBERRY_JUICE_BUCKET = ITEMS.register("wildberry_juice_bucket", () -> new BucketItem(() -> FluidInit.WILDBERRY_JUICE_SOURCE.get(), new Item.Properties().tab(ModTabs.FARMING_TAB).stacksTo(1)));


    //Item
    public static final RegistryObject<Item> CHILI_PEPPER = ITEMS.register("chili_pepper",
            () -> new ChiliPepperItem(new Item.Properties().tab(ModTabs.FARMING_TAB).food(new FoodProperties.Builder().nutrition(3).saturationMod(0.4F).build())));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato",
            () -> new Item(new Item.Properties().tab(ModTabs.FARMING_TAB).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).build())));

    public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds", ()-> new ItemStakeCropSeed(new Item.Properties().tab(ModTabs.FARMING_TAB), BlockInit.TOMATO_CROP::get));
    public static final RegistryObject<Item> CHILI_PEPPER_SEEDS = ITEMS.register("chili_pepper_seeds", ()-> new ItemStakeCropSeed(new Item.Properties().tab(ModTabs.FARMING_TAB), BlockInit.CHILI_CROP::get));

    public static final RegistryObject<Item> OLIVES = ITEMS.register("olives",
            () -> new Item(new Item.Properties().tab(ModTabs.FARMING_TAB).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.4F)
                    .effect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1, false, false), 0.95F).build()).stacksTo(24)));

    public static final RegistryObject<Item> IRONBERRIES = ITEMS.register("ironberries",
            ( )-> new Item(new Item.Properties().tab(ModTabs.FARMING_TAB).food(new FoodProperties.Builder().nutrition(2).saturationMod(0.4F)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 15, false, false), 1.0F)
                    .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 15, false, false), 1.0F)
                    .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 15, false, false), 1.0F)
                    .effect(() -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 15, false, false), 1.0F)
                    .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 200, 15, false, false), 1.0F)
                    .effect(() -> new MobEffectInstance(MobEffects.JUMP, 200, 250, false, false), 1.0F).build()).stacksTo(16)));

    public static final RegistryObject<Item> WILDBERRIES  = ITEMS.register("wildberries",
            () -> new ItemNameBlockItem(BlockInit.WILDBERRIES.get(), (new Item.Properties().tab(ModTabs.FARMING_TAB).food(new FoodProperties.Builder().nutrition(2).saturationMod(0.5F).build()).stacksTo(16))));

    public static final RegistryObject<Item> GRAPES  = ITEMS.register("grapes",
            () -> new Item(new Item.Properties().tab(ModTabs.FARMING_TAB).food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build()).stacksTo(16)));
}
