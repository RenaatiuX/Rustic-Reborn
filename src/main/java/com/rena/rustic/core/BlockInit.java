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
import net.minecraft.world.level.block.Blocks;
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

    public static final RegistryObject<Block> CHAIR_OAK = register("chair_oak", () -> new BlockChair(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_SPRUCE = register("chair_spruce", () -> new BlockChair(Block.Properties.copy(Blocks.SPRUCE_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_BIRCH = register("chair_birch", () -> new BlockChair(Block.Properties.copy(Blocks.BIRCH_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_JUNGLE = register("chair_jungle", () -> new BlockChair(Block.Properties.copy(Blocks.JUNGLE_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_ACACIA = register("chair_acacia", () -> new BlockChair(Block.Properties.copy(Blocks.ACACIA_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_DARK_OAK = register("chair_big_oak", () -> new BlockChair(Block.Properties.copy(Blocks.DARK_OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_CRIMSON = register("crimson_chair", () -> new BlockChair(Block.Properties.copy(Blocks.CRIMSON_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_WARPED = register("warped_chair", () -> new BlockChair(Block.Properties.copy(Blocks.WARPED_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STONE = register("stone_chair", () -> new BlockChair(Block.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_GRANITE = register("diorite_chair", () -> new BlockChair(Block.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_ANDESITE = register("andesite_chair", () -> new BlockChair(Block.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_OAK = register("stripped_oak_chair", () -> new BlockChair(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_SPRUCE = register("stripped_spruce_chair", () -> new BlockChair(Block.Properties.copy(Blocks.SPRUCE_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_BIRCH = register("stripped_birch_chair", () -> new BlockChair(Block.Properties.copy(Blocks.BIRCH_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_JUNGLE = register("stripped_jungle_chair", () -> new BlockChair(Block.Properties.copy(Blocks.JUNGLE_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_ACACIA = register("stripped_acacia_chair", () -> new BlockChair(Block.Properties.copy(Blocks.ACACIA_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_DARK_OAK = register("stripped_dark_oak_chair", () -> new BlockChair(Block.Properties.copy(Blocks.DARK_OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_CRIMSON = register("stripped_crimson_chair", () -> new BlockChair(Block.Properties.copy(Blocks.CRIMSON_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_WARPED = register("stripped_warped_chair", () -> new BlockChair(Block.Properties.copy(Blocks.WARPED_PLANKS)), ModTabs.FARMING_TAB);

    public static final <T extends Block>RegistryObject<T> register(String name, Supplier<T> block, CreativeModeTab tab){
        return register(name, block, b -> new BlockItem(b, new Item.Properties().tab(tab)));
    }

    public static final <T extends Block>RegistryObject<T> register(String name, Supplier<T> block, Function<Block, Item> blockItem){
        RegistryObject<T> finalBlock = BLOCKS.register(name, block);
        ItemInit.ITEMS.register(name, () -> blockItem.apply(finalBlock.get()));
        return finalBlock;
    }
}
