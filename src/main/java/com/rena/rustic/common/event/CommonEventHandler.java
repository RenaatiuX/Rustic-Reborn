package com.rena.rustic.common.event;

import com.rena.rustic.core.BlockInit;
import com.rena.rustic.core.ItemInit;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonEventHandler {

    @SubscribeEvent
    public void onSeedInteractEvent(PlayerInteractEvent.EntityInteract event) {

        if (event.getTarget() instanceof Chicken) {
            ItemStack equippedItem = event.getPlayer().getItemInHand(event.getHand());
            Animal targetAnimal = (Animal) event.getTarget();

            if (equippedItem != null &&
                    (equippedItem.getItem() == ItemInit.TOMATO_SEEDS.get() || equippedItem.getItem() == ItemInit.CHILI_PEPPER_SEEDS.get()) &&
                    targetAnimal.getAge() >= 0 &&
                    !targetAnimal.isInLove()) {
                Player player = event.getPlayer();

                if (!player.getAbilities().instabuild) {
                    equippedItem.shrink(1);
                }

                targetAnimal.setInLove(player);
            }
        }
    }

    /*@SubscribeEvent
    public void onChickenUpdate(LivingEvent.LivingUpdateEvent event) {
        if ((event.getEntity().getClass().equals(Chicken.class))) {
            Chicken chicken = (Chicken) event.getEntity();

            for (EntityAITaskEntry task : chicken.tasks.taskEntries) {
                if (task.action instanceof EntityAITemptRustic) return;
            }

            chicken.tasks.addTask(4, new EntityAITemptRustic(chicken, 1, Sets.newHashSet(
                    ItemInit.CHILI_PEPPER_SEEDS.get(),
                    ItemInit.TOMATO_SEEDS.get(),
                    //Item.BY_BLOCK(BlockInit.APPLE_SEEDS),
                    Item.BY_BLOCK.get(BlockInit.GRAPE_STEM.get())
            ), false));
        }
    }*/

}
