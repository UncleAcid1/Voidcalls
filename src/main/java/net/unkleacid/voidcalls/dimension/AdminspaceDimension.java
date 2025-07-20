package net.unkleacid.voidcalls.dimension;

import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.dimension.Dimension;

public class AdminspaceDimension extends Dimension {
    public AdminspaceDimension(int id){
        this.id = id;
    }

    @Override
    public ChunkSource createChunkGenerator() {
        return new AdminspaceChunkGenerator(world, this.world.getSeed(), 1, 1);
    }

    @Override
    protected void initBiomeSource() {
        biomeSource = new AdminspaceBiomeSource(world);
    }
}
