package net.unkleacid.voidcalls.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.unkleacid.voidcalls.Voidcalls;

import java.util.Random;

public class NotextureEntity extends AnimalEntity {

    private static final String TEXTURE_PATH = "/assets/voidcalls/stationapi/textures/entity/notexture.png";

    private int wanderCooldown, wanderTicks;
    private float wanderYaw;

    private int phase          = 0;  // 0=wander, 1=stare, 2=chase
    private int stareTicks     = 0;
    private int chaseTicks     = 0;

    private int teleportCooldown = 0;
    private boolean warningSoundPlaying = false;

    private int placeBlockTimer = 0;

    public NotextureEntity(World world) {
        super(world);
        this.texture      = TEXTURE_PATH;
        this.maxHealth    = 20;
        this.health       = 20;
        this.stepHeight   = 1.0F;  // let it naturally step up blocks
        this.wanderCooldown = 2000 + this.random.nextInt(4000);
    }
    @Override
    public void tick() {
        super.tick();
        PlayerEntity player = this.world.getClosestPlayer(this, 7.0);
        // phase transition
        if (player != null) {
            if (phase == 0) {
                phase = 1;
                stareTicks = 0;
            }
        } else {
            phase = 0;
            stareTicks = chaseTicks = 0;
            warningSoundPlaying = false;
        }
        if (phase == 0) {
            chaseTicks = 0;
            wanderBehavior();
        } else if (phase == 1) {
            stareTicks++;
            facePlayer(player);
            zeroMotion();
            if (stareTicks >= 50) {
                phase = 2;
                chaseTicks = 0;
            }
        } else {
            chaseTicks++;
            chaseBehavior(player);
        }
        if (!world.isRemote) {
            if (++placeBlockTimer >= 40) {
                placeBlockTimer = 0;
                int count = (phase == 2) ? 3 : 1;
                for (int i = 0; i < count; i++) placeErrTextureBlockNearby();
            }
        }

        if (!onGround) this.velocityY -= 0.03;
        else this.velocityY = 0;
        this.move(this.velocityX, this.velocityY, this.velocityZ);

        if (teleportCooldown > 0) teleportCooldown--;
    }

    private void wanderBehavior() {
        if (wanderTicks > 0) {
            double speed = 0.0025;
            float rad = (float) Math.toRadians(wanderYaw);
            this.velocityX = -MathHelper.sin(rad) * speed;
            this.velocityZ =  MathHelper.cos(rad) * speed;
            this.yaw = this.bodyYaw = this.prevYaw = wanderYaw;
            wanderTicks--;
        } else {
            this.velocityX = this.velocityZ = 0;
            if (--wanderCooldown <= 0) {
                wanderYaw = this.random.nextFloat() * 360f;
                wanderTicks = 1 + this.random.nextInt(2);
                wanderCooldown = 2000 + this.random.nextInt(4000);
            }
        }
    }

    private void chaseBehavior(PlayerEntity player) {
        double dx = player.x - this.x;
        double dz = player.z - this.z;
        float angle = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90);
        this.wanderYaw = angle;
        float rad = (float) Math.toRadians(wanderYaw);
        this.velocityX = -MathHelper.sin(rad) * 0.1;
        this.velocityZ =  MathHelper.cos(rad) * 0.1;
        this.yaw = this.bodyYaw = this.prevYaw = wanderYaw;
        wanderTicks = 1;

        if (chaseTicks < 100 && chaseTicks % 3 == 0) {
            this.world.playSound(this.x, this.y, this.z, "voidcalls:lightning", 0.7F, 0.6F);
        }

        double distSq = dx*dx + (player.y - this.y)*(player.y - this.y) + dz*dz;
        if (distSq < 0.25 && teleportCooldown <= 0) {
            Vec3i sp = player.getSpawnPos();
            if (sp != null) {
                player.setPosition(sp.x + 0.5, sp.y + 2, sp.z + 0.5);
                teleportCooldown = 40;
            }
        }

        if (chaseTicks == 82) {
            this.world.playSound(this.x, this.y, this.z, "voidcalls:hello", 1.0F, 1.0F);
        }

        if (chaseTicks >= 102) {
            this.damage(this, 9999);
        }
    }

    private void facePlayer(PlayerEntity player) {
        double dx = player.x - this.x;
        double dz = player.z - this.z;
        float angle = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90);
        this.yaw = this.bodyYaw = this.prevYaw = angle;
    }

    private void zeroMotion() {
        this.velocityX = this.velocityY = this.velocityZ = 0;
    }

    private void placeErrTextureBlockNearby() {
        Random rnd = this.random;
        int r = 3;
        int sx = MathHelper.floor(this.x), sy = MathHelper.floor(this.y), sz = MathHelper.floor(this.z);
        for (int i = 0; i < 10; i++) {
            int x = sx + rnd.nextInt(r*2+1) - r;
            int y = sy + rnd.nextInt(r*2+1) - r;
            int z = sz + rnd.nextInt(r*2+1) - r;
            if (world.getBlockId(x,y,z) == 0) {
                world.setBlock(x,y,z, Voidcalls.ERR_TEXTURE_BLOCK.id);
                return;
            }
        }
    }

    @Override
    public boolean damage(Entity src, int amt) {
        if (src == this) {
            this.dead = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean canSpawn() {
        return this.y > 64 && this.world.hasSkyLight(
                MathHelper.floor(this.x),
                MathHelper.floor(this.y),
                MathHelper.floor(this.z)
        ) && this.random.nextInt(100) == 0; // 1% chance
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("wanderCooldown", wanderCooldown);
        nbt.putInt("wanderTicks", wanderTicks);
        nbt.putFloat("wanderYaw", wanderYaw);
        nbt.putInt("phase", phase);
        nbt.putInt("stareTicks", stareTicks);
        nbt.putInt("chaseTicks", chaseTicks);
        nbt.putInt("teleportCooldown", teleportCooldown);
        nbt.putBoolean("warningSoundPlaying", warningSoundPlaying);
        nbt.putInt("placeBlockTimer", placeBlockTimer);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        wanderCooldown        = nbt.getInt("wanderCooldown");
        wanderTicks           = nbt.getInt("wanderTicks");
        wanderYaw             = nbt.getFloat("wanderYaw");
        phase                 = nbt.getInt("phase");
        stareTicks            = nbt.getInt("stareTicks");
        chaseTicks            = nbt.getInt("chaseTicks");
        teleportCooldown      = nbt.getInt("teleportCooldown");
        warningSoundPlaying   = nbt.getBoolean("warningSoundPlaying");
        placeBlockTimer       = nbt.getInt("placeBlockTimer");
    }

    @Override protected String getRandomSound() { return null; }
    @Override protected String getHurtSound()   { return null; }
    @Override protected String getDeathSound()  { return null; }
}
