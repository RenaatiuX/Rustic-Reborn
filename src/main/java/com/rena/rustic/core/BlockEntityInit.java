package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.blockentity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> TES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, RusticReborn.MOD_ID);

    public static final RegistryObject<BlockEntityType<VaseTileEntity>> VASE_BLOCK_ENTITY = TES.register("vase", () -> BlockEntityType.Builder.of(VaseTileEntity::new, BlockInit.VASE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ApiaryTileEntity>> APIARY_TILE_ENTITY = TES.register("apiary", () -> BlockEntityType.Builder.of(ApiaryTileEntity::new, BlockInit.APIARY.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrushingTubTileEntitiy>> CRUSHING_TUB_TILE_ENTITY = TES.register("crushing_tub", () -> BlockEntityType.Builder.of(CrushingTubTileEntitiy::new, BlockInit.CRUSHING_TUB.get()).build(null));
    public static final RegistryObject<BlockEntityType<BarrelTileEntity>> BARRELTILE_ENTITY = TES.register("barrel", () -> BlockEntityType.Builder.of(BarrelTileEntity::new, BlockInit.BARREL.get()).build(null));
    public static final RegistryObject<BlockEntityType<BrewingBarrelTileEntity>> BREWING_BARREL_TILE_ENTITY = TES.register("brewing_barrel", () -> BlockEntityType.Builder.of(BrewingBarrelTileEntity::new, BlockInit.BREWING_BARREL.get()).build(null));
    public static final RegistryObject<BlockEntityType<CabinetTileEntity>> CABINET = TES.register("cabinet", () -> BlockEntityType.Builder.of(CabinetTileEntity::new, BlockInit.CABINET.get()).build(null));

}
