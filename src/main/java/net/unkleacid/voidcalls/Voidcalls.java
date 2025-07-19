package net.unkleacid.voidcalls;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Namespace;
import net.unkleacid.voidcalls.block.ErrTextureBlock;
import net.unkleacid.voidcalls.block.GlowingObsidianBlock;
import net.unkleacid.voidcalls.block.AdminSpaceBlock;
import net.unkleacid.voidcalls.block.AdminSpaceLightBlock;
import net.unkleacid.voidcalls.entity.AngelEntity;
import net.unkleacid.voidcalls.entity.NotextureEntity;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;

@Entrypoint
public class Voidcalls {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;
    public static Block ERR_TEXTURE_BLOCK;
    public static Block GLOWING_OBSIDIAN_BLOCK;
    public static Block ADMINSPACE_BLOCK;
    public static Block ADMINSPACE_LIGHT_BLOCK;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        ERR_TEXTURE_BLOCK = new ErrTextureBlock(NAMESPACE.id("err_texture"))
                .setTranslationKey(NAMESPACE, "err_texture");
        GLOWING_OBSIDIAN_BLOCK = new GlowingObsidianBlock(NAMESPACE.id("0-"))
                .setTranslationKey(NAMESPACE, "0-");
        ADMINSPACE_BLOCK = new AdminSpaceBlock(NAMESPACE.id("contained.adminspace"))
                .setTranslationKey(NAMESPACE, "contained.adminspace");
        ADMINSPACE_LIGHT_BLOCK = new AdminSpaceLightBlock(NAMESPACE.id("contained.adminspace.light"))
                .setTranslationKey(NAMESPACE, "contained.adminspace.light");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(NotextureEntity.class, "notexture");
        event.register(AngelEntity.class,    "angel");
    }

    @EventListener
    public void registerMobHandlers(MobHandlerRegistryEvent event) {
        Registry.register(event.registry, NAMESPACE.id("angel"), AngelEntity::new);
        Registry.register(event.registry, NAMESPACE.id("notexture"), NotextureEntity::new);
    }
}
