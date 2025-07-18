package net.unkleacid.voidcalls.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.opengl.GL11;
import net.unkleacid.voidcalls.entity.model.AngelEntityModel;
import org.lwjgl.opengl.GL12;

@Environment(EnvType.CLIENT)
public class AngelEntityRenderer extends LivingEntityRenderer {
    public AngelEntityRenderer() {
        super(new AngelEntityModel(), 0.0F);
    }

    @Override
    public void render(LivingEntity entity, double x, double y, double z, float yaw, float delta) {
        GL11.glPushMatrix();

        GL11.glTranslated(x, y + entity.height * -0.1D, z);
        GL11.glRotatef(-this.dispatcher.yaw, 0F, 1F, 0F);
        GL11.glRotatef(90F, 0F, 0F, -1F);

        this.bindTexture("/assets/voidcalls/stationapi/textures/entity/angel.png");

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        float scale = 0.0625F;
        this.model.render(0F, 0F, 0F, 0F, 0F, scale);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        GL11.glPopMatrix();

        this.renderNameTag(entity, x, y + entity.height, z);
    }
}
