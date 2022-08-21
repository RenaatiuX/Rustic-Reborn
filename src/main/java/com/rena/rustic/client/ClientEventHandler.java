package com.rena.rustic.client;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.block.BlockVase;
import com.rena.rustic.common.entity.SittableEntity;
import com.rena.rustic.common.item.VaseItem;
import com.rena.rustic.common.network.RusticNetwork;
import com.rena.rustic.common.network.VariantPackage;
import com.rena.rustic.core.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RusticReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void mouseMoved(InputEvent.MouseScrollEvent event){
        if(Minecraft.getInstance() != null) {
            Player player = Minecraft.getInstance().player;
            if (player != null && player.isShiftKeyDown() && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == BlockInit.VASE.get().asItem()){
                event.setCanceled(true);
                int variant = (int) (VaseItem.getVariant(player.getItemInHand(InteractionHand.MAIN_HAND)) + event.getScrollDelta());
                if (variant < BlockVase.MIN_VARIANT) {
                    variant = BlockVase.MAX_VARIANT;
                } else if (variant > BlockVase.MAX_VARIANT){
                    variant = BlockVase.MIN_VARIANT;
                }
                VaseItem.setVariant(player.getItemInHand(InteractionHand.MAIN_HAND), variant);
                RusticNetwork.CHANNEL.sendToServer(new VariantPackage(variant));
            }
        }
    }
}
