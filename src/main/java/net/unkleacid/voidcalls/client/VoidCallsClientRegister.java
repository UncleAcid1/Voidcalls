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
import net.unkleacid.voidcalls.entity.NearsightedEntity;
import net.unkleacid.voidcalls.entity.renderer.AngelEntityRenderer;
import net.unkleacid.voidcalls.entity.renderer.NearsightedEntityRenderer;

@Entrypoint
@Environment(EnvType.CLIENT)
public class VoidCallsClientRegister {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @EventListener
    public void onTextureRegister(TextureRegisterEvent event) {
        ExpandableAtlas atlas = Atlases.getTerrain();
        atlas.addTexture(NAMESPACE.id("angel"));
        atlas.addTexture(NAMESPACE.id("nearsighted"));
    }

    @EventListener
    public void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(AngelEntity.class,     new AngelEntityRenderer());
        event.renderers.put(NearsightedEntity.class, new NearsightedEntityRenderer());
    }
}
