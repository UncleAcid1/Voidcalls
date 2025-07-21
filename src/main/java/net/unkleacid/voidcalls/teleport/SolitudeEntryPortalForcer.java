package net.unkleacid.voidcalls.teleport;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.dimension.PortalForcer;

public class SolitudeEntryPortalForcer extends PortalForcer {

    @Override
    public boolean createPortal(World world, Entity entity) {
        return false;
    }

    @Override
    public boolean teleportToValidPortal(World world, Entity entity) {
        if (!(entity instanceof PlayerEntity)) {
            return false;
        }

        int ix = MathHelper.floor(entity.x);
        int iz = MathHelper.floor(entity.z);

        int safeY = world.getTopSolidBlockY(ix, iz);

        BlockPos dest = new BlockPos(ix, safeY, iz);

        double px = dest.getX() + 0.5;
        double py = dest.getY() + 2.0;
        double pz = dest.getZ() + 0.5;

        entity.setPositionAndAngles(px, py, pz, entity.yaw, entity.pitch);
        entity.velocityX = entity.velocityY = entity.velocityZ = 0;
        return true;
    }
}
