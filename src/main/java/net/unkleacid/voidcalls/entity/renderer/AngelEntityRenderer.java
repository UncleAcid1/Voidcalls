package net.unkleacid.voidcalls.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.unkleacid.voidcalls.entity.model.AngelEntityModel;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class AngelEntityRenderer extends LivingEntityRenderer {
    public AngelEntityRenderer() {
        super(new AngelEntityModel(), 0.0F);
    }

    @Override
    public void render(LivingEntity entity, double x, double y, double z, float yaw, float delta) {
        GL11.glPushMatrix();

        GL11.glTranslated(x, y, z);

        GL11.glRotatef(-this.dispatcher.yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(90F,0.0F, 0.0F, -1.0F);



        this.bindTexture("/assets/voidcalls/stationapi/textures/entity/angel.png");

        float scale = 0.0625F;
        this.model.render(0F, 0F, 0F, 0F, 0F, scale);

        GL11.glPopMatrix();

        this.renderNameTag(entity, x, y, z);
    }
}
