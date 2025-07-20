package net.unkleacid.voidcalls.feature;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.unkleacid.voidcalls.Voidcalls;

import java.util.Random;

public class AdminspaceMazeCellFeature extends Feature {

    // Kiki, you can change these
    public static final int CELL_SIZE = 5; // interior size, excludes wall of thickness 1
    public static final int WALL_HEIGHT = 4;

    public static final int WALL_BLOCK_ID = Block.DIAMOND_BLOCK.id;
    public static final int FLOOR_BLOCK_ID = Voidcalls.ADMINSPACE_BLOCK.id;
    public static final int LIGHT_BLOCK_ID = Voidcalls.ADMINSPACE_LIGHT_BLOCK.id;

    // Kiki, you CANNOT change anything below this
    public static final int CELL_SIZE_PLUS_WALL = CELL_SIZE + 1;

    @Override
    public boolean generate(World world, Random random, int cellX, int unused, int cellZ) {

        // walls
        for (int i = 0; i < CELL_SIZE_PLUS_WALL; i++) {

            world.setBlockWithoutNotifyingNeighbors(cellX + i, 1, cellZ, WALL_BLOCK_ID);
            world.setBlockWithoutNotifyingNeighbors(cellX, 1, cellZ + i, WALL_BLOCK_ID);
        }

        // floor
        for (int x = 0; x < CELL_SIZE_PLUS_WALL; x++) {
            for (int z = 0; z < CELL_SIZE_PLUS_WALL; z++) {

                world.setBlockWithoutNotifyingNeighbors(cellX + x, 0, cellZ + z, FLOOR_BLOCK_ID);
            }
        }

        // lights
        world.setBlock(cellX + 3, 0, cellZ + 3, LIGHT_BLOCK_ID);
        world.setBlock(cellX + 3, 0, cellZ, LIGHT_BLOCK_ID);
        world.setBlock(cellX, 0, cellZ + 3, LIGHT_BLOCK_ID);

        // TODO check if we can connect to adjacent maze cells that aren't already reachable by us
        // TODO basically we're trying to connect regions which aren't already connected by placing doors on the current cel

        // randomly place doors
        if (random.nextBoolean())
            placeNegXDoor(world, cellX, cellZ);

        if (random.nextBoolean())
            placeNegZDoor(world, cellX, cellZ);

        return true;
    }

//    private boolean hasNegXDoor(int cellX, int cellZ) {
//
//        return world.getBlockId(cellX, 1, cellZ + 1) == 0;
//    }
//
//    private boolean hasPosXDoor(int cellX, int cellZ) {
//
//        return world.getBlockId(cellX + CELL_SIZE_PLUS_WALL, 1, cellZ + 1) == 0;
//    }
//
//    private boolean hasNegZDoor(int cellX, int cellZ) {
//
//        return world.getBlockId(cellX + 1, 1, cellZ) == 0;
//    }
//
//    private boolean hasPosZDoor(int cellX, int cellZ) {
//
//        return world.getBlockId(cellX + 1, 1, cellZ + CELL_SIZE_PLUS_WALL) == 0;
//    }

    private void placeNegXDoor(World world, int cellX, int cellZ) {

        for (int i = 1; i < CELL_SIZE_PLUS_WALL; i++)
            world.setBlockWithoutNotifyingNeighbors(cellX, 1, cellZ + i, 0);
    }

//    private void placePosXDoor(World world, int cellX, int cellZ) {
//        placeNegXDoor(world, cellX + CELL_SIZE_PLUS_WALL, cellZ);
//    }

    private void placeNegZDoor(World world, int cellX, int cellZ) {

        for (int i = 1; i < CELL_SIZE_PLUS_WALL; i++)
            world.setBlockWithoutNotifyingNeighbors(cellX + i, 1, cellZ, 0);
    }

//    private void placePosZDoor(World world, int cellX, int cellZ) {
//        placeNegZDoor(world, cellX + CELL_SIZE_PLUS_WALL, cellZ);
//    }
}
