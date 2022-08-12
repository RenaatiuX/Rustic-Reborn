package com.rena.rustic.common.block_entity;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.config.RusticConfig;
import com.rena.rustic.common.container.ApiaryContainer;
import com.rena.rustic.core.BlockEntityInit;
import com.rena.rustic.core.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.GrowingPlantBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class ApiaryTileEntity extends BlockEntity implements WorldlyContainer, MenuProvider {

    private ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot == 0)
                return stack.getItem() == ItemInit.BEE.get();
            if (slot == 1)
                return stack.getItem() == ItemInit.HONEYCOMB.get();
            return super.isItemValid(slot, stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            ApiaryTileEntity.this.setChanged();
        }
    };
    LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.NORTH);



    private int reproductionTime = 1200;
    private int reproductionTimer = 0;
    private int productionTime = 600;
    private int productionTimer = 0;

    public ApiaryTileEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockEntityInit.APIARY_TILE_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void tick() {
        int numBees = 0;
        if (inventory.getStackInSlot(0).getItem().equals(ItemInit.BEE.get())) {
            numBees = inventory.getStackInSlot(0).getCount();
        }
        int numHoneycomb = 0;
        if (inventory.getStackInSlot(1).getItem().equals(ItemInit.HONEYCOMB.get())) {
            numHoneycomb = inventory.getStackInSlot(0).getCount();
        }

        if (numBees > 0 && !this.level.isClientSide) {
            reproductionTime = (int) (RusticConfig.BEE_REPRODUCTION_MULTIPLIER.get() * (1600F / ((numBees / 20F) + 1F)));
            productionTime = (int) (RusticConfig.BEE_HONEYCOMB_MULTIPLIER.get() * (800F / ((numBees / 20F) + 1F)));

            reproductionTimer++;
            productionTimer++;
            if (reproductionTimer >= reproductionTime) {
                reproductionTimer = 0;
                if (numBees < 64) {
                    inventory.getStackInSlot(0).grow(1);
                }
            }
            if (productionTimer >= productionTime) {
                productionTimer = 0;
                if (numHoneycomb == 0) {
                    inventory.setStackInSlot(1, new ItemStack(ItemInit.HONEYCOMB.get(), 1));
                } else if (numHoneycomb < 64) {
                    inventory.getStackInSlot(1).grow(1);
                }
            }

            if (RusticConfig.BEE_GROWTH_MULTIPLIER.get() != 0 && this.level.random.nextInt((int) Math.ceil(2048F / (numBees * RusticConfig.BEE_GROWTH_MULTIPLIER.get()))) == 0) {
                //System.out.println("apiary growth");
                int randX = this.level.random.nextInt(9) - 4;
                int randZ = this.level.random.nextInt(9) - 4;
                int x = this.worldPosition.getX();
                int y = this.worldPosition.getY();
                int z = this.worldPosition.getZ();
                for (int i = 0; i < 3; i++) {
                    BlockPos pos = new BlockPos(x + randX, y + 1 - i, z + randZ);
                    BlockState block = this.level.getBlockState(pos);
                    if (block.getBlock() instanceof GrowingPlantBlock && !this.level.isClientSide()) {
                        block.randomTick((ServerLevel) this.level, pos, level.random);
                        break;
                    }
                }
            }

            setChanged();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("items", this.inventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.inventory.deserializeNBT(tag.getCompound("items"));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN)
                return handlers[0].cast();

            return handlers[1].cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (LazyOptional<? extends IItemHandler> handler : this.handlers){
            handler.invalidate();
        }
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = SidedInvWrapper.create(this, Direction.DOWN, Direction.NORTH);
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == Direction.DOWN) {
            return new int[]{1};
        }
        return new int[]{0};
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return this.inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if (!this.inventory.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return this.inventory.getStackInSlot(pIndex);
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return this.inventory.extractItem(pIndex, pCount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return this.inventory.extractItem(pIndex, 64, false);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        this.inventory.setStackInSlot(pIndex, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if (!this.inventory.getStackInSlot(i).isEmpty()) {
                setItem(i, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(RusticReborn.MOD_ID + ".container.apiary");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new ApiaryContainer(pContainerId, pInventory, this);
    }
}
