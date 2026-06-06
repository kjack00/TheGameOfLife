package com.rowan.thegameoflife.util;

import com.rowan.thegameoflife.TheGameOfLife;
import com.rowan.thegameoflife.components.ModDataComponents;
import com.rowan.thegameoflife.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemPropertys {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.CELL_CONTROLLER.get(), ResourceLocation.fromNamespaceAndPath(TheGameOfLife.MOD_ID, "used"),
                (itemStack, clientLevel, livingEntity, i) -> itemStack.get(ModDataComponents.PAUSEPLAY) == true ? 1f : 0f);
    }
}
