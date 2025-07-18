package net.unkleacid.voidcalls.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import org.lwjgl.opengl.GL11;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class AngelEntityModel extends EntityModel {

    private final ModelPart panel;
    private final Random random = new Random();

    public AngelEntityModel() {
        panel = new ModelPart(0, 0);
        panel.addCuboid(-64F, -32F, 0F, 128, 64, 0, 0F);
        panel.setPivot(0F, 0F, 0F);
        panel.mirror = false;
    }

    @Override
    public void render(float limbAngle, float limbDistance, float ageInTicks,
                       float netHeadYaw, float headPitch, float scale) {
        float rotationJitter = 1.5F;
        float translateJitter = 0.01F;

        float rx = (random.nextFloat() - 0.5F) * 2F * rotationJitter;
        float ry = (random.nextFloat() - 0.5F) * 2F * rotationJitter;
        float rz = (random.nextFloat() - 0.5F) * 2F * rotationJitter;

        float tx = (random.nextFloat() - 0.5F) * 2F * translateJitter;
        float ty = (random.nextFloat() - 0.5F) * 2F * translateJitter;
        float tz = (random.nextFloat() - 0.5F) * 2F * translateJitter;

        GL11.glPushMatrix();
        GL11.glTranslatef(tx, ty, tz);
        GL11.glRotatef(rx, 1F, 0F, 0F);
        GL11.glRotatef(ry, 0F, 1F, 0F);
        GL11.glRotatef(rz, 0F, 0F, 1F);
        panel.render(scale);
        GL11.glPopMatrix();
    }

    @Override
    public void setAngles(float limbAngle, float limbDistance,
                          float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    }
}
