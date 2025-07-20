package net.unkleacid.voidcalls.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.dimension.PortalForcer;
import net.modificationstation.stationapi.api.block.CustomPortal;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.unkleacid.voidcalls.Voidcalls;

public class SolitudeBlock extends TemplateBlock implements CustomPortal {

    public SolitudeBlock(Identifier identifier) {
        super(identifier, Material.GLASS);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        switchDimension(player);
        return true;
    }

    @Override
    public Identifier getDimension(PlayerEntity playerEntity) {
        return Identifier.of(Voidcalls.NAMESPACE, "Solitude");
    }

    @Override
    public PortalForcer getTravelAgent(PlayerEntity playerEntity) {
        return new PortalForcer();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getRenderLayer() {
        return 0;
    }
}
