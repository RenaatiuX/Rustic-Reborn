package com.rena.rustic.common.item;

import com.rena.rustic.common.block.crop.BlockStakeCrop;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ItemStakeCropSeed extends Item {

    private Supplier<BlockStakeCrop> crop;

    public ItemStakeCropSeed(Properties pProperties, Supplier<BlockStakeCrop> crop) {
        super(pProperties);
        this.crop = crop;
    }

    public BlockState getCropState() {
        return crop.get().defaultBlockState();
    }

    public BlockStakeCrop getCrop() {
        return crop.get();
    }
}
