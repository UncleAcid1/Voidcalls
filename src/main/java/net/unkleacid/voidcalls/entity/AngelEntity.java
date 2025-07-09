package net.unkleacid.voidcalls.entity;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class AngelEntity extends AnimalEntity {

    private static final String TEXTURE_PATH = "/assets/voidcalls/stationapi/textures/entity/angel/angel.png";

    public AngelEntity(World world) {
        super(world);
        this.texture = TEXTURE_PATH;
        this.maxHealth = 10;
        this.health = 10;
        this.stepHeight = 1.0F;
    }

    @Override
    public void tick() {
        super.tick();
        this.velocityX = this.velocityY = this.velocityZ = 0;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
    }

    @Override
    public boolean damage(net.minecraft.entity.Entity source, int amount) {
        if (source == this) {
            this.dead = true;
            return true;
        }
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
