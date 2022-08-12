package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RusticReborn.MOD_ID);

    public static final RegistryObject<Item> BEE = ITEMS.register("bee", () -> new Item(new Item.Properties().tab(ModTabs.FARMING_TAB)));
    public static final RegistryObject<Item> HONEYCOMB = ITEMS.register("honeycomb", () -> new Item(new Item.Properties().tab(ModTabs.FARMING_TAB)));

    public static final RegistryObject<BucketItem> OLIVE_OIL_BUCKET = ITEMS.register("olive_oil_bucket", () -> new BucketItem(() -> FluidInit.OLIVE_OIL_SOURCE.get(), new Item.Properties().tab(ModTabs.FARMING_TAB).stacksTo(1)));
    public static final RegistryObject<BucketItem> IRONBERRY_JUICE_BUCKET = ITEMS.register("ironberry_juice_bucket", () -> new BucketItem(() -> FluidInit.IRONBERRY_JUICE_STILL.get(), new Item.Properties().tab(ModTabs.FARMING_TAB).stacksTo(1)));

}
