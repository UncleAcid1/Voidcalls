package net.unkleacid.voidcalls.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.unkleacid.voidcalls.entity.model.AngelEntityModel;
import net.minecraft.client.render.entity.LivingEntityRenderer;

@Environment(EnvType.CLIENT)
public class AngelEntityRenderer extends TropiEntityRenderer {

    public AngelEntityRenderer() {
        super(new AngelEntityModel(), 0.5F, "/assets/voidcalls/stationapi/textures/entity/angel.png");
    }
}
