package net.unkleacid.voidcalls.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;

public class NotextureEntity extends AnimalEntity {

    private static final String TEXTURE_PATH = "/assets/voidcalls/stationapi/textures/entity/notexture.png";

    private int wanderCooldown;
    private int wanderTicks;
    private float wanderYaw;

    private boolean helloPlayed = false;

    public NotextureEntity(World world) {
        super(world);
        this.texture = TEXTURE_PATH;
        this.maxHealth = 20;
        this.health = 20;
        this.stepHeight = 0.5F;
        // Idle for 100–300 s at spawn
        this.wanderCooldown = 2000 + this.random.nextInt(4000);
    }

    @Override
    public void tick() {
        super.tick();

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

        this.move(this.velocityX, this.velocityY, this.velocityZ);

        PlayerEntity player = this.world.getClosestPlayer(this, 7.0);
        if (player != null) {
            if (!helloPlayed) {
                this.world.playSound(this.x, this.y, this.z, "voidcalls.hello", 1.0F, 1.0F);
                helloPlayed = true;
            }
        } else {
            helloPlayed = false;
        }
    }

    @Override
    public boolean damage(Entity attacker, int amount) {
        return false; // completely ignore all damage
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
