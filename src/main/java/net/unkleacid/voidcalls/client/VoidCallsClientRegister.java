package net.unkleacid.voidcalls.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.unkleacid.voidcalls.entity.AngelEntity;
import net.unkleacid.voidcalls.entity.SubjugateEntity;
import net.unkleacid.voidcalls.entity.NearsightedEntity;
import net.unkleacid.voidcalls.entity.LamplighterEntity;
import net.unkleacid.voidcalls.entity.renderer.AngelEntityRenderer;
import net.unkleacid.voidcalls.entity.renderer.SubjugateEntityRenderer;
import net.unkleacid.voidcalls.entity.renderer.NearsightedEntityRenderer;
import net.unkleacid.voidcalls.entity.renderer.LamplighterEntityRenderer;

@Entrypoint
@Environment(EnvType.CLIENT)
public class VoidCallsClientRegister {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @EventListener
    public void onTextureRegister(TextureRegisterEvent event) {
        ExpandableAtlas atlas = Atlases.getTerrain();
        // existing entity textures
        atlas.addTexture(NAMESPACE.id("angel"));
        atlas.addTexture(NAMESPACE.id("subjugate"));
        atlas.addTexture(NAMESPACE.id("nearsighted"));
        atlas.addTexture(NAMESPACE.id("lamplighter"));
        // moon phase textures (0–7)
        for (int i = 0; i < 8; i++) {
            atlas.addTexture(NAMESPACE.id("moon_phase_" + i));
        }
    }

    @EventListener
    public void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(AngelEntity.class, new AngelEntityRenderer());
        event.renderers.put(SubjugateEntity.class, new SubjugateEntityRenderer());
        event.renderers.put(NearsightedEntity.class, new NearsightedEntityRenderer());
        event.renderers.put(LamplighterEntity.class, new LamplighterEntityRenderer());
    }
}
