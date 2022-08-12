package com.rena.rustic.common.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class DataGatherer {

    public static void gatherData(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if (event.includeClient()){
            registerClientData(gen, helper);
        }
        if (event.includeServer()){
            registerServerData(gen, helper);
        }
    }

    private static final void registerClientData(DataGenerator gen, ExistingFileHelper helper){

    }

    private static final void registerServerData(DataGenerator gen, ExistingFileHelper helper){
        gen.addProvider(new ModRecipeProvider(gen));
    }
}
