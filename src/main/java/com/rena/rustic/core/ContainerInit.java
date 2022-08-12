package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.container.ApiaryContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerInit {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, RusticReborn.MOD_ID);

    public static final RegistryObject<MenuType<ApiaryContainer>> APIARY_CONTAINER = CONTAINERS.register("apiary", () -> IForgeMenuType.create(ApiaryContainer::new));

}
