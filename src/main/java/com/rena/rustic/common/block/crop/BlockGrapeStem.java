package com.rena.rustic.common.block.crop;

import com.rena.rustic.common.block.BlockRope;
import com.rena.rustic.core.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockGrapeStem extends Block implements IPlantable, BonemealableBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

    private static final VoxelShape[] CROPS_AABB = new VoxelShape[] {
            Shapes.create(0.375D, 0.0D, 0.375D, 0.625D, 0.25D, 0.625D),
            Shapes.create(0.375D, 0.0D, 0.375D, 0.625D, 0.5D, 0.625D),
            Shapes.create(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D),
            Shapes.create(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D) };

    public BlockGrapeStem(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    public int getMaxAge() {
        return 3;
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.CROP;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return CROPS_AABB[pState.getValue(AGE)];
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);

        if (pLevel.getRawBrightness(pPos.above(), 0) >= 9) {
            int i = pState.getValue(AGE);

            if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, pLevel, pPos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,
                        pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {
                    pLevel.setBlock(pPos, pState.setValue(AGE, (i + 1)), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            } else if (pLevel.getBlockState(pPos.above()).getBlock() == BlockInit.ROPE.get()
                    && pLevel.getBlockState(pPos.above()).getValue(BlockRope.AXIS) != Direction.Axis.Y) {
                float f = getGrowthChance(this, pLevel, pPos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,
                        pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {

                    Direction.Axis axis = pLevel.getBlockState(pPos.above()).getValue(BlockRope.AXIS);
                    pLevel.setBlock(pPos.above(),
                            BlockInit.GRAPE_LEAVES.get().defaultBlockState().setValue(BlockGrapeLeaves.AXIS, axis).setValue(BlockGrapeLeaves.DIST, 0), 3);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

    protected static float getGrowthChance(Block blockIn, Level worldIn, BlockPos pos) {
        return 7F;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < getMaxAge()
                || (pState.getValue(AGE) == getMaxAge() && pLevel.getBlockState(pPos.above()).getBlock() == BlockInit.ROPE.get()
                && pLevel.getBlockState(pPos.above()).getValue(BlockRope.AXIS) != Direction.Axis.Y);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        if (pState.getValue(AGE) < getMaxAge()) {
            int i = pState.getValue(AGE) + this.getBonemealAgeIncrease(pLevel);
            int j = this.getMaxAge();
            if (i > j) {
                i = j;
            }
            pLevel.setBlock(pPos, pState.setValue(AGE, i), 2);
        } else {
            Direction.Axis axis = pLevel.getBlockState(pPos.above()).getValue(BlockRope.AXIS);
            pLevel.setBlock(pPos.above(),
                    BlockInit.GRAPE_LEAVES.get().defaultBlockState().setValue(BlockGrapeLeaves.AXIS, axis), 3);
        }
    }

    protected int getBonemealAgeIncrease(Level worldIn) {
        return Mth.nextInt(worldIn.random, 2, 5);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return defaultBlockState();
    }
}
