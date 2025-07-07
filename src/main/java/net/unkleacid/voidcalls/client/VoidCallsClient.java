package net.unkleacid.voidcalls.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.event.tick.GameTickEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.unkleacid.voidcalls.entity.NotextureEntity;

import java.lang.reflect.Field;
import java.util.List;

public class VoidCallsClient {

    private boolean helloPlayed = false;
    private Field worldField, playerField;

    @EventListener
    public void onGameTick(GameTickEvent event) {
        Minecraft mc = getMinecraftInstance();
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

    private World getClientWorld(Minecraft mc) {
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

    private Entity getClientPlayer(Minecraft mc) {
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

    private NotextureEntity findClosestNotexture(World world, LivingEntity player, double maxDist) {
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

    private Minecraft getMinecraftInstance() {
        try {
            Field inst = Minecraft.class.getDeclaredField("minecraft");
            inst.setAccessible(true);
            return (Minecraft) inst.get(null);
        } catch (Exception e) {
            return null;
        }
    }
}