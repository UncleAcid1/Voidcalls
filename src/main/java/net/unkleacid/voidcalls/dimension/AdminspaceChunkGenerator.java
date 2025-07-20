package net.unkleacid.voidcalls.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSource;
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk;
import net.unkleacid.voidcalls.Voidcalls;
import net.unkleacid.voidcalls.feature.AdminspaceMazeCellFeature;

public class AdminspaceChunkGenerator implements ChunkSource {

    private final World world;

    public AdminspaceChunkGenerator(World world) {

        this.world = world;
    }

    @Override
    public Chunk getChunk(int chunkX, int chunkZ) {

        // initialize every chunk to be completely empty
        return new FlattenedChunk(world, chunkX, chunkZ);
    }

    @Override
    public void decorate(ChunkSource src, int chunkX, int chunkZ) {

        chunkX *= 16;
        chunkZ *= 16;

        int w = AdminspaceMazeCellFeature.CELL_SIZE_PLUS_WALL;

        // cell position is misaligned with chunks (since they're different sizes) so calculate
        // every cell whose origin (corner) is within this chunk
        // (the algorithm is kinda scuffed but it's ok)
        for (int x = chunkX + (w - (chunkX % w)) % w; x < chunkX + 16; x += w) {
            for (int z = chunkZ + (w - (chunkZ % w)) % w; z < chunkZ + 16; z += w) {

                new AdminspaceMazeCellFeature().generate(world, world.random, x, 0, z);
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
