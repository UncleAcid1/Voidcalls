package net.unkleacid.voidcalls.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class NotextureEntity extends AnimalEntity {

    private static final String TEXTURE_PATH = "/assets/voidcalls/stationapi/textures/entity/notexture.png";

    // Wander AI variables
    private int wanderCooldown;
    private int wanderTicks;
    private float wanderYaw;

    public NotextureEntity(World world) {
        super(world);
        this.texture = TEXTURE_PATH;
        this.maxHealth = 20;
        this.health = 20;
        this.stepHeight = 0.5F;
        this.wanderCooldown = 2000 + this.random.nextInt(4000);
    }

    @Override
    public void tick() {
        super.tick();

        // Wander behavior
        if (wanderTicks > 0) {
            double speed = 0.0025;
            float rad = (float) Math.toRadians(wanderYaw);
            this.velocityX = -MathHelper.sin(rad) * speed;
            this.velocityZ =  MathHelper.cos(rad) * speed;
            this.yaw = this.bodyYaw = this.prevYaw = wanderYaw;
            wanderTicks--;
        } else {
            this.velocityX = 0;
            this.velocityZ = 0;
            if (--wanderCooldown <= 0) {
                wanderYaw = this.random.nextFloat() * 360f;
                wanderTicks = 1 + this.random.nextInt(2);
                wanderCooldown = 2000 + this.random.nextInt(4000);
            }
        }

        // Gravity
        if (!this.onGround) {
            this.velocityY -= 0.03;
        } else {
            this.velocityY = 0;
        }

        // Move entity
        this.move(this.velocityX, this.velocityY, this.velocityZ);
    }

    // Ignore all damage
    @Override
    public boolean damage(Entity attacker, int amount) {
        return false;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    @Override protected String getRandomSound() { return null; }
    @Override protected String getHurtSound()   { return null; }
    @Override protected String getDeathSound()  { return null; }
}
