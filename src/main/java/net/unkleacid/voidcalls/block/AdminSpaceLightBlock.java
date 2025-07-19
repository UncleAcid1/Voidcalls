package net.unkleacid.voidcalls.block;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class AdminSpaceLightBlock extends TemplateBlock {

    public AdminSpaceLightBlock(Identifier id) {
        super(id, Material.STONE);
        this.setTranslationKey(id.toString());
        this.setOpacity(1);
        this.setLuminance(1F);
        this.setHardness(80.0F);
        this.setBoundingBox(0, 0, 0, 1, 1, 1);
        this.setTickRandomly(true);
    }
}
