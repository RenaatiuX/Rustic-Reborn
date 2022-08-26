package com.rena.rustic.common.block;

import com.rena.rustic.common.block.crop.BlockGrapeLeaves;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

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

        /*ItemStack stack = pPlayer.getItemInHand(pHand);
		if (!canPlaceBlockOnSide(pLevel, pPos.offset(side), side) && stack.getItem() == Item.BY_BLOCK.get(this)) {
			if (!this.isBlockSupported(pLevel, pPos, pState)) {
				this.dropBlock(pLevel, pPos, pState);
                return InteractionResult.SUCCESS;
			}

			int yOffset = 1;
			while (yOffset < 64 && pLevel.getBlockState(pPos.below(yOffset)).getBlock() == this) {
				if (pLevel.getBlockState(pPos.below(yOffset)).getValue(AXIS) != Direction.Axis.Y) {
                    return InteractionResult.CONSUME;
				}
				yOffset++;
			}
			if (canPlaceBlockAt(pLevel, pPos.below(yOffset))) {
                pLevel.setBlock(pPos.below(yOffset), pState.setValue(AXIS, Direction.Axis.Y), 3);
				if (!pPlayer.getAbilities().instabuild) {
                    pPlayer.getItemInHand(pHand).shrink(1);
				}
				SoundType soundType = getSoundType(pState, pLevel, pPos, pPlayer);
                pLevel.playLocalSound(pPos.getX(), pPos.getY() - yOffset, pPos.getZ(), soundType.getPlaceSound(),
						SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F,
						false);
                return InteractionResult.SUCCESS;
			}
		}*/

		return InteractionResult.CONSUME;



    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
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

    protected void dropBlock(Level worldIn, BlockPos pos, BlockState state) {
        //this.dropBlockAsItem(worldIn, pos, state, 0);
        worldIn.isEmptyBlock(pos);
        SoundType soundType = getSoundType(state, worldIn, pos, null);
        worldIn.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), soundType.getBreakSound(), SoundSource.BLOCKS,
                (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F, true);
    }

    public boolean isSideSupported(Level world, BlockPos pos, BlockState state, Direction facing) {
        BlockState testState = world.getBlockState(pos.relative(facing));

        if (facing == Direction.DOWN) {
            return false;
        }

        boolean isSame = testState.getBlock() == state.getBlock()
                && ((state.getValue(AXIS) == Direction.Axis.Y && facing.getAxis() == Direction.Axis.Y)
                || testState.getValue(AXIS) == state.getValue(AXIS));
        //boolean isSideSolid = world.isSideSolid(pos.relative(facing), facing.getOpposite(), false);

        return isSame; //|| isSideSolid;
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS);
        pBuilder.add(DANGLE);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pState.getValue(AXIS) != Direction.Axis.Y && pLevel.getBlockState(pCurrentPos.below()).getBlock() instanceof BlockRopeBase && !(pLevel.getBlockState(pCurrentPos.below()).getBlock() instanceof BlockGrapeLeaves)
                && pLevel.getBlockState(pCurrentPos.below()).getValue(BlockRope.AXIS) == Direction.Axis.Y) {
            return pState.setValue(DANGLE, true);
        }
        return pState.setValue(DANGLE, false);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction dir = null;
        return super.getStateForPlacement(pContext).setValue(AXIS, dir.getAxis());
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return switch (direction) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(AXIS)) {
                case X -> state.setValue(AXIS, Direction.Axis.Z);
                case Z -> state.setValue(AXIS, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }

    /*@Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        pState = this.getActualState(pState, pLevel, pPos);
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
    }*/

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
}
