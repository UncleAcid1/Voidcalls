package net.unkleacid.voidcalls.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class ErrLightBlock extends TemplateBlock {

    public ErrLightBlock(Identifier id) {
        super(id, Material.STONE);
        this.setTranslationKey(id.toString());
        this.setOpacity(0);
        this.setLuminance(1F);
        this.setBoundingBox(0, 0, 0, 0, 0, 0);
        this.setTickRandomly(true);
    }


    @Override
    public void onTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote) {
            world.setBlock(x, y, z, 0);
        }
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public int getRenderLayer() {
        return 1;
    }
}
