package net.unkleacid.voidcalls;

import net.unkleacid.voidcalls.entity.NotextureEntity;
import net.unkleacid.voidcalls.entity.renderer.NotextureEntityRenderer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;

@Entrypoint
public class Voidcalls {

    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(NotextureEntity.class, "Notexture");
    }

    @EventListener
    public void registerEntityRenderer(EntityRendererRegisterEvent event) {
        event.renderers.put(NotextureEntity.class, new NotextureEntityRenderer());
    }
}
