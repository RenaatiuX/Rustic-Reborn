package com.rena.rustic.common.block.crop;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockCropStake extends Block {

    protected static final VoxelShape STAKE_SELECTION_AABB = Block.box(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
    protected static final VoxelShape STAKE_AABB = Block.box(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);

    public BlockCropStake(Properties p_49795_) {
        super(p_49795_);
    }


}
