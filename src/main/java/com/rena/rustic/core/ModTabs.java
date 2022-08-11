package com.rena.rustic.core;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class ModTabs {

    public static final CreativeModeTab FARMING_TAB = new ModItemGroup("farming_tab", BlockInit.VASE::get);

    public static final class ModItemGroup extends CreativeModeTab {
        private final Supplier<ItemLike> item;
        public ModItemGroup(String label, Supplier<ItemLike> item) {
            super(label);
            this.item = item;
        }

        @Override
        public ItemStack makeIcon() {
            return item.get().asItem().getDefaultInstance();
        }
    }
}
