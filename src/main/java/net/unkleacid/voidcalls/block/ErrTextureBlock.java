package net.unkleacid.voidcalls.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class ErrTextureBlock extends TemplateBlock {

    public ErrTextureBlock(Identifier id) {
        super(id, Material.AIR);
        this.setTranslationKey(id.toString());
        this.setOpacity(0);
        this.setHardness(0.0F);
        this.setBoundingBox(0, 0, 0, 1, 1, 1);
        this.setTickRandomly(true);
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        if (world.hasSkyLight(x, y + 1, z) && world.getLightLevel(x, y + 1, z) >= 15) {
            int meta = world.getBlockMeta(x, y, z) + 1;
            if (meta >= 1200) {
                world.setBlock(x, y, z, 0);
            } else {
                world.setBlockMeta(x, y, z, meta);
            }
        }
    }

    @Override public Box getCollisionShape(World world, int x, int y, int z) { return null; }
    @Override public boolean isOpaque()      { return false; }
    @Override public boolean isFullCube()    { return false; }
}
