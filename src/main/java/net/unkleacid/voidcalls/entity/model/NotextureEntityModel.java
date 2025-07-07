package net.unkleacid.voidcalls.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class NotextureEntityModel extends EntityModel {
    public TropiModelPart body     = new TropiModelPart(0, 0);
    public TropiModelPart rightArm = new TropiModelPart(0, 0);
    public TropiModelPart leftArm  = new TropiModelPart(0, 0);
    public TropiModelPart rightLeg = new TropiModelPart(0, 0);
    public TropiModelPart leftLeg  = new TropiModelPart(0, 0);

    public TropiModelPart h1  = new TropiModelPart(0, 0);
    public TropiModelPart h2  = new TropiModelPart(0, 0);
    public TropiModelPart h3  = new TropiModelPart(0, 0);
    public TropiModelPart h4  = new TropiModelPart(0, 0);
    public TropiModelPart h5  = new TropiModelPart(0, 0);
    public TropiModelPart h6  = new TropiModelPart(0, 0);
    public TropiModelPart h7  = new TropiModelPart(0, 0);
    public TropiModelPart h8  = new TropiModelPart(0, 0);
    public TropiModelPart h9  = new TropiModelPart(0, 0);
    public TropiModelPart h10 = new TropiModelPart(0, 0);
    public TropiModelPart h11 = new TropiModelPart(0, 0);
    public TropiModelPart h12 = new TropiModelPart(0, 0);
    public TropiModelPart h13 = new TropiModelPart(0, 0);
    public TropiModelPart h14 = new TropiModelPart(0, 0);
    public TropiModelPart h15 = new TropiModelPart(0, 0);
    public TropiModelPart h16 = new TropiModelPart(0, 0);
    public TropiModelPart h17 = new TropiModelPart(0, 0);
    public TropiModelPart h18 = new TropiModelPart(0, 0);
    public TropiModelPart h19 = new TropiModelPart(0, 0);
    public TropiModelPart h20 = new TropiModelPart(0, 0);
    public TropiModelPart h21 = new TropiModelPart(0, 0);
    public TropiModelPart h22 = new TropiModelPart(0, 0);
    public TropiModelPart h23 = new TropiModelPart(0, 0);

    public NotextureEntityModel() {
        body.addCube(-4F, 12F, -2F, 8, 12, 4, 0F, 12F, 0F);
        rightArm.addCube(4F, 12F, -2F, 4, 12, 4, 4F, 24F, -2F);
        leftArm.addCube(-8F, 12F, -2F, 4, 12, 4, -8F, 24F, -2F);
        rightLeg.addCube(-0.1F, 0F, -2F, 4, 12, 4, -0.1F, 12F, -2F);
        leftLeg.addCube(-3.9F, 0F, -2F, 4, 12, 4, -3.9F, 12F, -2F);

        h1 .addCube( 0F, 24F, -4F,  4,  7, 7, 0F, 24F, -4F);
        h2 .addCube(-1F, 31F, -3F,  4,  1, 5, 0F, 31F, -3F);
        h3 .addCube(-1F, 30F, -2F,  2,  5, 2, 0F, 30F, -2F);
        h4 .addCube( 9.2132F,37.2659F,-1.1F,1,5,1,9.2132F,37.2659F,-1.1F);
        h5 .addCube( 6.2372F,36.3005F,-1F,  1,5,1,6.2372F,36.3005F,-1F);
        h6 .addCube( 3.6519F,32.2689F,-1.1F,1,5,1,3.6519F,32.2689F,-1.1F);
        h7 .addCube( 3.9299F,28.3215F,-1F,  1,5,1,3.9299F,28.3215F,-1F);
        h8 .addCube( 7.2132F,32.2689F,-9.1F,1,5,1,7.2132F,32.2689F,-9.1F);
        h9 .addCube( 4.2372F,31.3005F,-9F,  1,5,1,4.2372F,31.3005F,-9F);
        h10.addCube(1.6519F,27.2689F,-9.1F,1,5,1,1.6519F,27.2689F,-9.1F);
        h11.addCube(4.9299F,23.3215F,-9F,  1,5,1,4.9299F,23.3215F,-9F);
        h12.addCube(-1.7868F,48.2659F,-7.1F,1,5,1,-1.7868F,48.2659F,-7.1F);
        h13.addCube(-4.7628F,47.3005F,-7F,  1,5,1,-4.7628F,47.3005F,-7F);
        h14.addCube(-7.3481F,43.2689F,-7.1F,1,5,1,-7.3481F,43.2689F,-7.1F);
        h15.addCube(-4.0701F,39.3215F,-7F,  1,5,1,-4.0701F,39.3215F,-7F);
        h16.addCube(-3.7868F,39.2659F,-7.1F,1,5,1,-3.7868F,39.2659F,-7.1F);
        h17.addCube(-6.7628F,38.3005F,-7F,  1,5,1,-6.7628F,38.3005F,-7F);
        h18.addCube(-9.3481F,34.2689F,-7.1F,1,5,1,-9.3481F,34.2689F,-7.1F);
        h19.addCube(-6.0701F,30.3215F,-7F,  1,5,1,-6.0701F,30.3215F,-7F);
        h20.addCube(-7.0876F,38.4903F,-2.1F,2,5,2,-7.0876F,38.4903F,-2.1F);
        h21.addCube(-7.1399F,35.0261F,-2F,  2,5,2,-7.1399F,35.0261F,-2F);
        h22.addCube(0.7F,35.9F,-2.1F,       2,5,2,0.7F,35.9F,-2.1F);
        h23.addCube(-4F,24F,0F,            4,4,4,-4F,24F,0F);
    }

    @Override
    public void render(float limbAngle, float limbDistance, float animProgress,
                       float headYaw, float headPitch, float scale) {
        body.render(scale);
        rightArm.render(scale);
        leftArm.render(scale);
        rightLeg.render(scale);
        leftLeg.render(scale);
        h1.render(scale); h2.render(scale); h3.render(scale); h4.render(scale); h5.render(scale);
        h6.render(scale); h7.render(scale); h8.render(scale); h9.render(scale); h10.render(scale);
        h11.render(scale); h12.render(scale); h13.render(scale); h14.render(scale); h15.render(scale);
        h16.render(scale); h17.render(scale); h18.render(scale); h19.render(scale);
        h20.render(scale); h21.render(scale); h22.render(scale); h23.render(scale);
    }

    @Override
    public void setAngles(float limbAngle, float limbDistance, float animProgress,
                          float headYaw, float headPitch, float scale) {
        float slowAngle = limbAngle / 3.0F;
        float slowDistance = limbDistance * 0.333F;
        rightArm.angleLeg(slowAngle, slowDistance, true);
        leftArm.angleLeg(slowAngle, slowDistance, false);
        rightLeg.angleLeg(slowAngle, slowDistance, false);
        leftLeg.angleLeg(slowAngle, slowDistance, true);
        h1.angleHead(headPitch, headYaw);
        h2.angleHead(headPitch, headYaw);
        h3.angleHead(headPitch, headYaw);
        h4.angleHead(headPitch, headYaw);
        h5.angleHead(headPitch, headYaw);
        h6.angleHead(headPitch, headYaw);
        h7.angleHead(headPitch, headYaw);
        h8.angleHead(headPitch, headYaw);
        h9.angleHead(headPitch, headYaw);
        h10.angleHead(headPitch, headYaw);
        h11.angleHead(headPitch, headYaw);
        h12.angleHead(headPitch, headYaw);
        h13.angleHead(headPitch, headYaw);
        h14.angleHead(headPitch, headYaw);
        h15.angleHead(headPitch, headYaw);
        h16.angleHead(headPitch, headYaw);
        h17.angleHead(headPitch, headYaw);
        h18.angleHead(headPitch, headYaw);
        h19.angleHead(headPitch, headYaw);
        h20.angleHead(headPitch, headYaw);
        h21.angleHead(headPitch, headYaw);
        h22.angleHead(headPitch, headYaw);
        h23.angleHead(headPitch, headYaw);
    }

    @Override
    public void animateModel(LivingEntity e, float limbAngle, float limbDistance, float tickDelta) {
        // Do nothing
    }
}
