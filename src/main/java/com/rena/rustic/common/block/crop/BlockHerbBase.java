package com.rena.rustic.common.block.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.checkerframework.checker.units.qual.A;

import java.util.Random;

public abstract class BlockHerbBase extends BushBlock implements BonemealableBlock, IPlantable {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
    private static final VoxelShape[] CROPS_AABB = new VoxelShape[] {
            Shapes.create(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
            Shapes.create(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
            Shapes.create(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
            Shapes.create(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D) };

    public BlockHerbBase(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0));
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return super.getPlantType(level, pos);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return CROPS_AABB[pState.getValue(this.getAgeProperty())];
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);

        if(pLevel.getRawBrightness(pPos, 0) >= 9){
            int i = this.getAge(pState);

            if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, pLevel, pPos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,
                        pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {
                    pLevel.setBlock(pPos, this.withAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

    protected static float getGrowthChance(Block blockIn, Level level, BlockPos pos) {
        return 4F;
    }

    public abstract Item getHerb();

    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 3;
    }

    protected int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    public BlockState withAge(int age) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), age);
    }

    public boolean isMaxAge(BlockState state) {
        return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        int i = this.getAge(pState) + this.getBonemealAgeIncrease(pLevel);
        int j = this.getMaxAge();

        if (i > j) {
            i = j;
        }

        pLevel.setBlock(pPos, this.withAge(i), 2);
    }

    protected int getBonemealAgeIncrease(Level worldIn) {
        return Mth.nextInt(worldIn.random, 2, 5);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    public static class ItemHerbBase extends ItemNameBlockItem implements IPlantable {

        private final BlockHerbBase herbBlock;

        public ItemHerbBase(BlockHerbBase block, Properties properties) {
            super(block, properties);
            this.herbBlock = block;
        }

        @Override
        public PlantType getPlantType(BlockGetter world, BlockPos pos) {
            return herbBlock.getPlantType(world, pos);
        }

        @Override
        public BlockState getPlant(BlockGetter world, BlockPos pos) {
            return herbBlock.getPlant(world, pos);
        }

    }
}
