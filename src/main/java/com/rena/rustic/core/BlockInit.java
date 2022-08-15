package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.block.*;
import com.rena.rustic.common.block.crop.BlockHerbBase;
import com.rena.rustic.common.item.VaseItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RusticReborn.MOD_ID);

    public static final RegistryObject<BlockVase> VASE = register("vase", BlockVase::new, VaseItem::new);
    public static final RegistryObject<BlockApiary> APIARY = register("apiary", BlockApiary::new, ModTabs.FARMING_TAB);
    public static final RegistryObject<BlockCrushingTub> CRUSHING_TUB = register("crushing_tub", BlockCrushingTub::new, ModTabs.FARMING_TAB);
    public static final RegistryObject<BlockBarrel> BARREL = register("barrel", BlockBarrel::new, ModTabs.FARMING_TAB);
    public static final RegistryObject<BlockBrewingBarrel> BREWING_BARREL = register("brewing_barrel", BlockBrewingBarrel::new, ModTabs.FARMING_TAB);

    public static final <T extends Block>RegistryObject<T> register(String name, Supplier<T> block, CreativeModeTab tab){
        return register(name, block, b -> new BlockItem(b, new Item.Properties().tab(tab)));
    }

    public static final <T extends Block>RegistryObject<T> register(String name, Supplier<T> block, Function<Block, Item> blockItem){
        RegistryObject<T> finalBlock = BLOCKS.register(name, block);
        ItemInit.ITEMS.register(name, () -> blockItem.apply(finalBlock.get()));
        return finalBlock;
    }
}
