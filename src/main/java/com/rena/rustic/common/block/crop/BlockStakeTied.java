package com.rena.rustic.common.block.crop;

import com.rena.rustic.common.block.BlockRope;
import com.rena.rustic.core.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

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
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return STAKE_AABB;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);
        pLevel.setBlock(pPos, BlockInit.CROP_STAKE.get().defaultBlockState(), 3);
        pLevel.blockUpdated(pPos, BlockInit.CROP_STAKE.get());
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        List<ItemStack> stacks = super.getDrops(pState, pBuilder);
        stacks.add(new ItemStack(BlockInit.ROPE.get(), 1));
        return stacks;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH);
        pBuilder.add(WEST);
        pBuilder.add(SOUTH);
        pBuilder.add(EAST);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        boolean n = false;
        boolean w = false;
        boolean s = false;
        boolean e = false;

        BlockState state1 = pLevel.getBlockState(pCurrentPos.north());
        if (state1.getBlock() instanceof BlockRope && state1.getValue(BlockRope.AXIS) == Direction.Axis.Z) {
            n = true;
        }
        if (state1.getBlock() == BlockInit.GRAPE_LEAVES.get() && state1.getValue(BlockGrapeLeaves.AXIS) == Direction.Axis.Z) {
            n = true;
        }
        if (state1.getBlock() == this) {
            n = true;
        }
        state1 = pLevel.getBlockState(pCurrentPos.west());
        if (state1.getBlock() instanceof BlockRope && state1.getValue(BlockRope.AXIS) == Direction.Axis.X) {
            w = true;
        }
        if (state1.getBlock() == BlockInit.GRAPE_LEAVES.get() && state1.getValue(BlockGrapeLeaves.AXIS) == Direction.Axis.X) {
            w = true;
        }
        if (state1.getBlock() == this) {
            w = true;
        }
        state1 = pLevel.getBlockState(pCurrentPos.south());
        if (state1.getBlock() instanceof BlockRope && state1.getValue(BlockRope.AXIS) == Direction.Axis.Z) {
            s = true;
        }
        if (state1.getBlock() == BlockInit.GRAPE_LEAVES.get() && state1.getValue(BlockGrapeLeaves.AXIS) == Direction.Axis.Z) {
            s = true;
        }
        if (state1.getBlock() == this) {
            s = true;
        }
        state1 = pLevel.getBlockState(pCurrentPos.east());
        if (state1.getBlock() instanceof BlockRope && state1.getValue(BlockRope.AXIS) == Direction.Axis.X) {
            e = true;
        }
        if (state1.getBlock() == BlockInit.GRAPE_LEAVES.get() && state1.getValue(BlockGrapeLeaves.AXIS) == Direction.Axis.X) {
            e = true;
        }
        if (state1.getBlock() == this) {
            e = true;
        }

        return this.defaultBlockState().setValue(NORTH, n).setValue(WEST, w).setValue(SOUTH, s).setValue(EAST, e);
    }

}
