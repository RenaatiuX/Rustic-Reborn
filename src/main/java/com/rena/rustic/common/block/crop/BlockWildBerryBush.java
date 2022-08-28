package com.rena.rustic.common.block.crop;

import com.rena.rustic.core.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockWildBerryBush extends BaseBushBlock implements BonemealableBlock {

    protected static final VoxelShape BUSH_AABB = Shapes.create(0.125D, 0.0D, 0.125D, 0.875D, 1D, 0.875D);

    public BlockWildBerryBush(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BUSH_AABB;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(ItemInit.WILDBERRIES.get());
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity) {
            pEntity.makeStuckInBlock(pState, new Vec3(0.8D, 0.75D, 0.8D));
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int i = pState.getValue(AGE);
        boolean flag = i == 3;
        if (!flag && pPlayer.getItemInHand(pHand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (i > 1) {
            int j = 1 + pLevel.random.nextInt(2);
            popResource(pLevel, pPos, new ItemStack(ItemInit.WILDBERRIES.get(), j + (flag ? 1 : 0)));
            pLevel.playSound((Player)null, pPos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            pLevel.setBlock(pPos, pState.setValue(AGE, Integer.valueOf(1)), 2);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    /*@Override
    public BlockColor getBlockColor() {
        return new BlockColor() {
            @Override
            public int getColor(BlockState pState, @Nullable BlockAndTintGetter pLevel, @Nullable BlockPos pPos, int pTintIndex) {
                if (pLevel != null && pPos != null && pTintIndex == 1) {
                    return BiomeColors.getAverageFoliageColor(pLevel, pPos);
                }
                return FoliageColor.getDefaultColor();
            }
        };
    }

    @Override
    public ItemColor getItemColor() {
        return new ItemColor() {
            @Override
            public int getColor(ItemStack pStack, int pTintIndex) {
                if (!(pStack.getItem() instanceof BlockItem)) return 0xFFFFFF;
                BlockState state = ((BlockItem) pStack.getItem()).getBlock().defaultBlockState();
                BlockColor blockColor = ((IColoredBlock) state.getBlock()).getBlockColor();
                return blockColor == null ? 0xFFFFFF : blockColor.getColor(state, null, null, pTintIndex);
            }
        };
    }*/
}
