package net.unkleacid.voidcalls.client;

<<<<<<< HEAD
=======
import net.fabricmc.loader.api.FabricLoader;
>>>>>>> 5f5fff2 (broken stuff always breaking /:)
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.unkleacid.voidcalls.entity.NotextureEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public final class VoidCallsClient {

    private static boolean helloPlayed = false;

<<<<<<< HEAD
    private static Field soundManagerField;
    private static Method playSoundMethod;

    public static void onClientGameTick() {
        Minecraft mc = Minecraft.INSTANCE;
        if (mc == null) return;

        World world = mc.world;
        Entity raw = mc.player;
        if (world == null) return;
        if (!(raw instanceof LivingEntity player)) return;

        NotextureEntity target = findClosestNotexture(world, player, 7.0);
        if (target != null) {
            if (!helloPlayed) {
                playHello(mc, target);
                helloPlayed = true;
            }
        } else {
=======
    private static Field worldField, playerField, cameraField, soundManagerField;
    private static Method playSoundMethod;

    public static void onClientGameTick() {
        Object game = FabricLoader.getInstance().getGameInstance();
        if (!(game instanceof Minecraft mc)) return;

        World worldObj = (World) getFirstFieldOfType(mc, World.class);
        Entity rawPlayer = (Entity) getFirstFieldOfType(mc, LivingEntity.class);
        if (!(rawPlayer instanceof LivingEntity player) || worldObj == null) return;

        NotextureEntity closest = findClosest(worldObj, player, 7.0);

        if (closest != null) {
            setCamera(mc, closest);
            if (!helloPlayed) {
                playHello(mc, closest);
                helloPlayed = true;
            }
        } else {
            setCamera(mc, player);
>>>>>>> 5f5fff2 (broken stuff always breaking /:)
            helloPlayed = false;
        }
    }

<<<<<<< HEAD
=======
    private static Object getFirstFieldOfType(Object instance, Class<?> type) {
        for (Field f : instance.getClass().getDeclaredFields()) {
            if (type.isAssignableFrom(f.getType())) {
                f.setAccessible(true);
                try { return f.get(instance); } catch (IllegalAccessException ignored) {}
            }
        }
        return null;
    }

    private static void setCamera(Minecraft mc, LivingEntity target) {
        if (cameraField == null) {
            for (Field f : mc.getClass().getDeclaredFields()) {
                if (LivingEntity.class.isAssignableFrom(f.getType())
                        && !f.getName().equalsIgnoreCase("thePlayer")) {
                    f.setAccessible(true);
                    cameraField = f;
                    break;
                }
            }
        }
        if (cameraField != null) {
            try { cameraField.set(mc, target); }
            catch (IllegalAccessException ignored) {}
        }
    }

>>>>>>> 5f5fff2 (broken stuff always breaking /:)
    private static void playHello(Minecraft mc, NotextureEntity e) {
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
<<<<<<< HEAD
                    if (p.length == 6
                            && p[0] == String.class
                            && p[1] == float.class) {
=======
                    if (p.length == 6 && p[0] == String.class && p[1] == float.class) {
>>>>>>> 5f5fff2 (broken stuff always breaking /:)
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
<<<<<<< HEAD
                        (float) e.x, (float) e.y, (float) e.z,
                        1.0F, 1.0F
                );
            }
        } catch (Throwable ignored) {
        }
    }

    private static NotextureEntity findClosestNotexture(World world, LivingEntity player, double maxDist) {
        double maxSq = maxDist * maxDist;
        NotextureEntity closest = null;
        @SuppressWarnings("unchecked")
        List<Entity> list = (List<Entity>) world.entities;
        for (Entity ent : list) {
            if (ent instanceof NotextureEntity ne) {
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
=======
                        (float)e.x, (float)e.y, (float)e.z,
                        1.0F, 1.0F
                );
            }
        } catch (Throwable ignored) {}
    }

    private static NotextureEntity findClosest(World worldObj, LivingEntity player, double maxDist) {
        double maxSq = maxDist * maxDist;
        NotextureEntity best = null;
        @SuppressWarnings("unchecked")
        List<Entity> list = (List<Entity>) worldObj.entities;
        for (Entity ent : list) {
            if (ent instanceof NotextureEntity ne) {
                double dx = ent.x - player.x;
                double dy = ent.y - player.y;
                double dz = ent.z - player.z;
                double distSq = dx*dx + dy*dy + dz*dz;
                if (distSq < maxSq) { maxSq = distSq; best = ne; }
            }
        }
        return best;
>>>>>>> 5f5fff2 (broken stuff always breaking /:)
    }
}
