package net.unkleacid.voidcalls.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.unkleacid.voidcalls.entity.NotextureEntity;

import java.lang.reflect.Field;
import java.util.List;

public class VoidCallsClient {
    private static boolean helloPlayed = false;
    private static Field worldField, playerField;

    public static void onClientGameTick() {
        Minecraft mc = (Minecraft) FabricLoader.getInstance().getGameInstance();
        if (mc == null) return;

        World world = getClientWorld(mc);
        Entity rawPlayer = getClientPlayer(mc);
        if (!(rawPlayer instanceof LivingEntity) || world == null) return;
        LivingEntity player = (LivingEntity) rawPlayer;

        NotextureEntity closest = findClosestNotexture(world, player, 7.0);

        if (closest != null) {
            mc.camera = closest;

            if (!helloPlayed) {
                mc.soundManager.playSound(
                        "voidcalls.hello",
                        (float) closest.x, (float) closest.y, (float) closest.z,
                        1.0F, 1.0F
                );
                helloPlayed = true;
            }
        } else {
            mc.camera = player;
            helloPlayed = false;
        }
    }

    private static World getClientWorld(Minecraft mc) {
        if (worldField == null) {
            for (Field f : mc.getClass().getDeclaredFields()) {
                if (f.getType() == World.class) {
                    f.setAccessible(true);
                    worldField = f;
                    break;
                }
            }
        }
        try {
            return worldField != null ? (World) worldField.get(mc) : null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private static Entity getClientPlayer(Minecraft mc) {
        if (playerField == null) {
            for (Field f : mc.getClass().getDeclaredFields()) {
                if (LivingEntity.class.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    playerField = f;
                    break;
                }
            }
        }
        try {
            return playerField != null ? (Entity) playerField.get(mc) : null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private static NotextureEntity findClosestNotexture(World world, LivingEntity player, double maxDist) {
        double maxDistSq = maxDist * maxDist;
        NotextureEntity closest = null;
        @SuppressWarnings("unchecked")
        List<Entity> entities = (List<Entity>) world.entities;

        for (Entity e : entities) {
            if (e instanceof NotextureEntity) {
                double dx = e.x - player.x;
                double dy = e.y - player.y;
                double dz = e.z - player.z;
                double distSq = dx * dx + dy * dy + dz * dz;
                if (distSq <= maxDistSq) {
                    closest = (NotextureEntity) e;
                    maxDistSq = distSq;
                }
            }
        }
        return closest;
    }
}
