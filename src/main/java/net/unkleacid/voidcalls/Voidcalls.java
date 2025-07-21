package net.unkleacid.voidcalls;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.DimensionRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.DimensionContainer;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;
import net.unkleacid.voidcalls.block.*;
import net.unkleacid.voidcalls.block.template.SlabBlockTemplate;
import net.unkleacid.voidcalls.block.template.StairsBlockTemplate;
import net.modificationstation.stationapi.api.util.Namespace;
import net.unkleacid.voidcalls.dimension.AdminspaceDimension;
import net.unkleacid.voidcalls.entity.AngelEntity;
import net.unkleacid.voidcalls.entity.NearsightedEntity;
import net.unkleacid.voidcalls.dimension.SolitudeDimension;
import net.unkleacid.voidcalls.block.AdminspaceEntryBlock;
import net.unkleacid.voidcalls.block.SolitudeReturnBlock;


@Entrypoint
public class Voidcalls {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Block SOLITUDEPORTAL;
    public static Block SOLITUDERETURNPORTAL;
    public static Block ADMINSPACEPORTAL;
    public static Block ERR_TEXTURE_BLOCK;
    public static Block GLOWING_OBSIDIAN_BLOCK;
    public static Block GLOWING_OBSIDIAN_SLAB;
    public static Block GLOWING_OBSIDIAN_STAIRS;
    public static Block ADMINSPACE_BLOCK;
    public static Block ADMINSPACE_SLAB;
    public static Block ADMINSPACE_STAIRS;
    public static Block ADMINSPACE_LIGHT_BLOCK;
    public static Block ADMINSPACE_LIGHT_SLAB;
    public static Block ADMINSPACE_LIGHT_STAIRS;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        SOLITUDEPORTAL = new SolitudeBlock(NAMESPACE.id("1010101010"))
                .setTranslationKey(NAMESPACE, "1010101010");
        SOLITUDERETURNPORTAL = new SolitudeReturnBlock(NAMESPACE.id("LEAVE"))
                .setTranslationKey(NAMESPACE, "LEAVE");
        ADMINSPACEPORTAL = new AdminspaceEntryBlock(NAMESPACE.id("null"))
                .setTranslationKey(NAMESPACE, "null");
        //ERR_TEXTURE_BLOCK CODE :3
        ERR_TEXTURE_BLOCK = new ErrTextureBlock(NAMESPACE.id("err_texture"))
                .setTranslationKey(NAMESPACE, "err_texture");

        //GLOWING OBSIDIAN BLOCKS (RED HOT N SPICY >:3)
        GLOWING_OBSIDIAN_BLOCK = new GlowingObsidianBlock(NAMESPACE.id("0-"))
                .setTranslationKey(NAMESPACE, "0-");
        GLOWING_OBSIDIAN_SLAB = new SlabBlockTemplate(
                NAMESPACE.id("0-_slab"),
                GLOWING_OBSIDIAN_BLOCK
        )
                .setTranslationKey(NAMESPACE, "0-_slab")
                .setHardness(4.0F)
                .setLuminance(0.2F);
        GLOWING_OBSIDIAN_STAIRS = new StairsBlockTemplate(
                NAMESPACE.id("0-_stairs"),
                GLOWING_OBSIDIAN_BLOCK
        )
                .setTranslationKey(NAMESPACE, "0-_stairs")
                .setHardness(4.0F)
                .setLuminance(0.2F);

        //GOOFYGRAY ADMINSPACE BLOCK SET
        ADMINSPACE_BLOCK = new AdminSpaceBlock(NAMESPACE.id("contained.adminspace"))
                .setTranslationKey(NAMESPACE, "contained.adminspace");
        ADMINSPACE_SLAB = new SlabBlockTemplate(
                NAMESPACE.id("contained.adminspace_slab"),
                ADMINSPACE_BLOCK
        )
                .setTranslationKey(NAMESPACE, "contained.adminspace_slab")
                .setHardness(80.0F)
                .setLuminance(0.0F);
        ADMINSPACE_STAIRS = new StairsBlockTemplate(
                NAMESPACE.id("contained.adminspace_stairs"),
                ADMINSPACE_BLOCK
        )
                .setTranslationKey(NAMESPACE, "contained.adminspace_stairs")
                .setHardness(80.0F)
                .setLuminance(0.0F);

        //ADMINSPACE BLOCKS BUT THEY GLOW :O
        ADMINSPACE_LIGHT_BLOCK = new AdminSpaceLightBlock(NAMESPACE.id("contained.adminspace.light"))
                .setTranslationKey(NAMESPACE, "contained.adminspace.light");
        ADMINSPACE_LIGHT_SLAB = new SlabBlockTemplate(
                NAMESPACE.id("contained.adminspace.light_slab"),
                ADMINSPACE_LIGHT_BLOCK
        )
                .setTranslationKey(NAMESPACE, "contained.adminspace.light_slab")
                .setHardness(80.0F)
                .setLuminance(1.0F);
        ADMINSPACE_LIGHT_STAIRS = new StairsBlockTemplate(
                NAMESPACE.id("contained.adminspace.light_stairs"),
                ADMINSPACE_LIGHT_BLOCK
        )
                .setTranslationKey(NAMESPACE, "contained.adminspace.light_stairs")
                .setHardness(80.0F)
                .setLuminance(1.0F);
    }

    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(NearsightedEntity.class, "nearsighted");
        event.register(AngelEntity.class,    "angel");
    }

    @EventListener
    public void registerMobHandlers(MobHandlerRegistryEvent event) {
        Registry.register(event.registry, NAMESPACE.id("angel"),    AngelEntity::new);
        Registry.register(event.registry, NAMESPACE.id("nearsighted"), NearsightedEntity::new);
    }

    @EventListener
    public void registerDimensions(DimensionRegistryEvent event){
        event.registry.register(
                Identifier.of(NAMESPACE, "Solitude"),
                new DimensionContainer<>(SolitudeDimension::new)
        );

        event.registry.register(
                Identifier.of(NAMESPACE, "adminspace"),
                new DimensionContainer<>(AdminspaceDimension::new)
        );
    }
}
