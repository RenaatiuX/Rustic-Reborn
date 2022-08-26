package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.block.*;
import com.rena.rustic.common.block.crop.*;
import com.rena.rustic.common.item.VaseItem;
import com.rena.rustic.common.world.feature.tree.AppleTreeGrower;
import com.rena.rustic.common.world.feature.tree.IronwoodTreeGrower;
import com.rena.rustic.common.world.feature.tree.OliveTreeGrower;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
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
    public static final RegistryObject<Block> TABLE_SPRUCE = register("table_spruce", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_BIRCH = register("table_birch", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_JUNGLE = register("table_jungle", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_ACACIA = register("table_acacia", ()-> new BlockTable(Block.Properties.copy(Blocks.ACACIA_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_DARK_OAK = register("table_big_oak", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_CRIMSON = register("table_crimson", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_WARPED = register("table_warped", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_STONE = register("table_stone", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_GRANITE = register("table_granite", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_ANDESITE = register("table_andesite", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_STRIPPED_OAK = register("table_stripped_oak", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_STRIPPED_SPRUCE= register("table_stripped_spruce", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_STRIPPED_BIRCH = register("table_stripped_birch", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_STRIPPED_JUNGLE = register("table_stripped_jungle", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_STRIPPED_ACACIA = register("table_stripped_acacia", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_STRIPPED_DARK_OAK = register("table_stripped_dark_oak", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_STRIPPED_CRIMSON = register("table_stripped_crimson", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> TABLE_STRIPPED_WARPED = register("table_stripped_warped", ()-> new BlockTable(Block.Properties.copy(Blocks.OAK_PLANKS)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> GARGOYLE = register("gargoyle",
            ()-> new BlockGargoyle(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> SLATE_PILLAR = register("slate_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> STONE_PILLAR = register("stone_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> ANDESITE_PILLAR = register("andesite_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.ANDESITE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> DIORITE_PILLAR = register("diorite_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.DIORITE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> GRANITE_PILLAR = register("granite_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> SLATE = register("slate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> SLATE_ROOF = register("slate_roof",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> SLATE_TILE = register("slate_tile",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> SLATE_BRICKS = register("slate_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> SLATE_CHISELED = register("slate_chiseled",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)), ModTabs.FARMING_TAB);

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
    public static final RegistryObject<Block> APPLE_SAPLING = register("apple_sapling",
            () -> new SaplingBlock(new AppleTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> IRONWOOD_FENCE = register("ironwood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, IRONWOOD_PLANKS.get().defaultMaterialColor()).strength(2.0F,3.0F).sound(SoundType.WOOD)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> OLIVE_FENCE = register("olive_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, IRONWOOD_PLANKS.get().defaultMaterialColor()).strength(2.0F,3.0F).sound(SoundType.WOOD)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> IRONWOOD_FENCE_GATE = register("ironwood_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, IRONWOOD_PLANKS.get().defaultMaterialColor()).strength(2.0F,3.0F).sound(SoundType.WOOD)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> OLIVE_FENCE_GATE = register("olive_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, IRONWOOD_PLANKS.get().defaultMaterialColor()).strength(2.0F,3.0F).sound(SoundType.WOOD)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> IRONWOOD_STAIR = register("ironwood_stair",
            () -> new StairBlock(()-> IRONWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(IRONWOOD_PLANKS.get())), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> OLIVE_STAIR = register("olive_stair",
            () -> new StairBlock(()-> OLIVE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(OLIVE_PLANKS.get())), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> APPLE_LEAVES = register("leaves_apple",
            ()-> new BlockLeavesApple(BlockBehaviour.Properties.of(Material.LEAVES)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> WILDBERRIES = register("wildberry_bush",
            () -> new BlockWildBerryBush(BlockBehaviour.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> FERTILE_SOIL = register("fertile_soil",
            ()-> new BlockFertileSoil(BlockBehaviour.Properties.of(Material.DIRT).strength(0.5F).sound(SoundType.GRAVEL)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> CLAY_WALL_DIAG = register("clay_wall_diag",
            ()-> new Block(BlockBehaviour.Properties.of(Material.CLAY).strength(1.0F).sound(SoundType.GRAVEL)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CLAY_WALL = register("clay_wall",
            ()-> new Block(BlockBehaviour.Properties.of(Material.CLAY).strength(1.0F).sound(SoundType.GRAVEL)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CLAY_WALL_CROSS = register("clay_wall_cross",
            ()-> new Block(BlockBehaviour.Properties.of(Material.CLAY).strength(1.0F).sound(SoundType.GRAVEL)), ModTabs.FARMING_TAB);

    public static final RegistryObject<BlockStakeCrop> TOMATO_CROP = BLOCKS.register("tomato_crop",
            ()-> new BlockStakeCrop(BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.CROP).randomTicks()){
                @Override
                protected Item getCrop() {
                    return ItemInit.TOMATO.get();
                }

                @Override
                protected Item getSeed() {
                    return ItemInit.TOMATO_SEEDS.get();
                }
            });

    public static final RegistryObject<BlockStakeCrop> CHILI_CROP = BLOCKS.register("chili_crop",
            ()-> new BlockStakeCrop(BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.CROP).randomTicks()){
                @Override
                protected Item getCrop() {
                    return ItemInit.CHILI_PEPPER.get();
                }

                @Override
                protected Item getSeed() {
                    return ItemInit.CHILI_PEPPER_SEEDS.get();
                }

                @Override
                public int getMaxHeight() {
                    return 2;
                }
            });

    public static final RegistryObject<Block> GRAPE_LEAVES = register("grape_leaves",
            ()-> new BlockGrapeLeaves(BlockBehaviour.Properties.of(Material.LEAVES).randomTicks().sound(SoundType.GRASS)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> GRAPE_STEM = register("grape_stem",
            ()-> new BlockGrapeStem(BlockBehaviour.Properties.of(Material.LEAVES).randomTicks().sound(SoundType.GRASS).strength(0.5F)), ModTabs.FARMING_TAB);

    public static final RegistryObject<Block> ROPE = register("rope",
            ()-> new BlockRope(BlockBehaviour.Properties.of(Material.CLOTH_DECORATION).strength(0.5F).sound(SoundType.WOOL)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> CROP_STAKE = register("crop_stake",
            ()-> new BlockCropStake(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0F, 5.0F).sound(SoundType.WOOD)), ModTabs.FARMING_TAB);
    public static final RegistryObject<Block> STAKE_TIED = BLOCKS.register("stake_tied",
            ()-> new BlockStakeTied(BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1.0F, 5.0F)));

    public static final <T extends Block>RegistryObject<T> register(String name, Supplier<T> block, CreativeModeTab tab){
        return register(name, block, b -> new BlockItem(b, new Item.Properties().tab(tab)));
    }

    public static final <T extends Block>RegistryObject<T> register(String name, Supplier<T> block, Function<Block, Item> blockItem){
        RegistryObject<T> finalBlock = BLOCKS.register(name, block);
        ItemInit.ITEMS.register(name, () -> blockItem.apply(finalBlock.get()));
        return finalBlock;
    }
}
