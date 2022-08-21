package com.rena.rustic.common.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackHandlerRustic extends ItemStackHandler {

    public ItemStackHandlerRustic(int size) {
        super(size);
    }

    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }
}
