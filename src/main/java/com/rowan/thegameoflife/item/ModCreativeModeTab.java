package com.rowan.thegameoflife.item;

import com.rowan.thegameoflife.TheGameOfLife;
import com.rowan.thegameoflife.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTab {
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TheGameOfLife.MOD_ID);

    public static final Supplier<CreativeModeTab> CELL_TAB = CREATIVE_MODE_TAB.register("cell_tab",
            ()-> CreativeModeTab.builder()
                    .icon(()-> new ItemStack(Blocks.BLACK_CONCRETE))
                    .title(Component.translatable("creativetab.thegameoflife.cell_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.CELL_BLOCK);
                        output.accept(ModItems.CELL_CONTROLLER);

                    })

                    .build());

    public static void register(IEventBus iEventBus){
        CREATIVE_MODE_TAB.register(iEventBus);
    }
}
