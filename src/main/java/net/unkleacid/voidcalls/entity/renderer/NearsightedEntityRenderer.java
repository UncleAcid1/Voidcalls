package net.unkleacid.voidcalls.entity.renderer;

import net.unkleacid.voidcalls.entity.model.NearsightedEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NearsightedEntityRenderer extends TropiEntityRenderer {

    public NearsightedEntityRenderer() {
        super(new NearsightedEntityModel(), 0.5F, "assets/voidcalls/stationapi/textures/entity/nearsighted.png");
    }
}
