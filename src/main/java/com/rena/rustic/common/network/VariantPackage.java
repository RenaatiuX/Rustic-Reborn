package com.rena.rustic.common.network;

import com.rena.rustic.common.item.VaseItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VariantPackage {

    private final int variant;

    public VariantPackage(int variant) {
        this.variant = variant;
    }

    public static VariantPackage read(FriendlyByteBuf buffer){
        return new VariantPackage(buffer.readInt());
    }

    public static void write(VariantPackage packet, FriendlyByteBuf buffer){
        buffer.writeInt(packet.variant);
    }

    public static void execute(VariantPackage packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            VaseItem.setVariant(ctx.get().getSender().getItemInHand(InteractionHand.MAIN_HAND), packet.variant);
        });
    }
}
