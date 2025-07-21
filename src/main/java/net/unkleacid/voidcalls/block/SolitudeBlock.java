package net.unkleacid.voidcalls.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.unkleacid.voidcalls.teleport.SolitudeEntryPortalForcer;
import net.modificationstation.stationapi.api.block.CustomPortal;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.unkleacid.voidcalls.Voidcalls;

public class SolitudeBlock extends TemplateBlock implements CustomPortal {

    public SolitudeBlock(Identifier identifier) {
        super(identifier, Material.GLASS);
    }

    @Override
    public void onEntityCollision(World world, int x, int y, int z, Entity entity) {
        if (!world.isRemote && entity instanceof PlayerEntity player) {
            this.switchDimension(player);
            getTravelAgent(player).moveToPortal(world, player);
        }
    }

    @Override
    public Identifier getDimension(PlayerEntity playerEntity) {
        return Identifier.of(Voidcalls.NAMESPACE, "Solitude");
    }

    @Override
    public SolitudeEntryPortalForcer getTravelAgent(PlayerEntity playerEntity) {
        return new SolitudeEntryPortalForcer();
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
