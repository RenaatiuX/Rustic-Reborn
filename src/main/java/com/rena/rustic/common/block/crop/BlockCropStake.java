package com.rena.rustic.common.block.crop;

import com.rena.rustic.common.item.ItemStakeCropSeed;
import com.rena.rustic.core.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockCropStake extends Block {

    protected static final VoxelShape STAKE_SELECTION_AABB = Shapes.create(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
    protected static final VoxelShape STAKE_AABB = Shapes.create(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);

    public BlockCropStake(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (!stack.isEmpty() && stack.getItem() instanceof ItemStakeCropSeed) {
            if (pLevel.getBlockState(pPos.below()).getBlock().canSustainPlant(pLevel.getBlockState(pPos.below()), pLevel, pPos.below(), Direction.UP, ((ItemStakeCropSeed) stack.getItem()).getCrop())) {
                pLevel.setBlock(pPos, ((ItemStakeCropSeed) stack.getItem()).getCropState(), 3);
                if (!pPlayer.getAbilities().instabuild) {
                    pPlayer.getItemInHand(pHand).shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        } else if (!stack.isEmpty() && stack.getItem() == Item.BY_BLOCK.get(BlockInit.ROPE.get())) {
            pLevel.setBlock(pPos, BlockInit.STAKE_TIED.get().defaultBlockState(), 2);
            pLevel.blockUpdated(pPos, BlockInit.STAKE_TIED.get());
            if (!pPlayer.getAbilities().instabuild) {
                pPlayer.getItemInHand(pHand).shrink(1);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return STAKE_SELECTION_AABB;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return STAKE_AABB;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
}
