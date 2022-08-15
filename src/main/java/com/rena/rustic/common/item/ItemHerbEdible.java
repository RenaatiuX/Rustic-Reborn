package com.rena.rustic.common.item;

import com.rena.rustic.common.block.crop.BlockHerbBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class ItemHerbEdible extends Item implements IPlantable {

    private BlockHerbBase herbBlock;

    public ItemHerbEdible(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return this.herbBlock.defaultBlockState();
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return this.herbBlock.getPlantType(level, pos);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
