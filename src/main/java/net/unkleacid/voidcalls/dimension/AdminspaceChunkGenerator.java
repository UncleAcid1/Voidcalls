package net.unkleacid.voidcalls.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSource;
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk;
import net.unkleacid.voidcalls.Voidcalls;

public class AdminspaceChunkGenerator implements ChunkSource {
    private static final int CELL_SIZE = 5;
    private static final int WALL_HEIGHT = 4;
    private final World world;
    private static Maze maze = null;

    public AdminspaceChunkGenerator(World world, long seed, int mazeWidth, int mazeHeight) {
        this.world = world;
        if (maze == null) {
            long start = System.currentTimeMillis();
            maze = new Maze(mazeWidth, mazeHeight, seed);
            System.out.println("Maze generated in " + (System.currentTimeMillis() - start) + "ms");
        }
        this.maze = maze;
    }


    @Override
    public Chunk getChunk(int chunkX, int chunkZ) {
        if (chunkX < 0 || chunkZ < 0 || chunkX >= maze.width || chunkZ >= maze.height) {
            return emptyChunk(chunkX, chunkZ);
        }

        byte[] blocks = new byte[16 * 128 * 16];
        Chunk chunk = new Chunk(world, blocks, chunkX, chunkZ);

        int offX = (16 - CELL_SIZE) / 2;
        int offZ = (16 - CELL_SIZE) / 2;

        for (int x = 0; x < CELL_SIZE; x++) {
            for (int z = 0; z < CELL_SIZE; z++) {
                int globalIdx = ((offX + x) * 128 + 0) * 16 + (offZ + z);
                boolean inMiddleThird = (x >= CELL_SIZE/3 && x < CELL_SIZE*2/3);
                if (inMiddleThird && (z % 2 == 0)) {
                    blocks[globalIdx] = (byte) Voidcalls.ADMINSPACE_LIGHT_BLOCK.id;
                } else {
                    blocks[globalIdx] = (byte) Voidcalls.ADMINSPACE_BLOCK.id;
                }
            }
        }

        buildWall(blocks, offX, offZ, maze.southOpen[chunkX][chunkZ], true);
        buildWall(blocks, offX, offZ + CELL_SIZE - 1,
                chunkZ > 0 && maze.southOpen[chunkX][chunkZ - 1], true);
        buildWall(blocks, offX, offZ, maze.eastOpen[chunkX][chunkZ], false);
        buildWall(blocks, offX + CELL_SIZE - 1, offZ,
                chunkX > 0 && maze.eastOpen[chunkX - 1][chunkZ], false);

        chunk.populateHeightMap();
        FlattenedChunk flat = new FlattenedChunk(world, chunkX, chunkZ);
        flat.fromLegacy(blocks);
        flat.populateHeightMap();
        return flat;
    }

    private void buildWall(byte[] blocks, int ox, int oz, boolean hasDoor, boolean horizontal) {
        int doorPos    = CELL_SIZE / 2;
        int doorHeight = 2;
        for (int h = 1; h <= WALL_HEIGHT; h++) {
            for (int i = 0; i < CELL_SIZE; i++) {
                if (hasDoor && i == doorPos && h <= doorHeight) {
                    continue;
                }
                int x = horizontal ? ox + i : ox;
                int z = horizontal ? oz : oz + i;
                int idx = (x * 128 + h) * 16 + z;
                blocks[idx] = (byte) Voidcalls.ADMINSPACE_BLOCK.id;
            }
        }
    }

    private Chunk emptyChunk(int cx, int cz) {
        byte[] blocks = new byte[16 * 128 * 16];
        Chunk chunk = new Chunk(world, blocks, cx, cz);
        FlattenedChunk flat = new FlattenedChunk(world, cx, cz);
        flat.fromLegacy(blocks);
        flat.populateHeightMap();
        return flat;
    }

    @Override public Chunk loadChunk(int x, int z) { return getChunk(x, z); }
    @Override public boolean isChunkLoaded(int x, int z) { return true; }
    @Override public void decorate(ChunkSource src, int x, int z) { }
    @Override public boolean save(boolean a, net.minecraft.client.gui.screen.LoadingDisplay d) { return true; }
    @Override public boolean tick() { return false; }
    @Override public boolean canSave() { return true; }

    @Environment(EnvType.CLIENT)
    public String getDebugInfo() { return "AdminspaceMaze"; }
}
