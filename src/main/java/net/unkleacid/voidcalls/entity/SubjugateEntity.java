package net.unkleacid.voidcalls.entity;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;

public class SubjugateEntity extends AnimalEntity {
    private static final String TEXTURE_PATH = "/assets/voidcalls/stationapi/textures/entity/subjugate.png";
    private int wanderCooldown;
    private int wanderTicks;
    private float wanderYaw;

    public SubjugateEntity(World world) {
        super(world);
        this.texture = TEXTURE_PATH;
        this.maxHealth = 10;
        this.health = 10;
        this.stepHeight = 1.0F;
        this.setBoundingBoxSpacing(0.6F, 1.8F);
        this.wanderCooldown = 200 + this.random.nextInt(400);
    }

    public SubjugateEntity(World world, double x, double y, double z) {
        this(world);
        this.setPosition(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();
        PlayerEntity player = this.world.getClosestPlayer(this, 5.0);
        if (player == null) {
            wanderBehavior();
        } else {
            facePlayer(player);
            zeroMotion();
        }

        if (!onGround) {
            this.velocityY -= 0.04;
        } else {
            this.velocityY = 0;
        }
        this.move(this.velocityX, this.velocityY, this.velocityZ);
    }

    private void wanderBehavior() {
        if (wanderTicks > 0) {
            float rad = (float) Math.toRadians(wanderYaw);
            double speed = 0.02;
            this.velocityX = -MathHelper.sin(rad) * speed;
            this.velocityZ = MathHelper.cos(rad) * speed;
            this.yaw = this.bodyYaw = this.prevYaw = wanderYaw;
            wanderTicks--;
        } else {
            this.velocityX = this.velocityZ = 0;
            if (--wanderCooldown <= 0) {
                wanderYaw = this.random.nextFloat() * 360F;
                wanderTicks = 20 + this.random.nextInt(20);
                wanderCooldown = 200 + this.random.nextInt(400);
            }
        }
    }

    private void facePlayer(PlayerEntity player) {
        double dx = player.x - this.x;
        double dz = player.z - this.z;
        float angle = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90);
        this.yaw = this.bodyYaw = this.prevYaw = angle;
    }

    private void zeroMotion() {
        this.velocityX = this.velocityY = this.velocityZ = 0;
    }

    @Override
    public boolean canSpawn() {
        return true;
    }
}
