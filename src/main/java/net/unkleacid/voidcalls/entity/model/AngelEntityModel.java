package net.unkleacid.voidcalls.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModel;

@Environment(EnvType.CLIENT)
public class AngelEntityModel extends EntityModel {

    public final TropiModelPart panelTL = new TropiModelPart(0, 0);
    public final TropiModelPart panelTR = new TropiModelPart(64, 0);
    public final TropiModelPart panelBL = new TropiModelPart(0, 32);
    public final TropiModelPart panelBR = new TropiModelPart(64, 32);

    public AngelEntityModel() {
        panelTL.addCube(-32F,  0F, 0F, 64, 32, 0, 0F, 0F, 0F);
        panelTR.addCube(-32F,  32F,0F, 64, 32, 0, 0F, 32F,0F);
        panelBL.addCube(-32F,  0F, 0F, 64, 32, 0, 0F, 0F, 0F);
        panelBR.addCube(-32F,  32F,0F, 64, 32, 0, 0F, 32F,0F);
    }

    @Override
    public void render(float limbAngle, float limbDistance, float animProgress,
                       float headYaw, float headPitch, float scale) {
        panelTL.render(scale);
        panelTR.render(scale);
        panelBL.render(scale);
        panelBR.render(scale);
    }

    @Override
    public void setAngles(float limbAngle, float limbDistance, float animProgress,
                          float headYaw, float headPitch, float scale) {
    }

    @Override
    public void animateModel(net.minecraft.entity.LivingEntity entity,
                             float limbAngle, float limbDistance, float tickDelta) {
    }
}
