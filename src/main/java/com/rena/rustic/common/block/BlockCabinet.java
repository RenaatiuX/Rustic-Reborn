package com.rena.rustic.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockCabinet extends BlockRotatable implements EntityBlock {

    public static final BooleanProperty MIRROR = BooleanProperty.create("mirror");
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");

    public static final int GUI_ID = 3;

    public BlockCabinet(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MIRROR , false).setValue(TOP , false).setValue(BOTTOM , false));
    }

    /*@Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if (pStack.hasTag()){
            CompoundTag compoundTag = pStack.getTag();
            if (compoundTag.contains("material", Tag.TAG_COMPOUND)){
                ItemStack material = new ItemStack(compoundTag.getCompound("material"));
                if (!material.isEmpty()) pTooltip.add(material.getDisplayName());
            }
        }
    }*/

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }
}
