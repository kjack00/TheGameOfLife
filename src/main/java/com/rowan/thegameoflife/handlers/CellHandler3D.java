package com.rowan.thegameoflife.handlers;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class CellHandler3D extends CellHandler{


    CellHandler3D(int cellType) {
        super(cellType);
    }

    @Override
    public int checkNeighborhood(BlockPos.MutableBlockPos pos, ServerLevel level, boolean addlist){
        int count = 0;
        for(int x = -1; x <= 1; ++x){
            for(int z = -1; z <= 1; ++z){
                for(int y = -1; y <= 1; ++y) {
                    if (x == 0 && z == 0 && y == 0) continue;
                    BlockPos.MutableBlockPos neighbor = pos;
                    neighbor = neighbor.offset(x, y, z).mutable();
                    if(!cellList.contains(neighbor) && addlist){
                        birthCanadateList.add(neighbor);
                    }
                    if(cellList.contains(neighbor)){
                        count += 1;
                    }
                }

            }
        }
        return count;
    }

    @Override
    public void appRules(BlockPos.MutableBlockPos pos, ServerLevel level) {
        int count = checkNeighborhood(pos, level, true);
        if (count < 5) {
            deathList.add(pos);
        } else if (count < 8) {
            //survives
        } else if (count == 8) {
            deathList.add(pos);
        }

    }

    @Override
    public void birthRules(BlockPos.MutableBlockPos pos, ServerLevel level) {
        int count = checkNeighborhood(pos, level, false);
        if (count == 5) {
            birthList.add(pos);
        }
    }
}
