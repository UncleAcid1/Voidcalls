package net.unkleacid.voidcalls.entity;

import net.danygames2014.nyalib.sound.SoundHelper;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.MessagePacket;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;
import net.unkleacid.voidcalls.Voidcalls;

@HasTrackingParameters(updatePeriod = 4, sendVelocity = TriState.TRUE, trackingDistance = 30)
public class AngelEntity extends AnimalEntity implements MobSpawnDataProvider {

    private static final String TEXTURE_PATH = "/assets/voidcalls/stationapi/textures/entity/angel/angel.png";

    private int wanderCooldown, wanderTicks;
    private float wanderYaw;
    private int phase = 0;
    private int stareTicks = 0;
    private int chaseTicks = 0;
    private int lifeTicks = 0;
    private static final int MAX_LIFE_TICKS = 1300;
    private static final int MAX_CHASE_TICKS = 300;

    public AngelEntity(World world) {
        super(world);
        this.texture = TEXTURE_PATH;
        this.maxHealth = 30;
        this.health = 10;
        this.stepHeight = 3.0F;
        this.setBoundingBoxSpacing(1.0F, 3.0F);
        this.wanderCooldown = 2000 + this.random.nextInt(4000);
        this.fireImmune = true;
    }

    public AngelEntity(World world, Double x, Double y, Double z) {
        this(world);
        this.setPosition(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();

        PlayerEntity closest = this.world.getClosestPlayer(this, 13.0);
        if (closest != null) {
            if (phase == 0) {
                phase = 1;
                stareTicks = 0;
            }
        } else {
            phase = 0;
            stareTicks = chaseTicks = 0;
        }

        if (phase == 0) {
            chaseTicks = 0;
            wanderBehavior();
        } else if (phase == 1) {
            stareBehavior(closest);
        } else {
            chaseBehavior(closest);
        }

        if (!this.onGround) {
            this.velocityY -= 0.03;
        } else {
            this.velocityY = 0;
        }
        this.move(this.velocityX, this.velocityY, this.velocityZ);

        if (!world.isRemote) {
            for (Object obj : this.world.players) {
                if (obj instanceof PlayerEntity player) {
                    if (player.boundingBox.intersects(this.boundingBox)) {
                        player.damage(this, 69);
                        SoundHelper.playSound(world, MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z), "voidcalls:chaseend", 1.0F, 1.0F);
                        this.damage(this, 420);
                    }
                }
            }
        }
        if (!world.isRemote) {
            lifeTicks++;
            if (lifeTicks >= MAX_LIFE_TICKS || (phase == 2 && chaseTicks >= MAX_CHASE_TICKS)) {
                this.markDead();
            }
        }
    }

    private void stareBehavior(PlayerEntity player) {
        stareTicks++;
        facePlayer(player);
        zeroMotion();
        if (stareTicks >= 100) {
            phase = 2;
            chaseTicks = 0;
        }
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
            this.velocityX = this.velocityZ = 0;
            if (--wanderCooldown <= 0) {
                wanderYaw = this.random.nextFloat() * 360f;
                wanderTicks = 1 + this.random.nextInt(2);
                wanderCooldown = 2000 + this.random.nextInt(4000);
            }
        }
    }

    private void chaseBehavior(PlayerEntity player) {
        chaseTicks++;
        double dx = player.x - this.x;
        double dz = player.z - this.z;
        float angle = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90);
        wanderYaw = angle;
        float rad = (float) Math.toRadians(wanderYaw);
        this.velocityX = -MathHelper.sin(rad) * 0.3;
        this.velocityZ = MathHelper.cos(rad) * 0.3;
        this.yaw = this.bodyYaw = this.prevYaw = wanderYaw;

        if (chaseTicks < MAX_CHASE_TICKS && chaseTicks % 3 == 0) {
            SoundHelper.playSound(world, MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z), "voidcalls:lightning", 0.9F, 0.4F);
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
    public boolean damage(net.minecraft.entity.Entity source, int amount) {
        return super.damage(source, amount);
    }

    @Override
    public boolean canSpawn() {
        int lightLevel = world.getLightLevel(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
        return this.y > 64
                && lightLevel < 8
                && this.random.nextInt(5) == 0;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }

    @Override
    protected String getRandomSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return null;
    }

    @Override
    protected String getDeathSound() {
        return null;
    }

    @Override
    public void writeToMessage(MessagePacket message) {
    }

    @Override
    public void readFromMessage(MessagePacket message) {
    }

    @Override
    public Identifier getHandlerIdentifier() {
        return Voidcalls.NAMESPACE.id("angel");
    }

    @Override
    public Packet getSpawnData() {
        return new EntitySpawnS2CPacket(this, this.id);
    }
}
