package com.rena.rustic.common.item;

import com.rena.rustic.common.block.crop.BlockStakeCrop;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

public class ItemStakeCropSeed extends Item {

    private BlockStakeCrop crop;

    public ItemStakeCropSeed(Properties pProperties, BlockStakeCrop crop) {
        super(pProperties);
        this.crop = crop;
    }

    public BlockState getCropState() {
        return crop.defaultBlockState();
    }

    public BlockStakeCrop getCrop() {
        return crop;
    }
}
