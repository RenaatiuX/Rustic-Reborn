package com.rena.rustic.common.block.crop;

import com.rena.rustic.core.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class BlockAppleSeeds extends Block implements IPlantable, BonemealableBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 1);

    private static final VoxelShape[] CROPS_AABB = new VoxelShape[] {
            Block.box(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
            Block.box(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D) };

    public BlockAppleSeeds(Properties properties) {
        super(properties);
    }


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
        if (pLevel.getRawBrightness(pPos.above(), 0) >= 9) {
            int i = pState.getValue(AGE);
            float f = getGrowthChance(this, pLevel, pPos);

            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,
                    pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {
                if (i < this.getMaxAge()) {
                    pLevel.setBlock(pPos, pState.setValue(AGE, (i + 1)), 2);
                } else {
                    pLevel.setBlock(pPos, BlockInit.APPLE_SAPLING.get().defaultBlockState(), 3);
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
            }
        }
    }

    protected static float getGrowthChance(Block blockIn, Level worldIn, BlockPos pos) {
        return 7F;
    }


    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.PLAINS;
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return defaultBlockState();
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        if (pState.getValue(AGE) < getMaxAge()) {
            pLevel.setBlock(pPos, pState.setValue(AGE, getMaxAge()), 2);
        } else {
            pLevel.setBlock(pPos, BlockInit.APPLE_SAPLING.get().defaultBlockState(), 3);
        }
    }
}
