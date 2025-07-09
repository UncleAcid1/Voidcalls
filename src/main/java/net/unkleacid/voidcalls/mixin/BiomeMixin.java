package net.unkleacid.voidcalls.mixin;

import net.unkleacid.voidcalls.entity.NotextureEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.EntitySpawnGroup;
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
    public void addNotextureSpawn(CallbackInfo ci) {
        // Weight = 1 (rare), Min group = 1, Max group = 1
        this.spawnablePassive.add(new EntitySpawnGroup(NotextureEntity.class, 1));
    }
}
