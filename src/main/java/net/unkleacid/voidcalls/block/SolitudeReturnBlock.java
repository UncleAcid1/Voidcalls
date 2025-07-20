package net.unkleacid.voidcalls.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.unkleacid.voidcalls.teleport.NoCreatePortalForcer;
import net.modificationstation.stationapi.api.block.CustomPortal;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import net.unkleacid.voidcalls.mixin.access.EntityAccessor;

public class SolitudeReturnBlock extends TemplateBlock implements CustomPortal {
    public static final int BOUNCING_META = 1;

    public SolitudeReturnBlock(Identifier id) {
        super(id, Material.ICE);
    }

    @Override
    public void onEntityCollision(World world, int x, int y, int z, Entity entity) {
        ((EntityAccessor) entity).setFallDistance(0F);

        if (!entity.isSneaking() && world.getBlockMeta(x, y, z) == BOUNCING_META) {
            entity.velocityY = 2.0;
        }
        if (entity.velocityY < 0) {
            entity.velocityY *= 0.005;
        }

        if (!world.isRemote && entity instanceof PlayerEntity player) {
            this.switchDimension(player);
            getTravelAgent(player).moveToPortal(world, player);
        }
    }

    /** Required by CustomPortal: which dimension to send them into */
    @Override
    public Identifier getDimension(PlayerEntity player) {
        return Identifier.of(Namespace.of("minecraft"), "overworld");
    }

    /** Provide our own PortalForcer that only overrides teleportToValidPortal(...) */
    @Override
    public NoCreatePortalForcer getTravelAgent(PlayerEntity player) {
        return new SpawnReturnForcer();
    }

    private static class SpawnReturnForcer extends NoCreatePortalForcer {
        /**
         * Finds a “portal” (we’re abusing it) and then teleports you
         * straight to your bed or world spawn.
         */
        @Override
        public boolean teleportToValidPortal(World world, Entity entity) {
            if (!(entity instanceof PlayerEntity player)) return false;

            // pick bed spawn if set
            Vec3i bed = player.getSpawnPos();
            BlockPos target;
            if (bed != null) {
                target = new BlockPos(bed.x, bed.y+3, bed.z);
            } else {
                // else world spawn
                Vec3i ws = world.getSpawnPos();
                target = new BlockPos(ws.x, ws.y, ws.z);
            }

            // if that spot is solid, lift to top solid block
            if (world.getBlockState(target).isOpaque()) {
                int safeY = world.getTopSolidBlockY(target.getX(), target.getZ());
                target = new BlockPos(target.getX(), safeY, target.getZ());
            }

            // center inside the block
            double px = target.getX() + 0.5;
            double py = target.getY();
            double pz = target.getZ() + 0.5;

            // teleport and zero velocity
            entity.setPositionAndAngles(px, py, pz, player.yaw, player.pitch);
            entity.velocityX = entity.velocityY = entity.velocityZ = 0;
            return true;
        }
    }

    // Rendering + misc overrides
    @Override public boolean isOpaque()                   { return false; }
    @Override public int getRenderLayer()                 { return 1; }
    @Override protected int getDroppedItemMeta(int meta) { return meta; }

    @Override
    public int getColor(int meta) {
        return switch (meta) {
            case 1 -> 0x389274;
            case 2 -> 0xFFFF00;
            default -> 0xFFFFFF;
        };
    }

    @Override
    public int getColorMultiplier(BlockView view, int x, int y, int z) {
        return getColor(view.getBlockMeta(x, y, z));
    }

    @Override
    public Box getCollisionShape(World world, int x, int y, int z) {
        // full XZ collider
        return Box.createCached(x, y, z, x + 1, y, z + 1);
    }

    /** Your three metas (bounce / normal / whatever) */
    public int[] getValidMetas() {
        return new int[]{0, 1, 2};
    }
}
