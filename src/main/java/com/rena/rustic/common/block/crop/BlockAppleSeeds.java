package com.rena.rustic.common.block.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class BlockAppleSeeds extends CropBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 1);

    private static final VoxelShape[] CROPS_AABB = new VoxelShape[] {
            Block.box(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
            Block.box(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D) };

    public BlockAppleSeeds(Properties p_52247_) {
        super(p_52247_);
    }

    @Override
    public int getMaxAge() {
        return 1;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return CROPS_AABB[pState.getValue(AGE)];
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
    }
}
