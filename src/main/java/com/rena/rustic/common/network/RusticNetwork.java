package com.rena.rustic.common.network;

import com.rena.rustic.RusticReborn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class RusticNetwork {

    public static final SimpleChannel CHANNEL = registerChannel("channel1", "0.1.0");

    protected static SimpleChannel registerChannel(String id, String version){
        return NetworkRegistry.newSimpleChannel(RusticReborn.modLoc(id), () -> version, version::equals, version::equals);
    }

    public static void init(){
        int index = 0;
        CHANNEL.registerMessage(0, VariantPackage.class, VariantPackage::write,  VariantPackage::read, VariantPackage::execute);

    }
}
