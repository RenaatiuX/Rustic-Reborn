package com.rena.rustic.common.block;

import com.rena.rustic.common.block_entity.VaseTileEntity;
import com.rena.rustic.common.item.VaseItem;
import io.netty.handler.codec.mqtt.MqttProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BlockVase extends Block implements EntityBlock {
    public static final int MAX_VARIANT = 5, MIN_VARIANT = 0;

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", MIN_VARIANT, MAX_VARIANT);
    protected static final VoxelShape SHAPE = Shapes.create(16d * 0.125D, 0.0D, 16d * 0.125D, 16d * 0.875D, 16D, 16d * 0.875D);

    public BlockVase() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(0.5f, 0));
        this.registerDefaultState(this.defaultBlockState().setValue(VARIANT, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            VaseTileEntity te = (VaseTileEntity) level.getBlockEntity(pos);
            if (te != null && player instanceof ServerPlayer) {
                NetworkHooks.openGui((ServerPlayer) player, te, pos);
            }
        }
        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        pState.setValue(VARIANT, VaseItem.getVariant(pStack) < 0 ? 0 : VaseItem.getVariant(pStack));
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? (level, pos, state, blockentity) -> ((VaseTileEntity) blockentity).clientTick() : (level, pos, state, blockentity) -> ((VaseTileEntity) blockentity).serverTick();
    }
}
