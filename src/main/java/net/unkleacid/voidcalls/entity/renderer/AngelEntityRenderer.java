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

        // Move to the entity's position
        GL11.glTranslated(x, y, z);

        // Apply fixed rotation to face forward (positive Z) and correct texture
        GL11.glRotatef(180, 0, 1, 0); // Flip around Y if needed (texture faces camera)
        GL11.glRotatef(-90F, 1F, 0F, 0F); // rotate so the flat plane faces +Z, not up



        this.bindTexture("/assets/voidcalls/stationapi/textures/entity/angel.png");

        float scale = 0.0625F;
        this.model.render(0F, 0F, 0F, 0F, 0F, scale);

        GL11.glPopMatrix();

        this.renderNameTag(entity, x, y, z);
    }
}
