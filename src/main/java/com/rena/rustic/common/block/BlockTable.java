package com.rena.rustic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockTable extends Block {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public static final VoxelShape AABB = Shapes.create(0, 0.875, 0, 1, 1, 1);

    public BlockTable(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, true).setValue(EAST, true).setValue(SOUTH, true).setValue(WEST, true));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return AABB;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
    {
        return AABB;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor level, BlockPos pos, BlockPos newPos)
    {
        BlockState stateTemp = level.getBlockState(pos.north());
        Block blockTemp = stateTemp.getBlock();
        boolean blockNorth = blockTemp instanceof BlockTable;
        stateTemp = level.getBlockState(pos.south());
        blockTemp = stateTemp.getBlock();
        boolean blockSouth = blockTemp instanceof BlockTable;
        stateTemp = level.getBlockState(pos.east());
        blockTemp = stateTemp.getBlock();
        boolean blockEast = blockTemp instanceof BlockTable;
        stateTemp = level.getBlockState(pos.west());
        blockTemp = stateTemp.getBlock();
        boolean blockWest = blockTemp instanceof BlockTable;

        return state.setValue(NORTH, !blockNorth && !blockWest)
                .setValue(EAST, !blockNorth && !blockEast)
                .setValue(SOUTH, !blockSouth && !blockEast)
                .setValue(WEST, !blockSouth && !blockWest);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH);
        builder.add(EAST);
        builder.add(SOUTH);
        builder.add(WEST);
    }
}
