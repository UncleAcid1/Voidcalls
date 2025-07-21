package net.unkleacid.voidcalls.dimension;

import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.dimension.Dimension;

public class SolitudeDimension extends Dimension {
    public SolitudeDimension(int id) {
        this.id = id;
    }

    @Override
    protected void initBiomeSource() {
        biomeSource = new SolitudeBiomeSource(world);
    }

    @Override
    public ChunkSource createChunkGenerator() {
        return new SolitudeChunkGenerator(world, world.getSeed());
    }

    @Override
    public float getTimeOfDay(long worldTime, float delta) {
        return 0.5F;
    }
}
