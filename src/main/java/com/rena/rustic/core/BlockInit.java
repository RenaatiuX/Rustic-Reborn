package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.block.*;
import com.rena.rustic.common.block.crop.BlockHerbBase;
import com.rena.rustic.common.item.VaseItem;
import com.rena.rustic.common.world.feature.tree.IronwoodTreeGrower;
import com.rena.rustic.common.world.feature.tree.OliveTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
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
    public static final RegistryObject<Block> CHAIR_CRIMSON = register("chair_crimson", () -> new BlockChair(Block.Properties.copy(Blocks.CRIMSON_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_WARPED = register("chair_warped", () -> new BlockChair(Block.Properties.copy(Blocks.WARPED_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STONE = register("chair_stone", () -> new BlockChair(Block.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_GRANITE = register("chair_diorite", () -> new BlockChair(Block.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_ANDESITE = register("chair_andesite", () -> new BlockChair(Block.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_OAK = register("chair_stripped_oak", () -> new BlockChair(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_SPRUCE = register("chair_stripped_spruce", () -> new BlockChair(Block.Properties.copy(Blocks.SPRUCE_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_BIRCH = register("chair_stripped_birch", () -> new BlockChair(Block.Properties.copy(Blocks.BIRCH_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_JUNGLE = register("chair_stripped_jungle", () -> new BlockChair(Block.Properties.copy(Blocks.JUNGLE_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_ACACIA = register("chair_stripped_acacia", () -> new BlockChair(Block.Properties.copy(Blocks.ACACIA_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_DARK_OAK = register("chair_stripped_dark_oak", () -> new BlockChair(Block.Properties.copy(Blocks.DARK_OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_CRIMSON = register("chair_stripped_crimson", () -> new BlockChair(Block.Properties.copy(Blocks.CRIMSON_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CHAIR_STRIPPED_WARPED = register("chair_stripped_warped", () -> new BlockChair(Block.Properties.copy(Blocks.WARPED_PLANKS)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> TABLE_OAK = register("table_oak", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> IRONWOOD_DOOR = register("ironwood_door", ()-> new DoorBlock(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> OLIVE_DOOR = register("olive_door", ()-> new DoorBlock(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> IRONWOOD_LOG = register("ironwood_log", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.STONE).strength(2.0F).sound(SoundType.WOOD)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> OLIVE_LOG = register("olive_log", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F).sound(SoundType.WOOD)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> IRONWOOD_PLANKS = register("ironwood_planks", ()-> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.STONE).strength(2.0F, 3.0F).sound(SoundType.WOOD)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> OLIVE_PLANKS = register("olive_planks", ()-> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_YELLOW).strength(2.0F, 3.0F).sound(SoundType.WOOD)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> IRONWOOD_SAPLING = register("ironwood_sapling",
            () -> new SaplingBlock(new IronwoodTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> OLIVE_SAPLING = register("olive_sapling",
            () -> new SaplingBlock(new OliveTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), ModTabs.FARMING_TAB);

    public static final <T extends Block>RegistryObject<T> register(String name, Supplier<T> block, CreativeModeTab tab){
        return register(name, block, b -> new BlockItem(b, new Item.Properties().tab(tab)));
    }

    public static final <T extends Block>RegistryObject<T> register(String name, Supplier<T> block, Function<Block, Item> blockItem){
        RegistryObject<T> finalBlock = BLOCKS.register(name, block);
        ItemInit.ITEMS.register(name, () -> blockItem.apply(finalBlock.get()));
        return finalBlock;
    }
}
