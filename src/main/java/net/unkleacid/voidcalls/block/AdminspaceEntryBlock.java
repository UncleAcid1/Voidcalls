package net.unkleacid.voidcalls.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.unkleacid.voidcalls.teleport.NoCreatePortalForcer;
import net.modificationstation.stationapi.api.block.CustomPortal;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.unkleacid.voidcalls.Voidcalls;

public class AdminspaceEntryBlock extends TemplateBlock implements CustomPortal {

    public AdminspaceEntryBlock(Identifier identifier) {
        super(identifier, Material.GLASS);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        switchDimension(player);
        return true;
    }

    @Override
    public Identifier getDimension(PlayerEntity playerEntity) {
        return Identifier.of(Voidcalls.NAMESPACE, "adminspace");
    }

    @Override
    public NoCreatePortalForcer getTravelAgent(PlayerEntity playerEntity) {
        return new NoCreatePortalForcer();
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
