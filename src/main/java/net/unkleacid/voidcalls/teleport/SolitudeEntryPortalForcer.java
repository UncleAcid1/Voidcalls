package net.unkleacid.voidcalls.teleport;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.dimension.PortalForcer;

/**
 * A custom PortalForcer for Solitude entry that finds the upper
 * terrain surface by scanning downward from the world ceiling,
 * so players never spawn inside the bottom return‐block layer.
 */
public class SolitudeEntryPortalForcer extends PortalForcer {

    @Override
    public boolean createPortal(World world, Entity entity) {
        // We never actually create a portal—always teleport directly.
        return false;
    }

    @Override
    public boolean teleportToValidPortal(World world, Entity entity) {
        if (!(entity instanceof PlayerEntity)) {
            return false;
        }

        int ix = MathHelper.floor(entity.x);
        int iz = MathHelper.floor(entity.z);

        // scan down from the world top to find the first solid block
        int maxY = world.getHeight();
        int surfaceY = 0;
        for (int y = maxY; y >= 0; y--) {
            if (!world.isAir(ix, y, iz)) {
                surfaceY = y;
                break;
            }
        }

        // place the player two blocks above that surface
        double px = ix + 0.5;
        double py = surfaceY + 2.0;
        double pz = iz + 0.5;

        entity.setPositionAndAngles(px, py, pz, entity.yaw, entity.pitch);
        entity.velocityX = entity.velocityY = entity.velocityZ = 0;
        return true;
    }
}
