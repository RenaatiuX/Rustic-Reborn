package com.rena.rustic;

import com.mojang.logging.LogUtils;
import com.rena.rustic.client.ClientSetup;
import com.rena.rustic.common.config.RusticConfig;
import com.rena.rustic.common.datagen.DataGatherer;
import com.rena.rustic.common.network.RusticNetwork;
import com.rena.rustic.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RusticReborn.MOD_ID)
public class RusticReborn
{
    public static final ResourceLocation modLoc(String name){
        return new ResourceLocation(MOD_ID, name);
    }

    public static final String MOD_ID = "rustic";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RusticReborn()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RusticConfig.init());

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        modBus.addListener(this::setup);
        modBus.addListener(ClientSetup::setRenderLayers);
        modBus.addGenericListener(RecipeSerializer.class, RecipeInit::registerRecipes);
        modBus.addListener(DataGatherer::gatherData);

        ItemInit.ITEMS.register(modBus);
        BlockInit.BLOCKS.register(modBus);
        FluidInit.FLUIDS.register(modBus);
        BlockEntityInit.TES.register(modBus);
        ContainerInit.CONTAINERS.register(modBus);
        EntityInit.ENTITIES.register(modBus);

        // Register ourselves for server and other game events we are interested in
        //MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            RusticNetwork.init();
        });
    }

    private void client(final FMLClientSetupEvent event){

    }


}
