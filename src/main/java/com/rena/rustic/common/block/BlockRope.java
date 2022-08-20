package com.rena.rustic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.block.state.BlockState;

import java.util.logging.Level;

public class BlockRope extends BlockRopeBase{
    public BlockRope(Properties properties) {
        super(properties);
    }

    /*
    @Override
	public boolean isSideSupported(World world, BlockPos pos, IBlockState state, EnumFacing facing) {
		IBlockState testState = world.getBlockState(pos.offset(facing));

		if (facing == EnumFacing.DOWN) {
			return false;
		}

		boolean isSame = testState.getBlock() == state.getBlock() && (testState.getValue(AXIS) == state.getValue(AXIS)
				|| (state.getValue(AXIS) == EnumFacing.Axis.Y && facing.getAxis() == EnumFacing.Axis.Y));
		boolean isSideSolid = world.isSideSolid(pos.offset(facing), facing.getOpposite(), false);
		boolean isTiedStake = testState.getBlock() == ModBlocks.STAKE_TIED;
		boolean isGrapeLeaves = testState.getBlock() == ModBlocks.GRAPE_LEAVES
				&& testState.getValue(BlockGrapeLeaves.AXIS) == state.getValue(AXIS);
		boolean isLattice = testState.getBlock() instanceof BlockLattice;

		return isSame || isSideSolid || isTiedStake || isGrapeLeaves || isLattice;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
		IBlockState testState = world.getBlockState(pos.offset(side.getOpposite()));

		if (side == EnumFacing.UP) {
			return canPlaceBlockOnSide(world, pos, EnumFacing.DOWN);
		}

		boolean isThis = testState.getBlock() == this && testState.getValue(AXIS) == side.getAxis();
		boolean isSideSolid = world.isSideSolid(pos.offset(side.getOpposite()), side, false);
		boolean isTiedStake = testState.getBlock() == ModBlocks.STAKE_TIED;
		boolean isGrapeLeaves = testState.getBlock() == ModBlocks.GRAPE_LEAVES
				&& testState.getValue(BlockGrapeLeaves.AXIS) == side.getAxis();
		boolean isLattice = testState.getBlock() instanceof BlockLattice;

		return isThis || isSideSolid || isTiedStake || isGrapeLeaves || isLattice;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (!worldIn.isRemote) {
			if (entityIn instanceof EntityArrow && isArrowInAABB(worldIn, pos, state, (EntityArrow) entityIn)) {
				this.dropBlock(worldIn, pos, state);
			}
		}
	}
     */

    /*protected boolean isArrowInAABB(Level worldIn, BlockPos pos, BlockState state, Arrow entity) {
        double xExp = (state.getValue(AXIS) == Direction.Axis.X) ? 0 : 0.125;
        double yExp = (state.getValue(AXIS) == Direction.Axis.Y) ? 0 : 0.125;
        double zExp = (state.getValue(AXIS) == Direction.Axis.Z) ? 0 : 0.125;

        AxisAlignedBB aabb = this.getBoundingBox(state, worldIn, pos);
        if (aabb != null) {
            aabb = aabb.expand(xExp, yExp, zExp).offset(pos);

            if (entity.getEntityBoundingBox() != null && aabb.intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }*/

    /*
    @Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side) {
		if (state.getValue(AXIS) == side.getAxis()) {
			return BlockFaceShape.CENTER_SMALL;
		}
		return BlockFaceShape.UNDEFINED;
	}
     */
}
