package com.rena.rustic.common.block_entity;

import com.rena.rustic.common.recipes.CrushingTubRecipe;
import com.rena.rustic.core.BlockEntityInit;
import com.rena.rustic.core.FluidInit;
import com.rena.rustic.core.RecipeInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrushingTubTileEntitiy extends BlockEntity implements Container {

    protected static final int CAPACITY = FluidAttributes.BUCKET_VOLUME * 8;

    private ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            blockUpdate();
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot == 0){
                return level.getRecipeManager().getRecipeFor(RecipeInit.CRUSHING_TUB_RECIPE, new SimpleContainer(stack), level).isPresent();
            }
            return super.isItemValid(slot, stack);
        }
    };

    private FluidTank tank = new FluidTank(CAPACITY){
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
            blockUpdate();
        }


    };

    public CrushingTubTileEntitiy(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockEntityInit.CRUSHING_TUB_TILE_ENTITY.get(), pWorldPosition, pBlockState);
    }


    public void crush(LivingEntity entity){
        CrushingTubRecipe recipe = getRecipe();
        if (recipe != null){
            FluidStack output = recipe.getOutput();
            int amount = this.tank.fill(output, IFluidHandler.FluidAction.SIMULATE);
            if (amount >= output.getAmount()){
                this.tank.fill(output, IFluidHandler.FluidAction.EXECUTE);
               this.inventory.extractItem(0, 1, false);
               if (!recipe.getByProduct().isEmpty()){
                   Containers.dropContents(this.level, this.worldPosition, new SimpleContainer(recipe.getByProduct().copy()));
               }
                this.level.playSound(null, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5, SoundEvents.SLIME_BLOCK_FALL, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
                blockUpdate();
                this.setChanged();
            }
        }
    }

    @Nullable
    private CrushingTubRecipe getRecipe(){
        return this.level.getRecipeManager().getRecipeFor(RecipeInit.CRUSHING_TUB_RECIPE, this, this.level).orElse(null);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("tank", this.tank.writeToNBT(new CompoundTag()));
        tag.put("items", this.inventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.tank.readFromNBT(tag.getCompound("tank"));
        this.inventory.deserializeNBT(tag.getCompound("items"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            this.handleUpdateTag(tag);
        }
    }

    public void blockUpdate(){
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && side != Direction.UP){
            return LazyOptional.of(() -> this.tank).cast();
        }else if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side != Direction.UP){
            return LazyOptional.of(() -> this.inventory).cast();
        }
        return super.getCapability(cap, side);
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
    public int getCapacity(){
        int cap = 0;
        for (int i = 0;i<this.tank.getTanks();i++){
            cap += this.tank.getFluidInTank(i).getAmount();
        }
        return cap;
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

    /**
     * only use on client side in render stuff
     */
    public FluidTank getTank() {
        return tank;
    }
}
