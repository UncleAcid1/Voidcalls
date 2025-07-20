package net.unkleacid.voidcalls.dimension;

import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.dimension.Dimension;

public class SolitudeDimension extends Dimension {
    public SolitudeDimension(int id){
        this.id = id;
    }

    @Override
    public ChunkSource createChunkGenerator() {
        return new SolitudeChunkGenerator(world, this.world.getSeed());
    }

    @Override
    protected void initBiomeSource() {
        biomeSource = new SolitudeBiomeSource(world);
    }
}
