package net.unkleacid.voidcalls.dimension;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.unkleacid.voidcalls.world.biome.LeaveBiome;

public class SolitudeBiomeSource extends BiomeSource {
    public static final Biome LEAVE_BIOME = new LeaveBiome();

    public SolitudeBiomeSource(World world) {
        super(world);
    }

    @Override
    public Biome[] getBiomesInArea(Biome[] biomes, int x, int z, int width, int depth) {
        biomes = new Biome[width * depth];
        this.temperatureMap = new double[width * depth];
        this.downfallMap = new double[width * depth];
        this.weirdnessMap = new double[width * depth];

        for (int i = 0; i < biomes.length; i++) {
            biomes[i] = LEAVE_BIOME;

            // Avoid crashes with dummy data
            temperatureMap[i] = 0.0;
            downfallMap[i] = 0.0;
            weirdnessMap[i] = 0.0;
        }

        return biomes;
    }
}
