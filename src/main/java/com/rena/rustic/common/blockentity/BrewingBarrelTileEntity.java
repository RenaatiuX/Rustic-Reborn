package com.rena.rustic.common.blockentity;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.core.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BrewingBarrelTileEntity extends BlockEntity implements Container, MenuProvider {

    private ItemStackHandler inventory = new ItemStackHandler(6) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).isPresent() || stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent();
        }
    };
    private FluidTank input = new FluidTank(8000);
    private FluidTank output = new FluidTank(8000);
    private FluidTank auxiliary = new FluidTank(8000);

    public BrewingBarrelTileEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockEntityInit.BREWING_BARREL_TILE_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void serverTick(){

    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inputTank", input.writeToNBT(new CompoundTag()));
        pTag.put("outputTank", output.writeToNBT(new CompoundTag()));
        pTag.put("auxiliaryTank", auxiliary.writeToNBT(new CompoundTag()));
        pTag.put("inventory", this.inventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.input.readFromNBT(pTag.getCompound("inputTank"));
        this.output.readFromNBT(pTag.getCompound("outputTank"));
        this.auxiliary.readFromNBT(pTag.getCompound("auxiliaryTank"));
        this.inventory.deserializeNBT(pTag.getCompound("inventory"));
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public FluidTank getInput() {
        return input;
    }

    public FluidTank getOutput() {
        return output;
    }

    public FluidTank getAuxiliary() {
        return auxiliary;
    }

    public void blockUpdate() {
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
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

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            if (side == Direction.UP)
                return LazyOptional.of(() -> this.input).cast();
            if(side == Direction.DOWN)
                return LazyOptional.of(() -> this.output).cast();
            return LazyOptional.of(() -> this.auxiliary).cast();
        }
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> this.inventory).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(RusticReborn.MOD_ID + "container.brewing_barrel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return null;
    }
}
