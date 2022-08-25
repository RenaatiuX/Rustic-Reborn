package com.rena.rustic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import static net.minecraftforge.common.PlantType.*;

public class BlockFertileSoil extends Block {
    public BlockFertileSoil(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        PlantType plantType = plantable.getPlantType(world, pos.relative(facing));

        if(DESERT.equals(plantType)){
            return true;
        }
        else if(NETHER.equals(plantType)){
            return false;
        }
        else if(CROP.equals(plantType)){
            return true;
        }
        else if(CAVE.equals(plantType)){
            return true;
        }
        else if(PLAINS.equals(plantType)){
            return true;
        }
        else if(WATER.equals(plantType)){
            return true;
        }
        else return BEACH.equals(plantType);
    }
}
