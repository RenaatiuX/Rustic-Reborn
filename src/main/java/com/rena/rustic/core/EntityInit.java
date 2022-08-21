package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.client.renderer.SeatRenderer;
import com.rena.rustic.common.entity.SittableEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = RusticReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            RusticReborn.MOD_ID);

    public static final RegistryObject<EntityType<SittableEntity>> SEAT = register("seat",
            EntityType.Builder.<SittableEntity>of((type, world) -> new SittableEntity(world), MobCategory.MISC)
                    .sized(0.0F, 0.0F).setCustomClientFactory((spawnEntity, world) -> new SittableEntity(world)));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder)
    {
        return ENTITIES.register(name, () -> builder.build(name));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.SEAT.get(), SeatRenderer::new);
    }

}
