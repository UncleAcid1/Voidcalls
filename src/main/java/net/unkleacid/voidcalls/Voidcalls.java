package net.unkleacid.voidcalls;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.unkleacid.voidcalls.block.ErrTextureBlock;
import net.unkleacid.voidcalls.entity.AngelEntity;
import net.unkleacid.voidcalls.entity.NotextureEntity;
import net.unkleacid.voidcalls.entity.renderer.AngelEntityRenderer;
import net.modificationstation.stationapi.api.event.registry.EntityHandlerRegistryEvent;
import net.modificationstation.stationapi.api.client.registry.EntityHandlerRegistry;
import net.unkleacid.voidcalls.entity.renderer.NotextureEntityRenderer;

@Entrypoint
public class Voidcalls {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Block ERR_TEXTURE_BLOCK;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        ERR_TEXTURE_BLOCK = new ErrTextureBlock(NAMESPACE.id("err_texture"))
                .setTranslationKey(NAMESPACE, "err_texture");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(NotextureEntity.class, "notexture");
        event.register(AngelEntity.class,    "angel");
    }

    @EventListener
    public void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(NotextureEntity.class, new NotextureEntityRenderer());
        event.renderers.put(AngelEntity.class, new AngelEntityRenderer());
    }

    public void registerEntityHandlers(EntityHandlerRegistryEvent event) {
        event.register(NAMESPACE.id("angel"), (world, x, y, z) -> new AngelEntity(world));
        event.register(NAMESPACE.id("notexture"), (world, x, y, z) -> new NotextureEntity(world));
    }
}
