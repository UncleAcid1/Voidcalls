package net.unkleacid.voidcalls.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Unique
    private static final String[] MOON_PHASE_TEXTURES = {
            "/assets/voidcalls/textures/moon/moon_phase_0.png",
            "/assets/voidcalls/textures/moon/moon_phase_1.png",
            "/assets/voidcalls/textures/moon/moon_phase_2.png",
            "/assets/voidcalls/textures/moon/moon_phase_3.png",
            "/assets/voidcalls/textures/moon/moon_phase_4.png",
            "/assets/voidcalls/textures/moon/moon_phase_5.png",
            "/assets/voidcalls/textures/moon/moon_phase_6.png",
            "/assets/voidcalls/textures/moon/moon_phase_7.png"
    };

    @Shadow private TextureManager textureManager;
    @Shadow private World world;

    @WrapOperation(
            method = "renderSky",
            at = @At(
                    value    = "INVOKE",
                    target   = "Lorg/lwjgl/opengl/GL11;glBindTexture(II)V",
                    remap    = false,
                    ordinal  = 1  // second bind is the moon
            )
    )
    private void swapMoonTexture(int target, int texture, Operation<Void> original) {
        long worldTime = world.getTime();          // total ticks
        long days      = worldTime / 24000L;       // full day count
        int phase      = (int)(days % 8);          // cycle 0–7

        texture = textureManager.getTextureId(MOON_PHASE_TEXTURES[phase]);
        original.call(target, texture);
    }
}

