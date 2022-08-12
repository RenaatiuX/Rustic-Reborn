package com.rena.rustic.common.item;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.block.BlockVase;
import com.rena.rustic.core.BlockInit;
import com.rena.rustic.core.ModTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    public VaseItem(Block b) {
        super(b, new Item.Properties().tab(ModTabs.FARMING_TAB));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(new TranslatableComponent(RusticReborn.MOD_ID + ".tooltip.vase.variant", getVariant(pStack)));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
