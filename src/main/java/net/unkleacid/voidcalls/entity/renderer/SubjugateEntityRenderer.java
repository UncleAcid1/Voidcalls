package net.unkleacid.voidcalls.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.unkleacid.voidcalls.entity.SubjugateEntity;
import net.unkleacid.voidcalls.entity.model.SubjugateEntityModel;

@Environment(EnvType.CLIENT)
public class SubjugateEntityRenderer extends LivingEntityRenderer {
    private static final String TEXTURE_PATH = "/assets/voidcalls/stationapi/textures/entity/subjugate.png";

    public SubjugateEntityRenderer() {
        super(new SubjugateEntityModel(), 0.5F);
    }

    @Override
    public void render(LivingEntity entity, double x, double y, double z, float yaw, float delta) {
        this.bindTexture(TEXTURE_PATH);
        super.render((SubjugateEntity) entity, x, y, z, yaw, delta);
    }
}
