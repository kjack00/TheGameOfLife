package com.rowan.thegameoflife.item.custom;

import com.rowan.thegameoflife.block.Custom.CellBlock;
import com.rowan.thegameoflife.block.ModBlocks;
import com.rowan.thegameoflife.components.ModDataComponents;
import com.rowan.thegameoflife.handlers.CellHandler;
import com.rowan.thegameoflife.handlers.CellTicker;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;

public class CellController extends Item {
    public CellController(Properties properties) {
        super(properties.component(ModDataComponents.PAUSEPLAY, false));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack item = player.getItemInHand(usedHand);
        boolean pauseplay = item.get(ModDataComponents.PAUSEPLAY);
        if(!player.isCrouching()) {
            item.set(ModDataComponents.PAUSEPLAY, !pauseplay);
        }
        if (item.has(ModDataComponents.TYPE)) {
            int type = item.get(ModDataComponents.TYPE);
            if (pauseplay) {
                CellTicker.pauseCells(type);
            } else {
                CellTicker.playCells(type);
            }

        } else {
            item.set(ModDataComponents.TYPE, -1);
        }


        return super.use(level, player, usedHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        assert player != null;
        if(player.isCrouching()) {
            ItemStack item = context.getItemInHand();
            BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
            if (blockState.is(ModBlocks.CELL_BLOCK)) {
                int type = blockState.getValue(CellBlock.TYPE);
                item.set(ModDataComponents.TYPE, type);
            }else{
                item.set(ModDataComponents.TYPE, -1);
            }
        }
        return super.useOn(context);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        int type = -1;
        boolean pausePlay = true;
        HashSet<CellHandler> pausedHandlers = CellTicker.pausedHandlers;
        if (stack.has(ModDataComponents.TYPE)) {
            type = stack.get(ModDataComponents.TYPE);
        }
        if (stack.has(ModDataComponents.PAUSEPLAY)) {
            pausePlay = stack.get(ModDataComponents.PAUSEPLAY);
        }
        if(CellTicker.checkCells(type)){
            stack.set(ModDataComponents.PAUSEPLAY, false);
        } else {
            stack.set(ModDataComponents.PAUSEPLAY, true);
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        int type = -1;
        boolean pausePlay = true;
        if (stack.has(ModDataComponents.TYPE)) {
            type = stack.get(ModDataComponents.TYPE);
        }
        if (stack.has(ModDataComponents.PAUSEPLAY)) {
            pausePlay = stack.get(ModDataComponents.PAUSEPLAY);
        }
        if( type == -1 ){
            tooltipComponents.add(Component.literal("Selected Cell Type: All"));
        }else {
            tooltipComponents.add(Component.literal("Selected Cell Type: " + type));
        }
        if(pausePlay){
            tooltipComponents.add(Component.literal("Playing"));
        }else{
            tooltipComponents.add(Component.literal("Paused"));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

}
