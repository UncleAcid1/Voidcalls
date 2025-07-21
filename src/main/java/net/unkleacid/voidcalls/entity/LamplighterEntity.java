package net.unkleacid.voidcalls.entity;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.unkleacid.voidcalls.Voidcalls;

import java.util.Random;

public class LamplighterEntity extends AnimalEntity {

    private static final String TEXTURE_PATH = "/assets/voidcalls/stationapi/textures/entity/lamplighter.png";

    private int wanderCooldown, wanderTicks;
    private float wanderYaw;
    private int phase = 0;            // 0 = wandering, 1 = staring
    private int stareTicks = 0;
    private int placeBlockTimer = 0;

    public LamplighterEntity(World world) {
        super(world);
        this.texture = TEXTURE_PATH;
        this.maxHealth = 20;
        this.health = 20;
        this.stepHeight = 1.0F;
        this.setBoundingBoxSpacing(1.0F, 2.0F);
        this.wanderCooldown = 2000 + this.random.nextInt(4000);
    }

    public LamplighterEntity(World world, double x, double y, double z) {
        this(world);
        this.setPosition(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();

        // detect player in 7‑block radius
        PlayerEntity player = this.world.getClosestPlayer(this, 7.0);
        if (player != null) {
            if (phase == 0) {
                phase = 1;
                stareTicks = 0;
            }
        } else {
            phase = 0;
            stareTicks = 0;
        }

        // behavior
        if (phase == 0) {
            wanderBehavior();
        } else {
            stareBehavior(player);
        }

        // place a light block every 40 ticks
        if (!world.isRemote) {
            if (++placeBlockTimer >= 40) {
                placeBlockTimer = 0;
                placeErrLightBlockNearby();
            }
        }

        // gravity & movement
        if (!onGround) {
            this.velocityY -= 0.03;
        } else {
            this.velocityY = 0;
        }
        this.move(this.velocityX, this.velocityY, this.velocityZ);
    }

    private void wanderBehavior() {
        if (wanderTicks > 0) {
            double speed = 0.0025;
            float rad = (float) Math.toRadians(wanderYaw);
            this.velocityX = -MathHelper.sin(rad) * speed;
            this.velocityZ = MathHelper.cos(rad) * speed;
            this.yaw = this.bodyYaw = this.prevYaw = wanderYaw;
            wanderTicks--;
        } else {
            // pause, then pick a new random heading/time
            this.velocityX = this.velocityZ = 0;
            if (--wanderCooldown <= 0) {
                wanderYaw = this.random.nextFloat() * 360f;
                wanderTicks = 1 + this.random.nextInt(2);
                wanderCooldown = 2000 + this.random.nextInt(4000);
            }
        }
    }

    private void stareBehavior(PlayerEntity player) {
        stareTicks++;
        facePlayer(player);
        zeroMotion();
        // just stare as long as they remain nearby
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

    private void placeErrLightBlockNearby() {
        Random rnd = this.random;
        int r = 3;
        int sx = MathHelper.floor(this.x), sy = MathHelper.floor(this.y), sz = MathHelper.floor(this.z);
        for (int i = 0; i < 10; i++) {
            int xx = sx + rnd.nextInt(r * 2 + 1) - r;
            int yy = sy + rnd.nextInt(r * 2 + 1) - r;
            int zz = sz + rnd.nextInt(r * 2 + 1) - r;
            if (world.getBlockId(xx, yy, zz) == 0) {
                world.setBlock(xx, yy, zz, Voidcalls.ERR_LIGHT_BLOCK.id);
                return;
            }
        }
    }

    @Override
    public boolean damage(net.minecraft.entity.Entity src, int amt) {
        return false;
    }

    @Override
    public boolean canSpawn() {
        return true;
    }
}
