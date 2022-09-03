package com.rena.rustic.common.block;

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
        super(BlockBehaviour.Properties.copy(Blocks.CHEST));
        this.registerDefaultState(this.stateDefinition.any().setValue(MIRROR, false).setValue(CONNECTION, ConnectionType.NOT));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()){
            CabinetTileEntity te = (CabinetTileEntity) pLevel.getBlockEntity(pPos);
            if (te != null && pPlayer instanceof ServerPlayer){
                NetworkHooks.openGui((ServerPlayer) pPlayer, te, pPos);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (!pLevel.isClientSide()) {
            if (pState.getBlock() == BlockInit.CABINET.get() && pNeighborState.getBlock() == BlockInit.CABINET.get()) {
                if (!isConnected(pState) && !isConnected(pNeighborState)) {
                    if (pDirection == Direction.DOWN) {
                        return pState.setValue(CONNECTION, ConnectionType.BOTTOM);
                    } else if (pDirection == Direction.UP) {
                        return pState.setValue(CONNECTION, ConnectionType.TOP);
                    } else if (pDirection == pState.getValue(HORIZONTAL_FACING).getClockWise()) {
                        return pState.setValue(MIRROR, true);
                    }
                }
                if (pDirection == pState.getValue(HORIZONTAL_FACING).getClockWise()) {
                    return pState.setValue(MIRROR, true);
                }
            }

        }
        return pState;
    }

    private static boolean isConnected(BlockState neighborState) {
        return neighborState.getValue(CONNECTION).isConnected() && neighborState.getValue(CONNECTION).isConnected();
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
        NOT("not"),
        TOP("top"),
        BOTTOM("bottom");

        private final String propertyName;
        ConnectionType(String propertyName){
            this.propertyName = propertyName;
        }

        @Override
        public String getSerializedName() {
            return this.propertyName;
        }

        public boolean isConnected(){
            return this != NOT;
        }
    }
}
