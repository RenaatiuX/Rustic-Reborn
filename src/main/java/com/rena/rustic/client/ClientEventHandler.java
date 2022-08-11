package com.rena.rustic.client;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.block.BlockVase;
import com.rena.rustic.common.item.VaseItem;
import com.rena.rustic.core.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

@Mod.EventBusSubscriber(modid = RusticReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void mouseMoved(InputEvent.MouseScrollEvent event){
        if(Minecraft.getInstance() != null) {
            Player player = Minecraft.getInstance().player;
            if (player != null && player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == BlockInit.VASE.get().asItem() && event.getScrollDelta() > 0){
                event.setCanceled(true);

                int variant = (int) ((VaseItem.getVariant(player.getItemInHand(InteractionHand.MAIN_HAND)) - BlockVase.MIN_VARIANT
                                        + (event.getScrollDelta() / 120)) % (BlockVase.MAX_VARIANT - BlockVase.MIN_VARIANT + 1)
                                        + BlockVase.MIN_VARIANT);
                if (variant < BlockVase.MIN_VARIANT) {
                    variant = BlockVase.MAX_VARIANT;
                }
                VaseItem.setVariant(player.getItemInHand(InteractionHand.MAIN_HAND), variant);
            }
        }
    }
}
