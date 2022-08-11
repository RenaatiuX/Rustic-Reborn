package com.rena.rustic.common.item;

import com.rena.rustic.common.block.BlockVase;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class VaseItem extends BlockItem {
    public static final int getVariant(ItemStack stack){
        if (stack.hasTag() && stack.getTag().contains("variant")){
            return stack.getTag().getInt("variant");
        }

        return -1;
    }

    public static final void setVariant(ItemStack stack, int variant){
        if (variant < BlockVase.MIN_VARIANT || variant > BlockVase.MAX_VARIANT)return;
        stack.getOrCreateTag().putInt("variant", variant);
    }

    public VaseItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }


}
