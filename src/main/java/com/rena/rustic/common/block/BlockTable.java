package com.rena.rustic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockTable extends BlockFurnitureWaterlogged {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public static final VoxelShape AABB = Shapes.create(0, 0.875, 0, 1, 1, 1);

    public BlockTable(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false));    }

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
        boolean north = level.getBlockState(pos.north()).getBlock() == this;
        boolean east = level.getBlockState(pos.east()).getBlock() == this;
        boolean south = level.getBlockState(pos.south()).getBlock() == this;
        boolean west = level.getBlockState(pos.west()).getBlock() == this;
        return state.setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west);
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

    /*
    @Override
	public boolean isSideSolid(IBlockState state, IBlockAccess ba, BlockPos pos, EnumFacing side) {
		if (side == EnumFacing.UP) {
			return true;
		}
		return false;
	}

	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }
     */

    /*public BlockState getActualState(BlockState state, LevelReader worldIn, BlockPos pos)
    {
        BlockState stateTemp = worldIn.getBlockState(pos.north());
        Block blockTemp = stateTemp.getBlock();
        boolean blockNorth = blockTemp instanceof BlockTable;
        stateTemp = worldIn.getBlockState(pos.south());
        blockTemp = stateTemp.getBlock();
        boolean blockSouth = blockTemp instanceof BlockTable;
        stateTemp = worldIn.getBlockState(pos.east());
        blockTemp = stateTemp.getBlock();
        boolean blockEast = blockTemp instanceof BlockTable;
        stateTemp = worldIn.getBlockState(pos.west());
        blockTemp = stateTemp.getBlock();
        boolean blockWest = blockTemp instanceof BlockTable;

        return state.setValue(NW, !blockNorth && !blockWest)
                .setValue(NE, !blockNorth && !blockEast)
                .setValue(SE, !blockSouth && !blockEast)
                .setValue(SW, !blockSouth && !blockWest);
    }

    /*protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {NW, NE, SE, SW});
    }*/

    /*
    @Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return AABB;
	}

    @Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing side) {
		return (side == EnumFacing.UP) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}
     */
}
