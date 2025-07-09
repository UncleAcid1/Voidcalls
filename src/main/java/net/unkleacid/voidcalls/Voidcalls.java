package net.unkleacid.voidcalls;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.unkleacid.voidcalls.block.ErrTextureBlock;
import net.unkleacid.voidcalls.entity.AngelEntity;
import net.unkleacid.voidcalls.entity.NotextureEntity;
import net.unkleacid.voidcalls.entity.renderer.AngelEntityRenderer;
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
        // Register both custom entities
        event.register(NotextureEntity.class, "notexture");
        event.register(AngelEntity.class,    "angel");
    }

    @EventListener
    public void registerEntityRenderers(EntityRendererRegisterEvent event) {
        // Bind renderers for both entities
        event.renderers.put(NotextureEntity.class, new NotextureEntityRenderer());
        event.renderers.put(AngelEntity.class,    new AngelEntityRenderer());
    }

    @EventListener
    public void onTextureRegister(TextureRegisterEvent event) {
        ExpandableAtlas atlas = Atlases.getTerrain();
        // Ensure block and entity textures are loaded
        atlas.addTexture(NAMESPACE.id("err_texture"));
        atlas.addTexture(NAMESPACE.id("angel"));
        atlas.addTexture(NAMESPACE.id("notexture"));
    }
}
