package net.unkleacid.voidcalls.world.biome;

import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.worldgen.biome.StationBiome;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LeaveBiome extends Biome implements StationBiome {

    public LeaveBiome() {
        super();

        this.topBlockId = (byte) Block.STONE.id;
        this.soilBlockId = (byte) Block.STONE.id;

        this.setName("LEAVE.LEAVE.LEAVE.LEAVE.");
        this.setGrassColor(0x000000);
        this.setFoliageColor(0x000000);

        this.spawnableMonsters.clear();
        this.spawnablePassive.clear();
        this.spawnableWaterCreatures.clear();
    }

    @Override
    public Feature getRandomTreeFeature(Random random) {
        return null;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getSkyColor(float temperature) {
        return 0x000000; // Black sky
    }

    @Override
    public List getSpawnableEntities(net.minecraft.entity.SpawnGroup group) {
        return Collections.emptyList();
    }
}
