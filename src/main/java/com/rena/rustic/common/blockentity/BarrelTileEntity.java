package com.rena.rustic.common.blockentity;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.core.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BarrelTileEntity extends BlockEntity implements Container, MenuProvider {

    private Component displayName = getDefaultName();
    private boolean hasCustomName = false;
    private ItemStackHandler inventory = new ItemStackHandler(27){
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            BarrelTileEntity.this.setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return super.isItemValid(slot, stack);
        }
    };

    public BarrelTileEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockEntityInit.BARRELTILE_ENTITY.get(), pWorldPosition, pBlockState);
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

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> this.inventory).cast();
        }
        return super.getCapability(cap, side);
    }

    protected Component getDefaultName(){
        return new TranslatableComponent(RusticReborn.MOD_ID + ".container.barrel");
    }

    @Override
    public Component getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(Component displayName) {
        this.displayName = displayName;
        this.hasCustomName = true;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", this.inventory.serializeNBT());
        pTag.putBoolean("hasCustomName", this.hasCustomName);
        if (this.displayName != null) {
            pTag.putString("CustomName", Component.Serializer.toJson(this.displayName));
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.inventory.deserializeNBT(pTag.getCompound("inventory"));
        this.hasCustomName = pTag.getBoolean("hasCustomName");
        if (hasCustomName){
            this.displayName = Component.Serializer.fromJson(pTag.getString("CustomName"));
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return ChestMenu.threeRows(pContainerId, pInventory, this);
    }

    public boolean hasCustomName() {
        return hasCustomName;
    }
}
