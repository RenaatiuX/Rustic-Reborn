package com.rena.rustic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockTable extends Block {

    public static final BooleanProperty NW = BooleanProperty.create("nw");
    public static final BooleanProperty NE = BooleanProperty.create("ne");
    public static final BooleanProperty SE = BooleanProperty.create("se");
    public static final BooleanProperty SW = BooleanProperty.create("sw");

    public static final VoxelShape AABB = Shapes.create(0, 0.875, 0, 1, 1, 1);

    public BlockTable(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(NW, true).setValue(NE, true).setValue(SE, true).setValue(SW, true));
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

    public BlockState getActualState(BlockState state, LevelReader worldIn, BlockPos pos)
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
