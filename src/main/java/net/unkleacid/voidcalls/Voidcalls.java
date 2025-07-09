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
import net.unkleacid.voidcalls.entity.NotextureEntity;
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
    }

    @EventListener
    public void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(NotextureEntity.class, new NotextureEntityRenderer());
    }

    @EventListener
    public void onTextureRegister(TextureRegisterEvent event) {
        ExpandableAtlas atlas = Atlases.getTerrain();
        atlas.addTexture(NAMESPACE.id("err_texture"));
    }
}
