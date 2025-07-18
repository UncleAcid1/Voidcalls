package net.unkleacid.voidcalls.mixin;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.EntitySpawnGroup;
import net.unkleacid.voidcalls.entity.AngelEntity;
import net.unkleacid.voidcalls.entity.NotextureEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Biome.class)
public class BiomeMixin {

    @Shadow
    protected List spawnablePassive;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void addVoidcallsSpawns(CallbackInfo ci) {
        this.spawnablePassive.add(new EntitySpawnGroup(NotextureEntity.class, 1));
        this.spawnablePassive.add(new EntitySpawnGroup(AngelEntity.class, 1));
    }
}
