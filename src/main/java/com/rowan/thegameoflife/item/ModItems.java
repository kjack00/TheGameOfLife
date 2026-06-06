package com.rowan.thegameoflife.item;


import com.rowan.thegameoflife.TheGameOfLife;
import com.rowan.thegameoflife.item.custom.CellController;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(TheGameOfLife.MOD_ID);

    public static final DeferredItem<Item> CELL_CONTROLLER = ITEMS.registerItem("cell_controller",
            CellController::new, new Item.Properties());

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }



}
