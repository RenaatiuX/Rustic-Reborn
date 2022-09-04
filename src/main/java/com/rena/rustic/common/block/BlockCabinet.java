package com.rena.rustic.common.block;

import com.rena.rustic.RusticReborn;
import com.rena.rustic.common.blockentity.CabinetTileEntity;
import com.rena.rustic.core.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockCabinet extends BlockRotatable implements EntityBlock {

    public static final BooleanProperty MIRROR = BooleanProperty.create("mirror");
    public static final EnumProperty<ConnectionType> CONNECTION = EnumProperty.create("connection", ConnectionType.class);

    public static final int GUI_ID = 3;

    public BlockCabinet() {
        super(BlockBehaviour.Properties.copy(Blocks.CHEST).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(MIRROR, false).setValue(CONNECTION, ConnectionType.NOT));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            CabinetTileEntity te = (CabinetTileEntity) pLevel.getBlockEntity(pPos);
            if (te != null && pPlayer instanceof ServerPlayer) {
                NetworkHooks.openGui((ServerPlayer) pPlayer, te, pPos);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide()) {
            if (pLevel.getBlockState(pPos.relative(Direction.DOWN)).is(BlockInit.CABINET.get())) {
                pLevel.setBlock(pPos, pState.setValue(CONNECTION, ConnectionType.BOTTOM), 3);
                pLevel.setBlock(pPos.relative(Direction.DOWN), pLevel.getBlockState(pPos.relative(Direction.DOWN)).setValue(CONNECTION, ConnectionType.TOP), 3);
            } else if (pLevel.getBlockState(pPos.relative(Direction.UP)).is(BlockInit.CABINET.get())) {
                pLevel.setBlock(pPos, pState.setValue(CONNECTION, ConnectionType.TOP), 3);
                pLevel.setBlock(pPos.relative(Direction.UP), pLevel.getBlockState(pPos.relative(Direction.UP)).setValue(CONNECTION, ConnectionType.BOTTOM), 3);
            }
            if (pLevel.getBlockState(pPos.relative(pState.getValue(HORIZONTAL_FACING).getCounterClockWise())).is(BlockInit.CABINET.get())) {
                pLevel.setBlock(pPos, pLevel.getBlockState(pPos).setValue(MIRROR, true), 3);
            }
        }
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (!pLevel.isClientSide()) {
            ConnectionType connectionType = pState.getValue(CONNECTION);
            boolean mirror = pState.getValue(MIRROR);
            if (pState.getBlock() == BlockInit.CABINET.get() && pNeighborState.getBlock() == BlockInit.CABINET.get()) {
                if (!isConnected(pState) && !isConnected(pNeighborState)) {
                    if (pDirection == Direction.DOWN) {
                        connectionType = ConnectionType.BOTTOM;
                    } else if (pDirection == Direction.UP) {
                        connectionType = ConnectionType.TOP;
                    }
                }
            }
            if (pState.getBlock() == BlockInit.CABINET.get() && pDirection == pState.getValue(HORIZONTAL_FACING).getCounterClockWise() && pNeighborState.isAir()) {
                mirror = false;
            } else if (pState.getBlock() == BlockInit.CABINET.get() && pDirection == pState.getValue(HORIZONTAL_FACING).getCounterClockWise() && pNeighborState.is(BlockInit.CABINET.get())) {
                mirror = true;
            }
            connectionType = pLevel.getBlockState(connectionType.relative(pCurrentPos)).isAir() ? ConnectionType.NOT : connectionType;
            RusticReborn.LOGGER.info("" + mirror);
            pLevel.setBlock(connectionType.relative(pCurrentPos), pLevel.getBlockState(connectionType.relative(pCurrentPos)).setValue(MIRROR, mirror), 3, 0);
            return pState.setValue(MIRROR, mirror).setValue(CONNECTION, connectionType);
        }
        return pState;
    }

    private static boolean isConnected(BlockState state) {
        return state.getValue(CONNECTION).isConnected();
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CabinetTileEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (s, t, p, blockentity) -> ((CabinetTileEntity) blockentity).tick();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(CONNECTION, MIRROR);
    }

    public enum ConnectionType implements StringRepresentable {
        NOT("not", Direction.NORTH),
        TOP("top", Direction.UP),
        BOTTOM("bottom", Direction.DOWN);

        private final String propertyName;
        private final Direction dir;

        ConnectionType(String propertyName, Direction dir) {
            this.propertyName = propertyName;
            this.dir = dir;
        }

        @Override
        public String getSerializedName() {
            return this.propertyName;
        }

        public boolean isConnected() {
            return this != NOT;
        }

        public BlockPos relative(BlockPos pos) {
            if (this == NOT)
                return pos;
            return pos.relative(this.dir);
        }
    }
}
