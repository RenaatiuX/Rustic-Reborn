package com.rena.rustic.common.block;

import com.google.common.base.Predicate;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockCandle extends Block {

    public static final DirectionProperty FACING = DirectionProperty.create("facing",
            (Predicate<Direction>) p_apply_1_ -> p_apply_1_ != Direction.DOWN);

    protected static final VoxelShape STANDING_AABB = Shapes.create(0.4, 0.0, 0.4, 0.6, 0.9375, 0.6);
    protected static final VoxelShape CANDLE_NORTH_AABB = Shapes.create(0.35, 0.0, 0.7, 0.65, 0.8, 1.0);
    protected static final VoxelShape CANDLE_SOUTH_AABB = Shapes.create(0.35, 0.0, 0.0, 0.65, 0.8, 0.3);
    protected static final VoxelShape CANDLE_WEST_AABB = Shapes.create(0.7, 0.0, 0.35, 1.0, 0.8, 0.65);
    protected static final VoxelShape CANDLE_EAST_AABB = Shapes.create(0.0D, 0.0, 0.35, 0.3, 0.8, 0.65);

    public BlockCandle(Properties p_49795_) {
        super(p_49795_);

    }
}
