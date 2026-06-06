package com.rowan.thegameoflife.block.Custom;

import com.rowan.thegameoflife.handlers.CellHandler;
import com.rowan.thegameoflife.handlers.CellTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import static com.rowan.thegameoflife.handlers.CellTicker.cellHandler3d;

public class CellBlock extends Block {
    public static final IntegerProperty TYPE = IntegerProperty.create("type", 1, 16);

    public CellBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(TYPE, 1)
        );
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    CellHandler cellHandler = CellTicker.cellHandler;
    CellHandler cellHandlerBugs = CellTicker.cellHandlerBugs;
    CellHandler cellHandlerPatterns = CellTicker.cellHandlerPatterns;
    CellHandler cellHandler3d = CellTicker.cellHandler3d;
    CellHandler cellHandler3dv2 = CellTicker.CellHandler3dv2;


    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        int currentState = state.getValue(TYPE);
        if(currentState == 1) {
            cellHandler.addCell(pos.mutable());
        } else if (currentState == 2) {
            cellHandlerBugs.addCell(pos.mutable());
        } else if (currentState == 3) {
            cellHandlerPatterns.addCell(pos.mutable());
        } else if (currentState == 4) {
            cellHandler3d.addCell(pos.mutable());
        } else if (currentState == 5) {
            cellHandler3dv2.addCell(pos.mutable());
        } else if (currentState == 6) {

        } else if (currentState == 7) {

        } else if (currentState == 8) {

        } else if (currentState == 9) {

        } else if (currentState == 10) {

        } else if (currentState == 11) {

        } else if (currentState == 12) {

        } else if (currentState == 13) {

        } else if (currentState == 14) {

        } else if (currentState == 15) {

        } else if (currentState == 16) {

        }

        //System.out.println("hi");
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        int currentState = state.getValue(TYPE);
        if(currentState == 1) {
            cellHandler.remCell(pos.mutable());
        }
        //System.out.println("bye1");
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide){
            int currentState = state.getValue(TYPE);
            if(currentState < 16){
                level.setBlockAndUpdate(pos, state.setValue(TYPE, ++currentState));
            }else {
                level.setBlockAndUpdate(pos, state.setValue(TYPE, 1));
            }
        }
        return InteractionResult.SUCCESS;
    }
}
