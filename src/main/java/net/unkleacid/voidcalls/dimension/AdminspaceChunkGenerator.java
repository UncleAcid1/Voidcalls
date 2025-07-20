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

    private static final int CELL_SIZE = 5; // interior size, excludes wall of thickness 1
    private static final int CELL_SIZE_PLUS_WALL = CELL_SIZE + 1;
    private static final int WALL_HEIGHT = 4;

    private final World world;

    public AdminspaceChunkGenerator(World world) {

        this.world = world;
    }

    @Override
    public Chunk getChunk(int chunkX, int chunkZ) {

        // initialize every chunk to be completely empty
        // (except for a bedrock floor for testing convenience)
        byte[] blocks = new byte[16 * 128 * 16];

        for (int i=0; i<blocks.length; i += 128)
            blocks[i] = (byte) Block.BEDROCK.id;

        FlattenedChunk empty = new FlattenedChunk(world, chunkX, chunkZ);
        empty.fromLegacy(blocks);
        empty.populateHeightMap();

        return empty;
    }

//function generateCel(x, z) {
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

                // TODO perform door-placing algorithm
            }
        }
    }

    private void placeWalledCell(int originX, int originZ) {

        for (int x = 1; x < CELL_SIZE; x++) {
            for (int z = 1; z < CELL_SIZE; z++) {

                world.setBlock(originX + x, 64, originZ + z, Voidcalls.ADMINSPACE_BLOCK.id);
            }
        }
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
