package net.unkleacid.voidcalls.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.unkleacid.voidcalls.entity.model.NearsightedEntityModel;

@Environment(EnvType.CLIENT)
public class LamplighterEntityRenderer extends TropiEntityRenderer {

    public LamplighterEntityRenderer() {
        super(new NearsightedEntityModel(), 0.5F, "assets/voidcalls/stationapi/textures/entity/lamplighter.png");
    }
}
