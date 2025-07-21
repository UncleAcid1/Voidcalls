package net.unkleacid.voidcalls.teleport;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.dimension.PortalForcer;

public class NoCreatePortalForcer extends PortalForcer {

    //just makes it so no portals are made
    @Override
    public boolean createPortal(World world, Entity entity) {
        return false;
    }
}
