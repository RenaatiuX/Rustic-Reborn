package com.rena.rustic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockRopeBase extends Block {

    public static final EnumProperty<Direction.Axis> AXIS = EnumProperty.create("axis",
            Direction.Axis.class);

    public static final BooleanProperty DANGLE = BooleanProperty.create("dangle");

    protected static final VoxelShape Y_AABB = Shapes.box(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
    protected static final VoxelShape X_AABB = Shapes.box(0.0, 0.4375F, 0.4375F, 1.0F, 0.5625F, 0.5625F);
    protected static final VoxelShape Z_AABB = Shapes.box(0.4375F, 0.4375F, 0.0F, 0.5625F, 0.5625F, 1.0F);
    protected static final VoxelShape X_DANGLE_AABB = Shapes.box(0.0, 0.0F, 0.4375F, 1.0F, 0.5625F, 0.5625F);
    protected static final VoxelShape Z_DANGLE_AABB = Shapes.box(0.4375F, 0.0F, 0.0F, 0.5625F, 0.5625F, 1.0F);

    public BlockRopeBase(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        /*
        ItemStack stack = player.getHeldItem(hand);
		if (!canPlaceBlockOnSide(world, pos.offset(side), side) && stack.getItem() == Item.getItemFromBlock(this)) {
			if (!this.isBlockSupported(world, pos, state)) {
				this.dropBlock(world, pos, state);
				return true;
			}

			int yOffset = 1;
			while (yOffset < 64 && world.getBlockState(pos.down(yOffset)).getBlock() == this) {
				if (world.getBlockState(pos.down(yOffset)).getValue(AXIS) != EnumFacing.Axis.Y) {
					return false;
				}
				yOffset++;
			}
			if (canPlaceBlockAt(world, pos.down(yOffset))) {
				world.setBlockState(pos.down(yOffset), state.withProperty(AXIS, EnumFacing.Axis.Y), 3);
				if (!player.capabilities.isCreativeMode) {
					player.getHeldItem(hand).shrink(1);
				}
				SoundType soundType = getSoundType(state, world, pos, player);
				world.playSound(pos.getX(), pos.getY() - yOffset, pos.getZ(), soundType.getPlaceSound(),
						SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F,
						false);
				return true;
			}
		}

		return false;
         */
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    /*
    @Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
		IBlockState testState = world.getBlockState(pos.offset(side.getOpposite()));

		if (side == EnumFacing.UP) {
			return canPlaceBlockOnSide(world, pos, EnumFacing.DOWN);
		}

		boolean isThis = testState.getBlock() == this && testState.getValue(AXIS) == side.getAxis();
		boolean isSideSolid = world.isSideSolid(pos.offset(side.getOpposite()), side, false);

		return isThis || isSideSolid;
	}
     */

    /*protected void dropBlock(Level worldIn, BlockPos pos, BlockState state) {
        this.dropBlockAsItem(worldIn, pos, state, 0);
        worldIn.isEmptyBlock(pos);
        SoundType soundType = getSoundType(state, worldIn, pos, null);
        worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), soundType.getBreakSound(), SoundEvents.BLOCKS,
                (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F, true);
    }

    /*@Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        Direction dir = null;
        if (pFromPos.getX() != pPos.getX()) {
            dir = (pFromPos.getX() - pPos.getX()) < 0 ? Direction.WEST : Direction.EAST;
        } else if (pFromPos.getY() != pPos.getY()) {
            dir = (pFromPos.getY() - pPos.getY()) < 0 ? Direction.DOWN : Direction.UP;
        } else if (pFromPos.getZ() != pPos.getZ()) {
            dir = (pFromPos.getZ() - pPos.getZ()) < 0 ? Direction.NORTH : Direction.SOUTH;
        }
        if (dir != null) {
            if ((pState.getValue(AXIS) == dir.getAxis())
                    && !this.isSideSupported(pLevel, pPos, pState, dir)) {
                if (dir == Direction.UP) {
                    this.dropBlock(pLevel, pPos, pState);
                } else if (!this.isBlockSupported(pLevel, pPos, pState)) {
                    this.dropBlock(pLevel, pPos, pState);
                }
            }
        }
    }

    /*public boolean isSideSupported(Level world, BlockPos pos, BlockState state, Direction facing) {
        BlockState testState = world.getBlockState(pos.relative(facing));

        if (facing == Direction.DOWN) {
            return false;
        }

        boolean isSame = testState.getBlock() == state.getBlock()
                && ((state.getValue(AXIS) == Direction.Axis.Y && facing.getAxis() == Direction.Axis.Y)
                || testState.getValue(AXIS) == state.getValue(AXIS));
        boolean isSideSolid = world.isSideSolid(pos.relative(facing), facing.getOpposite(), false);

        return isSame || isSideSolid;
    }

    public boolean isBlockSupported(Level world, BlockPos pos, BlockState state) {
        if (state.getValue(AXIS) == Direction.Axis.X) {
            return this.isSideSupported(world, pos, state, Direction.WEST)
                    && this.isSideSupported(world, pos, state, Direction.EAST);
        } else if (state.getValue(AXIS) == Direction.Axis.Y) {
            return this.isSideSupported(world, pos, state, Direction.UP);
        } else if (state.getValue(AXIS) == Direction.Axis.Z) {
            return this.isSideSupported(world, pos, state, Direction.NORTH)
                    && this.isSideSupported(world, pos, state, Direction.SOUTH);
        }
        return false;
    }

    /*
    @Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AXIS, DANGLE });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Y;
		//boolean supported = false;
		int i = meta & 3;

		if (i == 0) {
			enumfacing$axis = EnumFacing.Axis.Y;
		} else if (i == 1) {
			enumfacing$axis = EnumFacing.Axis.X;
		} else if (i == 2) {
			enumfacing$axis = EnumFacing.Axis.Z;
		}

		return this.getDefaultState().withProperty(AXIS, enumfacing$axis);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis) state.getValue(AXIS);

		if (enumfacing$axis == EnumFacing.Axis.X) {
			i = 1;
		} else if (enumfacing$axis == EnumFacing.Axis.Z) {
			i = 2;
		}

		return i;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state.getValue(AXIS) != EnumFacing.Axis.Y && world.getBlockState(pos.down()).getBlock() instanceof BlockRopeBase && !(world.getBlockState(pos.down()).getBlock() instanceof BlockGrapeLeaves)
				&& world.getBlockState(pos.down()).getValue(BlockRope.AXIS) == EnumFacing.Axis.Y) {
			return state.withProperty(DANGLE, true);
		}
		return state.withProperty(DANGLE, false);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(AXIS,
				facing.getAxis());
	}
     */

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        switch (pRotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch ((Direction.Axis) pState.getValue(AXIS)) {
                    case X:
                        return pState.setValue(AXIS, Direction.Axis.Z);
                    case Z:
                        return pState.setValue(AXIS, Direction.Axis.X);
                    default:
                        return pState;
                }

            default:
                return pState;
        }
    }

    /*@Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        pState = this.getActualState(state, source, pos);
        switch (pState.getValue(AXIS)) {
            case Y:
                return Y_AABB;
            case X:
                if (pState.getValue(DANGLE)) {
                    return X_DANGLE_AABB;
                }
                return X_AABB;
            case Z:
                if (pState.getValue(DANGLE)) {
                    return Z_DANGLE_AABB;
                }
                return Z_AABB;
        }
        return Y_AABB;
    }

    /*
    @Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }
     */
}
