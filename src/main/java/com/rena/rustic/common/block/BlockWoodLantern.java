package com.rena.rustic.common.block;

import com.google.common.base.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockWoodLantern extends Block {

    public static final DirectionProperty FACING = DirectionProperty.create("facing", new Predicate<Direction>() {
        public boolean apply(@Nullable Direction p_apply_1_) { return p_apply_1_ != Direction.DOWN; }
    });

    protected static final VoxelShape LANTERN_AABB = Block.box(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
    protected static final VoxelShape LANTERN_NORTH_AABB = Block.box(0.25, 0.0, 0.25, 0.75, 1.0, 1.0);
    protected static final VoxelShape LANTERN_SOUTH_AABB = Block.box(0.25, 0.0, 0.0, 0.75, 1.0, 0.75);
    protected static final VoxelShape LANTERN_WEST_AABB = Block.box(0.25, 0.0, 0.25, 1.0, 1.0, 0.75);
    protected static final VoxelShape LANTERN_EAST_AABB = Block.box(0.0, 0.0, 0.25, 0.75, 1.0, 0.75);

    public BlockWoodLantern(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch ((Direction) pState.getValue(FACING)) {
            case EAST: return LANTERN_EAST_AABB;
            case WEST: return LANTERN_WEST_AABB;
            case SOUTH: return LANTERN_SOUTH_AABB;
            case NORTH: return LANTERN_NORTH_AABB;
            default: return LANTERN_AABB;
        }
    }

    /*
    @Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		for (EnumFacing facing : EnumFacing.VALUES) {
			if (this.canPlaceAt(worldIn, pos, facing)) {
				return true;
			}
		}
		return false;
	}

	private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
		BlockPos blockpos = pos.offset(facing.getOpposite());
		if (facing.getAxis().isHorizontal()) {
			return worldIn.isSideSolid(blockpos, facing, true);
		} else {
			IBlockState state = worldIn.getBlockState(blockpos);
			BlockFaceShape faceShape = state.getBlockFaceShape(worldIn, blockpos, facing);
			if (faceShape == BlockFaceShape.SOLID || faceShape == BlockFaceShape.CENTER || faceShape == BlockFaceShape.CENTER_BIG || faceShape == BlockFaceShape.CENTER_SMALL) {
				return true;
			}
			if (facing == EnumFacing.UP) {
				return state.getBlock().canPlaceTorchOnTop(state, worldIn, blockpos);
			}
			return false;
		}
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (this.canPlaceAt(worldIn, pos, facing)) {
			if (facing == EnumFacing.DOWN)
				return this.getDefaultState().withProperty(FACING, EnumFacing.UP);
			else
				return this.getDefaultState().withProperty(FACING, facing);
		} else {
			for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
				if (worldIn.isSideSolid(pos.offset(enumfacing.getOpposite()), enumfacing, true)) {
					return this.getDefaultState().withProperty(FACING, enumfacing);
				}
			}
			return this.getDefaultState();
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.checkForDrop(worldIn, pos, state);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		this.onNeighborChangeInternal(worldIn, pos, state);
	}

	protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.checkForDrop(worldIn, pos, state)) {
			return true;
		} else {
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
			EnumFacing.Axis axis = enumfacing.getAxis();
			boolean flag = false;

			if (axis.isHorizontal()) {
				if (!this.canPlaceAt(worldIn, pos, enumfacing)) flag = true;
			} else if (!this.canPlaceAt(worldIn, pos, EnumFacing.UP) && !this.canPlaceAt(worldIn, pos, EnumFacing.DOWN)) {
				flag = true;
			}

			if (flag) {
				this.dropBlockAsItem(worldIn, pos, state, 0);
				worldIn.setBlockToAir(pos);
				return true;
			} else {
				return false;
			}
		}
	}

	protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getBlock() == this) {
			EnumFacing facing = (EnumFacing) state.getValue(FACING);
			if (this.canPlaceAt(worldIn, pos, facing)) {
				return true;
			} else if ((facing == EnumFacing.UP) && this.canPlaceAt(worldIn, pos, EnumFacing.DOWN)) {
				return true;
			}
		}
		if (worldIn.getBlockState(pos).getBlock() == this) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		EnumFacing enumfacing = (EnumFacing) stateIn.getValue(FACING);
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.5D + (rand.nextFloat() * (6.0D / 16.0D));
		double d2 = (double) pos.getZ() + 0.5D;
		double rx = (double) ((rand.nextFloat() * 0.6F) - 0.3F);
		double rz = (double) ((rand.nextFloat() * 0.6F) - 0.3F);
		worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + rx, d1, d2 + rz, 0.0D, 0.0D, 0.0D, new int[0]);
		worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + rx, d1, d2 + rz, 0.0D, 0.0D, 0.0D, new int[0]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		EnumFacing facing = rot.rotate((EnumFacing) state.getValue(FACING));
		if (facing == EnumFacing.DOWN) facing = EnumFacing.UP;
		return state.withProperty(FACING, facing);
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState();
		switch (meta) {
			case 1: return iblockstate.withProperty(FACING, EnumFacing.EAST);
			case 2: return iblockstate.withProperty(FACING, EnumFacing.WEST);
			case 3: return iblockstate.withProperty(FACING, EnumFacing.SOUTH);
			case 4: return iblockstate.withProperty(FACING, EnumFacing.NORTH);
			case 5:
			default:
				return iblockstate;
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		switch ((EnumFacing) state.getValue(FACING)) {
		case EAST: return 1;
		case WEST: return 2;
		case SOUTH: return 3;
		case NORTH: return 4;
		case DOWN:
		case UP:
		default:
			return 0;
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side) {
		return side.getAxis().isVertical() ? BlockFaceShape.CENTER_BIG : BlockFaceShape.UNDEFINED;
	}
     */
}
