package com.rena.rustic.common.block;

import com.rena.rustic.common.block_entity.CrushingTubTileEntitiy;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.Nullable;

public class BlockCrushingTub extends Block implements EntityBlock {

    protected static final VoxelShape TUB_AABB = Shapes.create(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D);

    public BlockCrushingTub() {
        super(BlockBehaviour.Properties.of(Material.WOOD).strength(1.5f).sound(SoundType.WOOD).dynamicShape().noOcclusion());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pHand == InteractionHand.MAIN_HAND && !pLevel.isClientSide()) {
            CrushingTubTileEntitiy te = (CrushingTubTileEntitiy) pLevel.getBlockEntity(pPos);
            ItemStack heldItem = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
            if (te != null) {
                if (!heldItem.isEmpty()) {
                    //filling with tanks
                    if (heldItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).isPresent() && te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).isPresent()) {
                        IFluidHandler teHandler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
                        IFluidHandler itemHandler = heldItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
                        boolean success = false;
                        for (int i = 0; i < itemHandler.getTanks(); i++) {
                            int filled = teHandler.fill(itemHandler.getFluidInTank(i), IFluidHandler.FluidAction.EXECUTE);
                            itemHandler.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                            if (filled > 0) {
                                success = true;
                                te.setChanged();
                            }
                        }
                        te.blockUpdate();
                        if (success)
                            return InteractionResult.SUCCESS;
                    }
                    te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                        pPlayer.setItemInHand(InteractionHand.MAIN_HAND, h.insertItem(0, heldItem, false));
                    });
                    te.blockUpdate();
                    te.setChanged();
                    return InteractionResult.SUCCESS;
                } else if (pPlayer.isShiftKeyDown() && te.getAmount() > 0) {
                    te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(h -> {
                        for (int i = 0; i < h.getTanks(); i++) {
                            FluidStack drained = h.drain(h.getTankCapacity(i), IFluidHandler.FluidAction.EXECUTE);
                            SoundEvent sound = drained.getFluid().getAttributes().getEmptySound();
                            pLevel.playSound(null, pPos, sound, SoundSource.BLOCKS, 1, 1);
                        }
                    });
                    te.blockUpdate();
                    return InteractionResult.SUCCESS;
                } else if (!te.getItem(0).isEmpty()){
                    Containers.dropContents(pLevel, pPlayer, new SimpleContainer(te.getItem(0)));
                    te.setItem(0, ItemStack.EMPTY);
                    te.blockUpdate();
                    te.setChanged();
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float p_152430_) {
        if (!pLevel.isClientSide() && pEntity instanceof LivingEntity) {
            CrushingTubTileEntitiy te = (CrushingTubTileEntitiy) pLevel.getBlockEntity(pPos);
            if (te != null) {
                te.crush((LivingEntity) pEntity);
            }
        }
        super.fallOn(pLevel, pState, pPos, pEntity, p_152430_);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return TUB_AABB;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrushingTubTileEntitiy(pPos, pState);
    }
}
