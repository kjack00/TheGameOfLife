package com.rowan.thegameoflife.handlers;

import com.rowan.thegameoflife.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CellHandlerPatterns extends  CellHandler {


    CellHandlerPatterns(int cellType) {
        super(cellType);
    }

    @Override
    public int checkNeighborhood(BlockPos.MutableBlockPos pos, ServerLevel level, boolean addlist){
        int count = 0;
        for(int x = -5; x <= 5; ++x){
            for(int z = -5; z <= 5; ++z){
                if (x == 0 && z == 0) continue;
                BlockPos.MutableBlockPos neighbor = pos;
                neighbor = neighbor.offset(x, 0, z).mutable();
                if(!cellList.contains(neighbor) && addlist){
                    birthCanadateList.add(neighbor);
                }
                if(cellList.contains(neighbor)){
                    count += 1;
                }
            }
        }
        return count;
    }

    @Override
    public void appRules(BlockPos.MutableBlockPos pos, ServerLevel level) {
        int count = checkNeighborhood(pos, level, true);
        if(count == 1 || count == 5 || count == 6 || count == 7 || count == 8){
            //survives
        }else{
            deathList.add(pos);
        }
    }

    @Override
    public void birthRules(BlockPos.MutableBlockPos pos, ServerLevel level) {
        int count = checkNeighborhood(pos, level, false);
        if (count == 3 || count == 5 || count == 6 || count == 7){
            birthList.add(pos);
        }
    }
}
