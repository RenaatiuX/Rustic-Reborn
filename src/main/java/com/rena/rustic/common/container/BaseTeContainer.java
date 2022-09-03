package com.rena.rustic.common.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class BaseTeContainer<T extends BlockEntity> extends UtilContainer{
    protected final T blockEntity;
    /**
     * server side
     */
    public BaseTeContainer(MenuType<?> type, int id, Inventory playerInv, T blockEntity) {
        super(type, id, playerInv);
        this.blockEntity = blockEntity;
        init();
    }

    /**
     * client side, make sure that the pos of the te is written onto the buffer
     */
    public BaseTeContainer(MenuType<?> type, int id, Inventory playerInv, FriendlyByteBuf buffer){
        this(type, id, playerInv, getClientBlockEntity(playerInv, buffer));
    }

    protected abstract void init();

    public T getBlockEntity() {
        return blockEntity;
    }
}
