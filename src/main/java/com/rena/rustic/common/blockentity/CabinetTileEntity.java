package com.rena.rustic.common.blockentity;

import com.rena.rustic.common.block.BlockCabinet;
import com.rena.rustic.common.util.ItemStackHandlerRustic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CabinetTileEntity extends BlockEntity implements LidBlockEntity, Container, MenuProvider {

    public float lidAngle = 0;
    public float prevLidAngle = 0;
    public int numPlayersUsing = 0;
    private int ticksSinceSync = 0;
    public ItemStack material = ItemStack.EMPTY;

    private ItemStackHandlerRustic inventory = new ItemStackHandlerRustic (27) {
        @Override
        protected void onContentsChanged(int slot) {
            CabinetTileEntity.this.setChanged();
        }
    };

    public CabinetTileEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @OnlyIn(Dist.CLIENT)
    private AABB renderAABB;

    /*@Override
    public AABB getRenderBoundingBox() {
        if (level.getBlockState(worldPosition.above()).getBlock() == ModBlocks.CABINET && level.getBlockState(worldPosition.above()).getPropertyKeys().contains(BlockCabinet.TOP) && level.getBlockState(worldPosition.above()).getValue(BlockCabinet.TOP)) {
            return new AABB(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), worldPosition.getX() + 1D, worldPosition.getY() + 2D, worldPosition.getZ() + 1D);
        }
        return new AABB(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), worldPosition.getX() + 1D, worldPosition.getY() + 1D, worldPosition.getZ() + 1D);
    }

    public void tick() {

        int i = this.worldPosition.getX();
        int j = this.worldPosition.getY();
        int k = this.worldPosition.getZ();
        ++this.ticksSinceSync;

        if (!this.level.isClientSide && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0) {

            int recordedUsers = numPlayersUsing;
            this.numPlayersUsing = 0;

            for (Player entityplayer : this.level.getEntitiesOfClass(Player.class,
                    new AABB((double) ((float) i - 5.0F), (double) ((float) j - 5.0F),
                            (double) ((float) k - 5.0F), (double) ((float) (i + 1) + 5.0F),
                            (double) ((float) (j + 1) + 5.0F), (double) ((float) (k + 1) + 5.0F)))) {
                if (entityplayer.openContainer instanceof ContainerCabinet) {
                    CabinetTileEntity tec = ((ContainerCabinet) entityplayer.openContainer).getTile();

                    if (tec.equals(this)) {
                        ++this.numPlayersUsing;
                    }
                } else if (entityplayer.openContainer instanceof ContainerCabinetDouble) {

                    CabinetTileEntity tec = ((ContainerCabinetDouble) entityplayer.openContainer).getTileBottom();

                    if (tec.equals(this)) {
                        ++this.numPlayersUsing;
                    }

                }
            }
            if (numPlayersUsing != recordedUsers) {
                setChanged();
            }
        }

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

    /*
    @Override
	public boolean receiveClientEvent(int id, int type) {
		if (id == 1) {
			this.numPlayersUsing = type;
			return true;
		} else {
			return super.receiveClientEvent(id, type);
		}
	}

	@Override
	public void openInventory(EntityPlayer player) {
		if (!player.isSpectator()) {
			if (this.numPlayersUsing < 0) {
				this.numPlayersUsing = 0;
			}

			++this.numPlayersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
			markDirty();
		}
	}

	public void closeInventory(EntityPlayer player) {
		if (!player.isSpectator() && this.getBlockType() instanceof BlockCabinet) {
			--this.numPlayersUsing;
			this.world.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
			markDirty();
		}
	}
     */

    /*@Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (!this.checkLootAndRead(pTag) && pTag.contains("items")) {
            inventory.deserializeNBT((CompoundTag) pTag.get("items"));
        }
        if (pTag.contains("CustomName", 8)) {
            this.setCustomName(pTag.getString("CustomName"));
        }
        if (pTag.contains("numUsers")) {
            numPlayersUsing = pTag.getInt("numUsers");
        }
        if (pTag.contains("material")) {
            material = new ItemStack(pTag.getCompound("material"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (!this.checkLootAndWrite(pTag)) {
            pTag.put("items", inventory.serializeNBT());
        }
        if (this.hasCustomName()) {
            pTag.putString("CustomName", this.customName);
        }
        pTag.put("numUsers", new IntTag(numPlayersUsing));
        pTag.put("material", material.serializeNBT());
        return pTag;
    }

    public boolean canInteractWith(Player playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(worldPosition.offset(0.5D, 0.5D, 0.5D)) <= 64D;
    }*/

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
        return 0;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int pIndex) {
        return null;
    }

    @Override
    public ItemStack removeItem(int pIndex, int pCount) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pIndex) {
        return null;
    }

    @Override
    public void setItem(int pIndex, ItemStack pStack) {

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {

    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return null;
    }
}
