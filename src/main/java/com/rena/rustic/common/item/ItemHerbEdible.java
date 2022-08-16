package com.rena.rustic.common.item;

import com.rena.rustic.common.block.crop.BlockHerbBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class ItemHerbEdible extends Item implements IPlantable {

    private BlockHerbBase herbBlock;

    public ItemHerbEdible(BlockHerbBase herbBlock, Properties pProperties) {
        super(pProperties);
        this.herbBlock = herbBlock;
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return this.herbBlock.defaultBlockState();
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return this.herbBlock.getPlantType(level, pos);
    }

    /*@Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        ItemStack itemstack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);

        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up())) {
            worldIn.setBlockState(pos.up(), this.herbBlock.getDefaultState());
            itemstack.shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }*/
}
