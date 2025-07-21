package net.unkleacid.voidcalls.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.unkleacid.voidcalls.entity.NearsightedEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public final class VoidCallsClient {

    private static boolean helloPlayed = false;

    private static Field soundManagerField;
    private static Method playSoundMethod;

    public static void onClientGameTick() {
        Minecraft mc = Minecraft.INSTANCE;
        if (mc == null) return;

        World world = mc.world;
        Entity raw = mc.player;
        if (world == null) return;
        if (!(raw instanceof LivingEntity player)) return;

        NearsightedEntity target = findClosestNearsighted(world, player, 7.0);
        if (target != null) {
            if (!helloPlayed) {
                playHello(mc, target);
                helloPlayed = true;
            }
        } else {
            helloPlayed = false;
        }
    }

    private static void playHello(Minecraft mc, NearsightedEntity e) {
        if (soundManagerField == null) {
            for (Field f : mc.getClass().getDeclaredFields()) {
                if (f.getName().toLowerCase().contains("snd")) {
                    f.setAccessible(true);
                    soundManagerField = f;
                    break;
                }
            }
        }
        if (soundManagerField == null) return;

        try {
            Object sndMgr = soundManagerField.get(mc);
            if (playSoundMethod == null) {
                for (Method m : sndMgr.getClass().getDeclaredMethods()) {
                    Class<?>[] p = m.getParameterTypes();
                    if (p.length == 6
                            && p[0] == String.class
                            && p[1] == float.class) {
                        m.setAccessible(true);
                        playSoundMethod = m;
                        break;
                    }
                }
            }
            if (playSoundMethod != null) {
                playSoundMethod.invoke(
                        sndMgr,
                        "voidcalls.hello",
                        (float) e.x, (float) e.y, (float) e.z,
                        1.0F, 1.0F
                );
            }
        } catch (Throwable ignored) {
        }
    }

    private static NearsightedEntity findClosestNearsighted(World world, LivingEntity player, double maxDist) {
        double maxSq = maxDist * maxDist;
        NearsightedEntity closest = null;
        @SuppressWarnings("unchecked")
        List<Entity> list = (List<Entity>) world.entities;
        for (Entity ent : list) {
            if (ent instanceof NearsightedEntity ne) {
                double dx = ne.x - player.x,
                        dy = ne.y - player.y,
                        dz = ne.z - player.z;
                double dist = dx * dx + dy * dy + dz * dz;
                if (dist < maxSq) {
                    maxSq = dist;
                    closest = ne;
                }
            }
        }
        return closest;
    }
}
