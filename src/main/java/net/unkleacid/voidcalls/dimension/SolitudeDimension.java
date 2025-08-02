package net.unkleacid.voidcalls.dimension;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.dimension.Dimension;
import net.modificationstation.stationapi.api.world.dimension.StationDimension;

import java.util.Random;

public class SolitudeDimension extends Dimension implements StationDimension {

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
        // Keep it at midday if you like
        return 0.5F;
    }

    @Override
    public int getHeight() {
        return 136;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public float[] getBackgroundColor(float timeOfDay, float tickDelta) {
        // [ R, G, B, A ]
        return new float[] { 1.0F, 1.0F, 1.0F, 1.0F };
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Vec3d getFogColor(float timeOfDay, float tickDelta) {
        return Vec3d.createCached(1.0, 1.0, 1.0);
    }
}
