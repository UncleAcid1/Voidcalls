package net.unkleacid.voidcalls.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class SubjugateEntityModel extends EntityModel {
    public TropiModelPart rightLeg   = new TropiModelPart(54, 0);
    public TropiModelPart leftLeg    = new TropiModelPart(40, 0);
    public TropiModelPart bodyRight  = new TropiModelPart(78, 0);
    public TropiModelPart bodyLeft   = new TropiModelPart(64, 0);
    public TropiModelPart rightArm   = new TropiModelPart(54, 0);
    public TropiModelPart leftArm    = new TropiModelPart(20, 0);
    public TropiModelPart head       = new TropiModelPart(90, 0);

    public SubjugateEntityModel() {
        // Right Leg
        rightLeg.addCube(
                -7.0F, 0.0F, -1.0F,
                7,   40,   0,
                -7.0F, 0.0F, -1.0F
        );

        // Left Leg
        leftLeg.addCube(
                0.0F, 0.0F, -1.0F,
                7,   40,   0,
                0.0F, 0.0F, -1.0F
        );

        // Body Right Half
        bodyRight.addCube(
                -7.0F, 40.0F, -1.0F,
                7,   40,   0,
                -7.0F, 40.0F, -1.0F
        );

        // Body Left Half
        bodyLeft.addCube(
                0.0F, 40.0F, -1.0F,
                7,   40,   0,
                0.0F, 40.0F, -1.0F
        );

        // Right Arm
        rightArm.addCube(
                -17.0F, 23.0F, -1.2F,
                10,  57,   0,
                -17.0F, 23.0F, -1.2F
        );

        // Left Arm
        leftArm.addCube(
                7.0F, 23.0F, -1.0F,
                10,  57,   0,
                7.0F, 23.0F, -1.0F
        );

        // Head
        head.addCube(
                -6.9743F, 80.0836F, -1.0F,
                14,   14,   0,
                -6.9743F, 80.0836F, -1.0F
        );
    }

    @Override
    public void render(float limbAngle, float limbDistance, float animProgress,
                       float headYaw, float headPitch, float scale) {
        rightLeg.render(scale);
        leftLeg.render(scale);
        bodyRight.render(scale);
        bodyLeft.render(scale);
        rightArm.render(scale);
        leftArm.render(scale);
        head.render(scale);
    }

    @Override
    public void animateModel(LivingEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        float jitterX = (entity.age % 3 - 1) * 0.01F;
        float jitterY = (entity.age % 5 - 2) * 0.01F;

        // Jitter head only
        head.pitch = jitterX;
        head.yaw   = jitterY;
    }

    @Override
    public void setAngles(float limbAngle, float limbDistance, float animProgress,
                          float headYaw, float headPitch, float scale) {
        float slowAngle    = limbAngle / 3.0F;
        float slowDistance = limbDistance * 0.333F;

        rightArm.angleLeg(slowAngle, slowDistance, true);
        leftArm.angleLeg(slowAngle, slowDistance, false);
        rightLeg.angleLeg(slowAngle, slowDistance, false);
        leftLeg.angleLeg(slowAngle, slowDistance, true);

        head.angleHead(headPitch, headYaw);
    }
}