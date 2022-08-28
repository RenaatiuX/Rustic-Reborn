package com.rena.rustic.common.block.crop;

import com.rena.rustic.core.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class BlockStakeCrop extends Block implements BonemealableBlock, IPlantable {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

    protected static final VoxelShape CROP_AABB = Shapes.create(0.125F, 0F, 0.125F, 0.875F, 1.0F, 0.875F);
    protected static final VoxelShape STAKE_AABB = Shapes.create(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);

    public BlockStakeCrop(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    public int getMaxAge() {
        return 3;
    }

    public int getMaxHeight() {
        return 3;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return CROP_AABB;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return STAKE_AABB;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        super.destroy(pLevel, pPos, pState);
        pLevel.setBlock(pPos, BlockInit.CROP_STAKE.get().defaultBlockState(), 3);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        pBuilder.getLevel();
        Random rand = pBuilder.getLevel().random;
        stacks.add(new ItemStack(getSeed(), rand.nextInt(2) + 1));
        if (pState.getValue(AGE) >= getMaxAge()) {
            stacks.add(new ItemStack(getCrop()));
        }
        return stacks;
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
        this.checkAndDropBlock(pLevel, pPos, pState);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        this.checkAndDropBlock(pLevel, pPos, pState);

        if (pLevel.getRawBrightness(pPos.above(), 0) >= 9) {
            int i = pState.getValue(AGE);

            if (i < getMaxAge()) {
                float f = getGrowthChance(this, pLevel, pPos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,
                        pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {
                    pLevel.setBlock(pPos, pState.setValue(AGE, i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            } else if (pLevel.getBlockState(pPos.above()).getBlock() == BlockInit.CROP_STAKE.get() && pLevel.getBlockState(pPos.below(getMaxHeight() - 1)).getBlock() != this) {
                float f = getGrowthChance(this, pLevel, pPos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState,
                        pRandom.nextInt((int) (25.0F / f) + 1) == 0)) {
                    pLevel.setBlock(pPos.above(), pState.setValue(AGE, 0), 3);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }

    protected static float getGrowthChance(Block block, Level world, BlockPos pos) {
        float growth = 0.125F * (world.getLightEmission(pos) - 11);
        BlockState soil = world.getBlockState(pos.offset(0, -1, 0));
        if (soil.getBlock().isFertile(soil, world, pos.offset(0, -1, 0)) || soil.getBlock() == block)
            growth *= 1.5F;
        return 3.5F + growth;
    }

    protected void checkAndDropBlock(Level worldIn, BlockPos pos, BlockState state) {
        if (!this.canBlockStay(worldIn, pos, state)) {
            worldIn.setBlock(pos, BlockInit.CROP_STAKE.get().defaultBlockState(), 3);
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return pState.getValue(AGE) < getMaxAge();
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        int i = pState.getValue(AGE) + this.getBonemealAgeIncrease(pLevel);
        int j = this.getMaxAge();

        if (i > j) {
            i = j;
        }

        pLevel.setBlock(pPos, pState.setValue(AGE, i), 2);
    }

    protected int getBonemealAgeIncrease(Level worldIn) {
        return Mth.nextInt(worldIn.random, 2, 5);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.CROP;
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return defaultBlockState();
    }

    public boolean canBlockStay(Level worldIn, BlockPos pos, BlockState state) {
        BlockState soil = worldIn.getBlockState(pos.below());
        return (worldIn.getLightEmission(pos) >= 8 || worldIn.canSeeSky(pos)) && (soil.getBlock() == this
                || soil.getBlock().canSustainPlant(soil, worldIn, pos.below(), Direction.UP, this));
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(this.getSeed());
    }

    protected abstract Item getSeed();

    protected abstract Item getCrop();

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pState.getValue(AGE) >= getMaxAge()) {
            pLevel.setBlock(pPos, pState.setValue(AGE, getMaxAge() - 1), 2);
            Block.popResource(pLevel, pPos, new ItemStack(getCrop()));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
