package net.unkleacid.voidcalls.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSource;
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk;
import net.unkleacid.voidcalls.Voidcalls;

public class AdminspaceChunkGenerator implements ChunkSource {

    // Kiki, you can change these
    private static final int CELL_SIZE = 5; // interior size, excludes wall of thickness 1
    private static final int WALL_HEIGHT = 4;

    private static final int WALL_BLOCK_ID = Block.DIAMOND_BLOCK.id;
    private static final int FLOOR_BLOCK_ID = Voidcalls.ADMINSPACE_BLOCK.id;
    private static final int LIGHT_BLOCK_ID = Voidcalls.ADMINSPACE_LIGHT_BLOCK.id;

    // Kiki, you CANNOT change anything below this
    private static final int CELL_SIZE_PLUS_WALL = CELL_SIZE + 1;

    private final World world;

    public AdminspaceChunkGenerator(World world) {

        this.world = world;
    }

    @Override
    public Chunk getChunk(int chunkX, int chunkZ) {

        // initialize every chunk to be completely empty
        return new FlattenedChunk(world, chunkX, chunkZ);
    }

//    basically we're trying to connect regions which aren't already connected by placing doors on the current cel
//    we walk around to test their connectedness, which doesn't guarantee they're not connected but that's not really necessary for a maze anyways
//    means the maze will have loops which is honestly a feature not a bug so

//function generateCell(x, z) {
//
//    for all 4 walls of this cel {
//
//        step outside of the wall
//
//        if that area isn't generated yet {
//            stop
//        } else {
//
//            wander around randomly for 10 steps
//
//            if at any point we step back into the cel at x,z {
//                don't place a door on this wall
//            } else {
//                place a door on this wall
//            }
//        }
//    }
//}

    @Override
    public void decorate(ChunkSource src, int chunkX, int chunkZ) {

        chunkX *= 16;
        chunkZ *= 16;

        // cell position is misaligned with chunks (since they're different sizes) so calculate
        // every cell whose origin (corner) is within this chunk
        // (the algorithm is kinda scuffed but it's ok)
        for (int x = chunkX + (CELL_SIZE_PLUS_WALL - (chunkX % CELL_SIZE_PLUS_WALL)) % CELL_SIZE_PLUS_WALL; x < chunkX + 16; x += CELL_SIZE_PLUS_WALL) {
            for (int z = chunkZ + (CELL_SIZE_PLUS_WALL - (chunkZ % CELL_SIZE_PLUS_WALL)) % CELL_SIZE_PLUS_WALL; z < chunkZ + 16; z += CELL_SIZE_PLUS_WALL) {

                placeWalledCell(x, z);

                // perform door-placing algorithm
                // step outside every wall, checking if we can navigate back. if not, place a door
                if (!hasNegXDoor(x, z) && tryDiscoverCellFrom(x, z, x - 1, z, 10))
                    placeNegXDoor(x, z);

                if (!hasPosXDoor(x, z) && tryDiscoverCellFrom(x, z, x + 1, z, 10))
                    placePosXDoor(x, z);

                if (!hasNegZDoor(x, z) && tryDiscoverCellFrom(x, z, x, z - 1, 10))
                    placeNegZDoor(x, z);

                if (!hasPosZDoor(x, z) && tryDiscoverCellFrom(x, z, x, z + 1, 10))
                    placePosZDoor(x, z);
            }
        }
    }

    private boolean tryDiscoverCellFrom(int cellX, int cellZ, int fromX, int fromZ, int maxSteps) {

        // area isn't generated yet
        if (world.getBlockId(fromX, 0, fromZ) == 0)
            return false;

        return Math.random() > 0.5;
    }

    private boolean hasNegXDoor(int cellX, int cellZ) {

        return world.getBlockId(cellX, 1, cellZ + 1) == 0;
    }

    private boolean hasPosXDoor(int cellX, int cellZ) {

        return world.getBlockId(cellX + CELL_SIZE_PLUS_WALL, 1, cellZ + 1) == 0;
    }

    private boolean hasNegZDoor(int cellX, int cellZ) {

        return world.getBlockId(cellX + 1, 1, cellZ) == 0;
    }

    private boolean hasPosZDoor(int cellX, int cellZ) {

        return world.getBlockId(cellX + 1, 1, cellZ + CELL_SIZE_PLUS_WALL) == 0;
    }

    private void placeNegXDoor(int cellX, int cellZ) {

        for (int i = 1; i < CELL_SIZE_PLUS_WALL; i++)
            world.setBlockWithoutNotifyingNeighbors(cellX, 1, cellZ + i, 0);
    }

    private void placePosXDoor(int cellX, int cellZ) {
        placeNegXDoor(cellX + CELL_SIZE_PLUS_WALL, cellZ);
    }

    private void placeNegZDoor(int cellX, int cellZ) {

        for (int i = 1; i < CELL_SIZE_PLUS_WALL; i++)
            world.setBlockWithoutNotifyingNeighbors(cellX + i, 1, cellZ, 0);
    }

    private void placePosZDoor(int cellX, int cellZ) {
        placeNegZDoor(cellX + CELL_SIZE_PLUS_WALL, cellZ);
    }

    private void placeWalledCell(int cellX, int cellZ) {

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
    }

    // note from Dairycultist: something tells me these should be implemented, like, better
    @Override public Chunk loadChunk(int x, int z) { return getChunk(x, z); }
    @Override public boolean isChunkLoaded(int x, int z) { return true; }
    @Override public boolean save(boolean a, net.minecraft.client.gui.screen.LoadingDisplay d) { return true; }
    @Override public boolean tick() { return false; }
    @Override public boolean canSave() { return true; }

    @Environment(EnvType.CLIENT)
    public String getDebugInfo() { return "AdminspaceMaze"; }
}
