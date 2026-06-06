package com.rowan.thegameoflife.handlers;

import com.rowan.thegameoflife.block.Custom.CellBlock;
import com.rowan.thegameoflife.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.*;
import java.util.logging.Level;

public class CellHandler {
    public Set<BlockPos.MutableBlockPos> cellList = new HashSet<>();
    public Set<BlockPos.MutableBlockPos> birthCanadateList = new HashSet<>();
    public Set<BlockPos.MutableBlockPos> birthList = new HashSet<>();
    public Set<BlockPos.MutableBlockPos> deathList = new HashSet<>();
    protected int type = 1;

    int neghborhoodNX = -1;
    int neghborhoodPX = 1;
    int neghborhoodNY = -1;
    int neghborhoodPY = 1;
    CellHandler(int cellType){
        this.type = cellType;
    }

    public void addCell(BlockPos.MutableBlockPos pos){
        cellList.add(pos);
    }

    public void remCell(BlockPos.MutableBlockPos pos){
        cellList.remove(pos);
    }


    public int checkNeighborhood(BlockPos.MutableBlockPos pos, ServerLevel level, boolean addlist) {
        int count = 0;
        for (int x = neghborhoodNX; x <= neghborhoodPX; ++x) {
            for (int z = neghborhoodNY; z <= neghborhoodPY; ++z) {
                if (x == 0 && z == 0) continue;
                BlockPos.MutableBlockPos neighbor = pos;
                neighbor = neighbor.offset(x, 0, z).mutable();
                if (!cellList.contains(neighbor) && addlist) {
                    birthCanadateList.add(neighbor);
                }
                if (cellList.contains(neighbor)) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public void appRules(BlockPos.MutableBlockPos pos, ServerLevel level) {
        int count = checkNeighborhood(pos, level, true);
        if (count < 2) {
            deathList.add(pos);
        } else if (count <= 3) {
            //survives
        } else if (count > 3) {
            deathList.add(pos);
        }

    }

    public void birthRules(BlockPos.MutableBlockPos pos, ServerLevel level) {
        int count = checkNeighborhood(pos, level, false);
        if (count == 3) {
            birthList.add(pos);
        }
    }

    public void tickCells(ServerLevel level) {
        //System.out.println(cellList);
        birthCanadateList.clear();
        birthList.clear();
        deathList.clear();

        for (BlockPos.MutableBlockPos pos : new ArrayList<>(cellList)) {
            appRules(pos, level);
        }
        for (BlockPos.MutableBlockPos pos : new ArrayList<>(birthCanadateList)) {
            birthRules(pos, level);
        }
        for (BlockPos.MutableBlockPos pos : new ArrayList<>(deathList)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            cellList.remove(pos);
        }
        for (BlockPos.MutableBlockPos pos : new ArrayList<>(birthList)) {
            level.setBlock(pos, ModBlocks.CELL_BLOCK.get().defaultBlockState().setValue(CellBlock.TYPE, type), 3);
        }
        //System.out.println(this);
        //System.out.println(type);
    }
}