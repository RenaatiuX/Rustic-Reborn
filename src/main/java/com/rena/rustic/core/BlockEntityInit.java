package com.rena.rustic.core;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.block_entity.ApiaryTileEntity;
import com.rena.rustic.common.block_entity.VaseTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> TES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, RusticReborn.MOD_ID);

    public static final RegistryObject<BlockEntityType<VaseTileEntity>> VASE_BLOCK_ENTITY = TES.register("vase", () -> BlockEntityType.Builder.of(VaseTileEntity::new, BlockInit.VASE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ApiaryTileEntity>> APIARY_TILE_ENTITY = TES.register("apiary", () -> BlockEntityType.Builder.of(ApiaryTileEntity::new, BlockInit.APIARY.get()).build(null));

}
