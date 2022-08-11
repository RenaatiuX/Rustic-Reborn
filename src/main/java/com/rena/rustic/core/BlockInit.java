package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RusticReborn.MOD_ID);

    public static final <T extends Block>RegistryObject<T> register(String name, Supplier<T> block, Function<Block, Item> blockItem){
            RegistryObject<T> finalBlock = BLOCKS.register(name, block);
            ItemInit.ITEMS.register(name, () -> blockItem.apply(finalBlock.get()));
            return finalBlock;
    }
}
