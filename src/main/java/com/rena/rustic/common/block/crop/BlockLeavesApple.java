package com.rena.rustic.common.block.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;
import java.util.Random;

import static com.rena.rustic.common.block.crop.BlockHerbBase.getGrowthChance;

public class BlockLeavesApple extends LeavesBlock implements BonemealableBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("apple_age", 0, 3);

    public BlockLeavesApple(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    public int getMaxAge() {
        return 3;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        int i = pState.getValue(AGE);

        if (i < this.getMaxAge() && isAirAdjacent(pLevel, pPos, pState)) {
            float f = getGrowthChance(this, pLevel, pPos);

            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,
                    pRandom.nextInt((int) (50.0F / f) + 1) == 0)) {
                pLevel.setBlock(pPos, pState.setValue(AGE, (i + 1)), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
            }
        }
    }

    protected static float getGrowthChance(Block blockIn, Level worldIn, BlockPos pos) {
        return 1F;
    }

    protected static boolean isAirAdjacent(Level world, BlockPos pos, BlockState state) {
        if (world.isEmptyBlock(pos.below()) || world.isEmptyBlock(pos.north()) || world.isEmptyBlock(pos.south())
                || world.isEmptyBlock(pos.west()) || world.isEmptyBlock(pos.east())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < getMaxAge() && isAirAdjacent((Level) pLevel, pPos, pState);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        return pState.getValue(AGE) < getMaxAge() && isAirAdjacent(pLevel, pPos, pState);
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        int i = pState.getValue(AGE) + this.getBonemealAgeIncrease(pLevel);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }
        pLevel.setBlock(pPos, pState.setValue(AGE, i), 2);
    }

    protected int getBonemealAgeIncrease(Level worldIn) {
        return Mth.nextInt(worldIn.random, 2, 5);
    }

    /*@Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pState.getValue(AGE) >= getMaxAge()) {
            pLevel.setBlock(pPos, pState.setValue(AGE, 0), 3);
            Block.spawnAsEntity(pLevel, pPos.relative(Direction), new ItemStack(Items.APPLE));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }*/

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        return super.getDrops(pState, pBuilder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext);
    }
}
