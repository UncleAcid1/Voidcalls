package net.unkleacid.voidcalls;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.event.tick.GameTickEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import net.minecraft.item.Item;
import net.unkleacid.voidcalls.client.VoidCallsClient;
import net.unkleacid.voidcalls.entity.NotextureEntity;
import net.unkleacid.voidcalls.entity.renderer.NotextureEntityRenderer;

public class Voidcalls {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE = Null.get();

    public static Item WEDDING_RING;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        WEDDING_RING = new TemplateItem(NAMESPACE.id("wedding_ring"))
                .setMaxCount(1)
                .setTranslationKey(NAMESPACE, "wedding_ring");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(NotextureEntity.class, "Notexture");
    }

    @EventListener
    public void registerEntityRenderer(EntityRendererRegisterEvent event) {
        event.renderers.put(NotextureEntity.class, new NotextureEntityRenderer());
    }

    @EventListener
    public void onGameTick(GameTickEvent event) {
        if (EnvType.CLIENT == FabricLoader.getInstance().getEnvironmentType()) {
            VoidCallsClient.onClientGameTick(event);
        }
    }
}
