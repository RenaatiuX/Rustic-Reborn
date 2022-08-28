package com.rena.rustic.common.block;

import com.rena.rustic.common.block.crop.BlockGrapeLeaves;
import com.rena.rustic.core.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class BlockRope extends BlockRopeBase{
    public BlockRope(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public boolean isSideSupported(Level world, BlockPos pos, BlockState state, Direction facing) {
        BlockState testState = world.getBlockState(pos.relative(facing));

        if (facing == Direction.DOWN) {
            return false;
        }

        boolean isSame = testState.getBlock() == state.getBlock() && (testState.getValue(AXIS) == state.getValue(AXIS)
                || (state.getValue(AXIS) == Direction.Axis.Y && facing.getAxis() == Direction.Axis.Y));
        boolean isSideSolid = Block.isFaceFull(world.getBlockState(pos.relative(facing)).getShape(world, pos), facing.getOpposite());
        boolean isTiedStake = testState.getBlock() == BlockInit.STAKE_TIED.get();
        boolean isGrapeLeaves = testState.getBlock() == BlockInit.GRAPE_LEAVES.get()
                && testState.getValue(BlockGrapeLeaves.AXIS) == state.getValue(AXIS);
        boolean isLattice = testState.getBlock() instanceof BlockLattice;

        return isSame || isSideSolid || isTiedStake || isGrapeLeaves || isLattice;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (!pLevel.isClientSide) {
            if (pEntity instanceof Arrow && isArrowInAABB(pLevel, pPos, pState, (Arrow) pEntity)) {
                this.dropBlock(pLevel, pPos, pState);
            }
        }
    }

    protected boolean isArrowInAABB(Level worldIn, BlockPos pos, BlockState state, Arrow entity) {
        double xExp = (state.getValue(AXIS) == Direction.Axis.X) ? 0 : 0.125;
        double yExp = (state.getValue(AXIS) == Direction.Axis.Y) ? 0 : 0.125;
        double zExp = (state.getValue(AXIS) == Direction.Axis.Z) ? 0 : 0.125;

        List<AABB> aabbs = state.getShape(worldIn, pos).toAabbs();
        if (aabbs != null && aabbs.size() > 0) {
            for (AABB aabb : aabbs) {
                aabb = aabb.move(xExp, yExp, zExp).move(pos);

                if (entity.getBoundingBox() != null && aabb.intersects(entity.getBoundingBox())) {
                    return true;
                }
            }
        }
        return false;
    }
}
