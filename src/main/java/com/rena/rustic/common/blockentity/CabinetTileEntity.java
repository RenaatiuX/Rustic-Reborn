package com.rena.rustic.common.blockentity;

import com.google.common.collect.Lists;
import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.block.BlockCabinet;
import com.rena.rustic.common.util.ItemStackHandlerRustic;
import com.rena.rustic.core.BlockEntityInit;
import com.rena.rustic.core.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class CabinetTileEntity extends BlockEntity implements LidBlockEntity, Container, MenuProvider {

    public float lidAngle = 0;
    public float prevLidAngle = 0;
    public int numPlayersUsing = 0;
    private boolean hasCustomName = false;
    public ItemStack material = ItemStack.EMPTY;
    private Component name = new TranslatableComponent(RusticReborn.MOD_ID + ".container.cabinet");

    private ItemStackHandlerRustic inventory = new ItemStackHandlerRustic(27) {
        @Override
        protected void onContentsChanged(int slot) {
            CabinetTileEntity.this.setChanged();
        }
    };

    public CabinetTileEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockEntityInit.CABINET.get(), pWorldPosition, pBlockState);
    }

    @Override
    public AABB getRenderBoundingBox() {
        if (level.getBlockState(worldPosition.above()).getBlock() == BlockInit.CABINET.get() && level.getBlockState(worldPosition.above()).getProperties().contains(BlockCabinet.CONNECTION) && level.getBlockState(worldPosition.above()).getValue(BlockCabinet.CONNECTION) == BlockCabinet.ConnectionType.TOP) {
            return new AABB(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), worldPosition.getX() + 1D, worldPosition.getY() + 2D, worldPosition.getZ() + 1D);
        }
        return new AABB(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), worldPosition.getX() + 1D, worldPosition.getY() + 1D, worldPosition.getZ() + 1D);
    }

    public void tick() {

        int i = this.worldPosition.getX();
        int j = this.worldPosition.getY();
        int k = this.worldPosition.getZ();
        this.prevLidAngle = this.lidAngle;
        //float f1 = 0.1F;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
            this.level.playSound((Player) null, i + 0.5D, j + 0.5D, k + 0.5D, SoundEvents.CHEST_OPEN,
                    SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            float f2 = this.lidAngle;

            if (this.numPlayersUsing > 0) {
                this.lidAngle += 0.1F;
            } else {
                this.lidAngle -= 0.1F;
            }

            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            }

            //float f3 = 0.5F;

            if (this.lidAngle < 0.5F && f2 >= 0.5F) {
                this.level.playSound((Player) null, i + 0.5D, j + 0.5D, k + 0.5D, SoundEvents.CHEST_CLOSE,
                        SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F) {
                this.lidAngle = 0.0F;
            }
        }
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("items")) {
            inventory.deserializeNBT(pTag.getCompound("items"));
        }
        if (pTag.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(pTag.getString("CustomName"));
        }
        if (pTag.contains("numUsers")) {
            numPlayersUsing = pTag.getInt("numUsers");
        }
        if (pTag.contains("material")) {
            material = ItemStack.of(pTag.getCompound("material"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("items", inventory.serializeNBT());
        if (hasCustomName)
            pTag.putString("CustomName", Component.Serializer.toJson(this.name));
        pTag.putInt("numUsers", numPlayersUsing);
        pTag.put("material", material.serializeNBT());
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> this.inventory).cast();
        }

        return super.getCapability(cap, side);
    }


    @Override
    public float getOpenNess(float pPartialTicks) {
        return this.lidAngle;
    }

    @Override
    public int getContainerSize() {
        return this.inventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if (this.inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
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
        return this.inventory.extractItem(pIndex, Integer.MAX_VALUE, false);
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {
        this.inventory.insertItem(pIndex, pStack, false);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void setCustomName(Component name) {
        this.name = name;
        this.hasCustomName = true;
    }

    @Override
    public Component getDisplayName() {
        return this.name;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        if (isDouble()) {
            List<BlockPos> poses = Lists.newArrayList(this.getBlockPos(), this.getOther());
            BlockPos max = poses.stream().max(Comparator.comparing(BlockPos::getY)).orElseThrow();
            Container combined = new CompoundContainer(this.getOtherTe(), this);
            if (max == this.getBlockPos()) {
                combined = new CompoundContainer(this, this.getOtherTe());
            }
            ChestMenu.sixRows(pContainerId, pInventory, combined);
        }
        return ChestMenu.threeRows(pContainerId, pInventory,this);
    }

    @Override
    public void startOpen(Player pPlayer) {
        if (!level.isClientSide()) {
            numPlayersUsing++;
            blockUpdate();
            setChanged();
        }
    }

    @Override
    public void stopOpen(Player pPlayer) {
        if (!level.isClientSide()) {
            numPlayersUsing = Math.max(0, numPlayersUsing - 1);
            blockUpdate();
            setChanged();

        }
    }

    private void blockUpdate() {
        this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    /**
     * client synced
     * @return whether this cabinet is connected
     */
    public boolean isDouble() {
        return this.getBlockState().getValue(BlockCabinet.CONNECTION).isConnected();
    }

    /**
     * client synced
     * @return the other cabinet if they are connected
     */
    public BlockPos getOther() {
        if (isDouble()) {
            if (this.getBlockState().getValue(BlockCabinet.CONNECTION) == BlockCabinet.ConnectionType.TOP)
                return this.getBlockPos().relative(Direction.UP);
            if (this.getBlockState().getValue(BlockCabinet.CONNECTION) == BlockCabinet.ConnectionType.BOTTOM)
                return this.getBlockPos().relative(Direction.DOWN);
        }
        return this.getBlockPos();
    }
    @Nullable
    public CabinetTileEntity getOtherTe(){
        return isDouble() ? (CabinetTileEntity) level.getBlockEntity(getOther()) : this;
    }
}
