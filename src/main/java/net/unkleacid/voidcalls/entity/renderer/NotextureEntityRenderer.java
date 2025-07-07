package net.unkleacid.voidcalls.entity.renderer;

import net.unkleacid.voidcalls.entity.model.NotextureEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class NotextureEntityRenderer extends TropiEntityRenderer {

    public NotextureEntityRenderer() {
        super(new NotextureEntityModel(), 0.5F, "assets/voidcalls/stationapi/textures/entity/notexture.png");
    }
}
