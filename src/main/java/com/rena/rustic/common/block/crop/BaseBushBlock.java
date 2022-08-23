package com.rena.rustic.common.block.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public abstract class BaseBushBlock extends BushBlock implements BonemealableBlock {

    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public BaseBushBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(getAgeProperty(), 0));
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public BlockState withAge(int age) {
        return defaultBlockState().setValue(getAgeProperty(), age);
    }

    public BlockState withMaxAge() {
        return defaultBlockState().setValue(getAgeProperty(), getMaxAge());
    }

    public boolean isMaxAge(BlockState state) {
        return state.getValue(getAgeProperty()) == getMaxAge();
    }

    public int getMaxAge() {
        return 3;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        int age = pState.getValue(getAgeProperty());
        if (age < getMaxAge() && pLevel.getRawBrightness(pPos.above(), 0) >= 9 && ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt(5) == 0)) {
            pLevel.setBlock(pPos, pState.setValue(getAgeProperty(), age + 1), Block.UPDATE_CLIENTS);
            ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(getAgeProperty());
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(getAgeProperty()) < getMaxAge();
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        int newAge = Math.min(getMaxAge(), pState.getValue(getAgeProperty()) + 1);
        pLevel.setBlock(pPos, pState.setValue(getAgeProperty(), newAge), Block.UPDATE_CLIENTS);
    }
}
