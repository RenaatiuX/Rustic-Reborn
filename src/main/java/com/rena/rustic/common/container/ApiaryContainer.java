package com.rena.rustic.common.container;

import com.rena.rustic.common.block_entity.ApiaryTileEntity;
import com.rena.rustic.core.ContainerInit;
import com.rena.rustic.core.ItemInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;

public class ApiaryContainer extends BaseTeContainer<ApiaryTileEntity> {

    public ApiaryContainer(int id, Inventory playerInv, ApiaryTileEntity blockEntity) {
        super(ContainerInit.APIARY_CONTAINER.get(), id, playerInv, blockEntity);
    }

    public ApiaryContainer(int id, Inventory playerInv, FriendlyByteBuf buffer) {
        super(ContainerInit.APIARY_CONTAINER.get(), id, playerInv, buffer);
    }

    @Override
    protected void init() {
        addPlayerInventory(8, 84);

        addSlot(new FilterSlot(this.blockEntity, 0, 80, 26, s -> s.getItem() == ItemInit.BEE.get()));
        addSlot(new FilterSlot(this.blockEntity, 1, 80, 44, s -> s.getItem() == ItemInit.HONEYCOMB.get()));
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
