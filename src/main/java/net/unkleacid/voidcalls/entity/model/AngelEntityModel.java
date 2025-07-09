package net.unkleacid.voidcalls.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

@Environment(EnvType.CLIENT)
public class AngelEntityModel extends EntityModel {

    private final ModelPart panel;

    public AngelEntityModel() {
        panel = new ModelPart(0, 0);
        panel.addCuboid(-32F, -64F, 0F, 64, 128, 0, 0F);
        panel.setPivot(0F, 0F, 0F);
        panel.mirror = false;
    }

    @Override
    public void render(float limbAngle, float limbDistance, float ageInTicks,
                       float netHeadYaw, float headPitch, float scale) {
        panel.render(scale);
    }

    @Override
    public void setAngles(float limbAngle, float limbDistance,
                          float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    }
}
