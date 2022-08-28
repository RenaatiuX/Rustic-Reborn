package com.rena.rustic.common.block.crop;

import com.google.common.base.Predicate;
import com.rena.rustic.common.block.BlockLattice;
import com.rena.rustic.common.block.BlockRope;
import com.rena.rustic.common.block.BlockRopeBase;
import com.rena.rustic.core.BlockInit;
import com.rena.rustic.core.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockGrapeLeaves extends BlockRopeBase implements BonemealableBlock {

    public static final BooleanProperty GRAPES = BooleanProperty.create("grapes");
    public static final IntegerProperty DIST = IntegerProperty.create("distance", 0, 1);

    public static final EnumProperty<Direction.Axis> AXIS = EnumProperty.<Direction.Axis>create("axis",
            Direction.Axis.class, axis -> axis != Direction.Axis.Y);

    public static final VoxelShape BRANCH_Z_AABB = Shapes.create(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);
    public static final VoxelShape BRANCH_X_AABB = Shapes.create(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);

    public BlockGrapeLeaves(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X).setValue(GRAPES, false).setValue(DIST, 0));
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        if (pState.getValue(GRAPES)) {
            stacks.add(new ItemStack(ItemInit.GRAPES.get()));
        }
        return stacks;
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        Direction.Axis axis = pState.getValue(AXIS);
        super.destroy(pLevel, pPos, pState);
        pLevel.setBlock(pPos, BlockInit.ROPE.get().defaultBlockState().setValue(BlockRope.AXIS, axis), 3);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pState.getValue(GRAPES)) {
            pLevel.setBlock(pPos, pState.setValue(GRAPES, false), 3);
            Block.popResource(pLevel, pPos, new ItemStack(ItemInit.GRAPES.get(), pLevel.random.nextInt(2) + 1));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(DIST) < 1) {
            return Shapes.block();
        }
        if (pState.getValue(AXIS) == Direction.Axis.Z) {
            return BRANCH_Z_AABB;
        } else {
            return BRANCH_X_AABB;
        }
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return false;
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
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
                    && pLevel.isEmptyBlock(pFromPos)) {
                if (dir == Direction.DOWN) {
                    this.dropBlock(pLevel, pPos, pState);
                } else if (!this.isBlockSupported(pLevel, pPos, pState)) {
                    this.dropBlock(pLevel, pPos, pState);
                }
            }
        }
    }

    @Override
    public boolean isSideSupported(Level world, BlockPos pos, BlockState state, Direction facing) {
        BlockState testState = world.getBlockState(pos.relative(facing));

        boolean isSame = testState.getBlock() == state.getBlock() && (testState.getValue(AXIS) == state.getValue(AXIS));
        boolean isRope = testState.getBlock() == BlockInit.ROPE.get() && state.getValue(AXIS) == testState.getValue(BlockRope.AXIS);
        boolean isSideSolid = Block.isFaceFull(world.getBlockState(pos.relative(facing)).getShape(world, pos), facing.getOpposite());
        boolean isTiedStake = testState.getBlock() == BlockInit.STAKE_TIED.get();
        boolean isLattice = testState.getBlock() instanceof BlockLattice;

        return isSame || isRope || isSideSolid || isTiedStake || isLattice;
    }

    @Override
    public boolean isBlockSupported(Level world, BlockPos pos, BlockState state) {
        if (state.getValue(AXIS) == Direction.Axis.X) {
            return this.isSideSupported(world, pos, state, Direction.WEST) && this.isSideSupported(world, pos, state, Direction.EAST);
        } else if (state.getValue(AXIS) == Direction.Axis.Z) {
            return this.isSideSupported(world, pos, state, Direction.NORTH) && this.isSideSupported(world, pos, state, Direction.SOUTH);
        }
        return false;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        if (!this.isBlockSupported(pLevel, pPos, pState)) {
            this.dropBlock(pLevel, pPos, pState);
        }

        if (pLevel.getRawBrightness(pPos.above(), 0) >= 9) {
            int i = pState.getValue(DIST);

            if (i > 0 && !pState.getValue(GRAPES) && pLevel.isEmptyBlock(pPos.below())) {
                float f = getGrowthChance(this, pLevel, pPos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,
                        pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {
                    pLevel.setBlock(pPos, pState.setValue(GRAPES, true), 3);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            } else if (i < 1 && canSpread(pLevel, pPos, pState)) {
                float f = getGrowthChance(this, pLevel, pPos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,
                        pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {
                    spread(pLevel, pPos, pState);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

    protected static float getGrowthChance(Block blockIn, Level worldIn, BlockPos pos) {
        return 3F;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        if (pState.getValue(DIST) > 0) {
            return !pState.getValue(GRAPES) && pLevel.getBlockState(pPos.below()).isAir();
        }
        return canSpread(pLevel, pPos, pState);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        if (pState.getValue(DIST) > 0) {
            pLevel.setBlock(pPos, pState.setValue(GRAPES, true), 3);
        } else {
            spread(pLevel, pPos, pState);
        }
    }

    public boolean canSpread(BlockGetter world, BlockPos pos, BlockState state) {
        if (state.getValue(DIST) == 0) {
            switch (state.getValue(AXIS)) {
                case X:
                    return (world.getBlockState(pos.west()).getBlock() == BlockInit.ROPE.get()
                            && world.getBlockState(pos.west()).getValue(BlockRope.AXIS) == state.getValue(AXIS))
                            || (world.getBlockState(pos.east()).getBlock() == BlockInit.ROPE.get()
                            && world.getBlockState(pos.east()).getValue(BlockRope.AXIS) == state.getValue(AXIS));
                case Z:
                    return (world.getBlockState(pos.north()).getBlock() == BlockInit.ROPE.get()
                            && world.getBlockState(pos.north()).getValue(BlockRope.AXIS) == state.getValue(AXIS))
                            || (world.getBlockState(pos.south()).getBlock() == BlockInit.ROPE.get()
                            && world.getBlockState(pos.south()).getValue(BlockRope.AXIS) == state.getValue(AXIS));
                default:
                    return false;
            }
        }
        return false;
    }

    public void spread(Level world, BlockPos pos, BlockState state) {
        if (state.getValue(DIST) < 1) {
            switch (state.getValue(AXIS)) {
                case X:
                    boolean westRope = world.getBlockState(pos.west()).getBlock() == BlockInit.ROPE.get()
                            && world.getBlockState(pos.west()).getValue(BlockRope.AXIS) == state.getValue(AXIS);
                    boolean eastRope = world.getBlockState(pos.east()).getBlock() == BlockInit.ROPE.get()
                            && world.getBlockState(pos.east()).getValue(BlockRope.AXIS) == state.getValue(AXIS);
                    if (westRope && eastRope) {
                        spreadToValidRope(world, pos, (world.random.nextFloat() < 0.5) ? pos.west() : pos.east(), state);
                    } else if (westRope) {
                        spreadToValidRope(world, pos, pos.west(), state);
                    } else if (eastRope) {
                        spreadToValidRope(world, pos, pos.east(), state);
                    }
                    break;
                case Z:
                    boolean northRope = world.getBlockState(pos.north()).getBlock() == BlockInit.ROPE.get()
                            && world.getBlockState(pos.north()).getValue(BlockRope.AXIS) == state.getValue(AXIS);
                    boolean southRope = world.getBlockState(pos.south()).getBlock() == BlockInit.ROPE.get()
                            && world.getBlockState(pos.south()).getValue(BlockRope.AXIS) == state.getValue(AXIS);
                    if (northRope && southRope) {
                        spreadToValidRope(world, pos, (world.random.nextFloat() < 0.5) ? pos.north() : pos.south(), state);
                    } else if (northRope) {
                        spreadToValidRope(world, pos, pos.north(), state);
                    } else if (southRope) {
                        spreadToValidRope(world, pos, pos.south(), state);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void spreadToValidRope(Level world, BlockPos origPos, BlockPos newPos, BlockState state) {
        Direction.Axis axis = world.getBlockState(newPos).getValue(BlockRope.AXIS);
        world.setBlock(newPos,
                defaultBlockState().setValue(AXIS, axis).setValue(DIST, 1), 3);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS);
        pBuilder.add(DANGLE);
        pBuilder.add(DIST);
        pBuilder.add(GRAPES);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pLevel.getBlockState(pCurrentPos.below()).getBlock() instanceof BlockRope
                && pLevel.getBlockState(pCurrentPos.below()).getValue(BlockRope.AXIS) == Direction.Axis.Y) {
            return pState.setValue(DANGLE, true);
        }
        return pState.setValue(DANGLE, false);
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState();
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return switch (pRotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch ( pState.getValue(AXIS)) {
                case X -> pState.setValue(AXIS, Direction.Axis.Z);
                case Z -> pState.setValue(AXIS, Direction.Axis.X);
                default -> pState;
            };
            default -> pState;
        };
    }
}
