package net.unkleacid.voidcalls.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.World;
import net.unkleacid.voidcalls.client.VoidCallsClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class WorldMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    public void voidCalls_tick(CallbackInfo ci) {
        if (EnvType.CLIENT == FabricLoader.getInstance().getEnvironmentType()) {
            VoidCallsClient.onClientGameTick();
        }
    }
}
