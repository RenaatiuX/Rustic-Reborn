package com.rena.rustic.common.block.crop;

import com.rena.rustic.core.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockStakeTied extends Block {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");

    protected static final VoxelShape KNOT_AABB = Shapes.create(0.3125F, 0.0F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
    protected static final VoxelShape STAKE_AABB = Shapes.create(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);

    public BlockStakeTied(Properties p_49795_) {
        super(p_49795_);
        registerDefaultState(this.stateDefinition.any().setValue(NORTH,  false).setValue(WEST, false).setValue(SOUTH, false).setValue(EAST,  false));

    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return KNOT_AABB;
    }

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return STAKE_AABB;
    }

    /*@Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);
        pLevel.setBlock(pPos, BlockInit.CROP_STAKE.getDefaultState(), 3);
        dropBlockAsItem(pLevel, pPos, pState, 0);
        pLevel.scheduleUpdate(pPos, BlockInit.CROP_STAKE, 1);
    }*/
}
