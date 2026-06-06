package com.rowan.thegameoflife.handlers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = "thegameoflife")
public class CellTicker {
    public static CellHandler cellHandler = new CellHandler(1);
    public static CellHandler cellHandlerBugs = new CellHandlerBugs(2);
    public static CellHandler cellHandlerPatterns = new CellHandlerPatterns(3);
    public static CellHandler cellHandler3d = new CellHandler3D(4);
    public static CellHandler CellHandler3dv2 = new CellHandler3Dv2(5);

    public static HashSet<CellHandler> handlersC = new HashSet<>();
    public static HashSet<CellHandler> pausedHandlers = new HashSet<>();

    public static void setup(){
        handlersC.add(cellHandler);
        handlersC.add(cellHandlerBugs);
        handlersC.add(cellHandlerPatterns);
        handlersC.add(cellHandler3d);
        handlersC.add(CellHandler3dv2);

    }

    public static void pauseCells(int cellType){
        if(cellType == - 1){
            pausedHandlers.addAll(handlersC);

        } else {
            for (CellHandler handler : handlersC) {
                if(handler.type == cellType){
                    pausedHandlers.add(handler);
                }
            }

        }

    }
    public static void playCells(int cellType){
        if(cellType == - 1){
            pausedHandlers.removeAll(handlersC);

        } else {
            for (CellHandler handler : handlersC) {
                if(handler.type == cellType){
                    pausedHandlers.remove(handler);
                }
            }

        }

    }
    public static boolean checkCells(int cellType){
        if(cellType == - 1 && pausedHandlers.containsAll(handlersC)){
            return true; // this means that if you unpause any cell it will appear unpaused
        } else {
            for (CellHandler handler : pausedHandlers) {
                if(handler.type == cellType){
                    return true;
                }
            }

        }
        return false;
    }



    static int tick = 0;
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if(tick%10 == 0) {
            for (CellHandler handler : handlersC) {
                if(!pausedHandlers.contains(handler)){
                    handler.tickCells(event.getServer().overworld()); //im to lazy to make it work evry where mab later
                }

               // System.out.println("ticked");
            }


            //
        }
        tick++;
    }
}
