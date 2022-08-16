package com.rena.rustic.common.block.crop;

import com.rena.rustic.core.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BlockStakeCrop extends Block implements BonemealableBlock, IPlantable {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

    protected static final VoxelShape CROP_AABB = Shapes.create(0.125F, 0F, 0.125F, 0.875F, 1.0F, 0.875F);
    protected static final VoxelShape STAKE_AABB = Shapes.create(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);

    public BlockStakeCrop(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, Random pRandom, BlockPos pPos, BlockState pState) {

    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return null;
    }
}
