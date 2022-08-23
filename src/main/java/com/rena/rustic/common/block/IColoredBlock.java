package com.rena.rustic.common.block;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IColoredBlock {

    @OnlyIn(Dist.CLIENT)
    public BlockColor getBlockColor();

    @OnlyIn(Dist.CLIENT)
    public ItemColor getItemColor();

}
