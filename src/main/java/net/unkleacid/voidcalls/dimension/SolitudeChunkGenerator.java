package net.unkleacid.voidcalls.dimension;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.LoadingDisplay;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSource;
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk;

public class SolitudeChunkGenerator implements ChunkSource {
    private final Random random;
    private final OctavePerlinNoiseSampler minLimitPerlinNoise;
    private final OctavePerlinNoiseSampler maxLimitPerlinNoise;
    private final OctavePerlinNoiseSampler perlinNoise1;
    private final World world;
    private double[] heightMap;
    private Biome[] biomes;

    public SolitudeChunkGenerator(World world, long seed) {
        this.world = world;
        this.random = new Random(seed);
        this.minLimitPerlinNoise = new OctavePerlinNoiseSampler(this.random, 16);
        this.maxLimitPerlinNoise = new OctavePerlinNoiseSampler(this.random, 16);
        this.perlinNoise1 = new OctavePerlinNoiseSampler(this.random, 8);
    }

    public void buildTerrain(int chunkX, int chunkZ, byte[] blocks) {
        int width = 2, height = 33, depth = 2;
        this.heightMap = generateHeightMap(this.heightMap, chunkX * width, 0, chunkZ * depth, width + 1, height, depth + 1);

        for (int x = 0; x < width; ++x) {
            for (int z = 0; z < depth; ++z) {
                for (int y = 0; y < 32; ++y) {
                    double v000 = heightMap[((x + 0) * (depth + 1) + z + 0) * height + y + 0];
                    double v001 = heightMap[((x + 0) * (depth + 1) + z + 0) * height + y + 1];
                    double dy = (v001 - v000) * 0.25;

                    for (int i = 0; i < 4; ++i) {
                        double value = v000 + dy * i;

                        for (int j = 0; j < 8; ++j) {
                            for (int k = 0; k < 8; ++k) {
                                int idx = (x * 8 + j) << 11 | (z * 8 + k) << 7 | (y * 4 + i);
                                blocks[idx] = value > 0 ? (byte) Block.BEDROCK.id : 0;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Chunk getChunk(int chunkX, int chunkZ) {
        this.random.setSeed((long)chunkX * 341873128712L + (long)chunkZ * 132897987541L);
        byte[] blocks = new byte[32768]; // 16 * 128 * 16
        Chunk chunk = new Chunk(this.world, blocks, chunkX, chunkZ);
        buildTerrain(chunkX, chunkZ, blocks);
        chunk.populateHeightMap();
        FlattenedChunk flatChunk = new FlattenedChunk(world, chunkX, chunkZ);
        flatChunk.fromLegacy(blocks);
        flatChunk.populateHeightMap();
        return flatChunk;
    }

    private double[] generateHeightMap(double[] map, int x, int y, int z, int sizeX, int sizeY, int sizeZ) {
        if (map == null) map = new double[sizeX * sizeY * sizeZ];
        double scale = 684.412;
        double depth = 684.412;

        double[] noise1 = perlinNoise1.create(null, x, y, z, sizeX, sizeY, sizeZ, scale / 80, depth / 160, scale / 80);
        double[] noise2 = minLimitPerlinNoise.create(null, x, y, z, sizeX, sizeY, sizeZ, scale, depth, scale);
        double[] noise3 = maxLimitPerlinNoise.create(null, x, y, z, sizeX, sizeY, sizeZ, scale, depth, scale);

        for (int i = 0; i < map.length; ++i) {
            double blend = (noise1[i] / 10.0 + 1.0) / 2.0;
            if (blend < 0) map[i] = noise2[i] / 512.0 - 8.0;
            else if (blend > 1) map[i] = noise3[i] / 512.0 - 8.0;
            else map[i] = (noise2[i] + (noise3[i] - noise2[i]) * blend) / 512.0 - 8.0;
        }

        return map;
    }

    @Override public Chunk loadChunk(int x, int z) { return getChunk(x, z); }
    @Override public boolean isChunkLoaded(int x, int z) { return true; }
    @Override public void decorate(ChunkSource source, int x, int z) { /* No decoration */ }
    @Override public boolean save(boolean saveEntities, LoadingDisplay display) { return true; }
    @Override public boolean tick() { return false; }
    @Override public boolean canSave() { return true; }

    @Environment(EnvType.CLIENT)
    public String getDebugInfo() {
        return "SolitudeChunkGenerator (Bedrock floating islands)";
    }
}
