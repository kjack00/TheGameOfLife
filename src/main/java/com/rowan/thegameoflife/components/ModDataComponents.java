package com.rowan.thegameoflife.components;


import com.rowan.thegameoflife.TheGameOfLife;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(TheGameOfLife.MOD_ID);


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> TYPE =
            register("type", integerBuilder -> integerBuilder.persistent(Codec.INT));


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> PAUSEPLAY =
            register("pauseplay", booleanBuilder -> booleanBuilder.persistent(Codec.BOOL));



    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return DATA_COMPONENT_TYPES.register(name, ()-> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus iEventBus){
        DATA_COMPONENT_TYPES.register(iEventBus);
    }
}




