package io.tff.aether.client.renders.entities;

import io.tff.aether.client.renders.entities.layer.LayerOldZephyrModel;
import io.tff.aether.client.renders.entities.layer.LayerZephyrTransparency;
import io.tff.aether.entities.hostile.EntityZephyr;
import io.tff.aether.AetherConfig;
import io.tff.aether.client.models.entities.ZephyrModel;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class ZephyrRenderer extends RenderLiving<EntityZephyr>
{

    private static final ResourceLocation ZEPHYR_TEXTURE = new ResourceLocation("aether", "textures/entities/zephyr/zephyr_main.png");

	public ZephyrRenderer(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new ZephyrModel(), 0.5F);
		this.addLayer(new LayerZephyrTransparency(this));
		this.addLayer(new LayerOldZephyrModel(this));
	}

    protected void renderZephyrMovement(EntityZephyr zephyr, float partialTickTime)
    {
        float f1 = ((float)zephyr.shootingAI.prevAttackCounter + (float)(zephyr.shootingAI.attackCounter - zephyr.shootingAI.prevAttackCounter) * partialTickTime) / 20.0F;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        f1 = 1.0F / (f1 * f1 * f1 * f1 * f1 * 2.0F + 1.0F);

        float f2 = (8.0F + f1) / 2.0F;
        float f3 = (8.0F + 1.0F / f1) / 2.0F;
        GlStateManager.scale(f3, f2, f3);
        GlStateManager.translate(0, 0.5D, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        
        if (AetherConfig.visual_options.legacy_models)
        {
        	GlStateManager.scale(0.8, 0.8, 0.8);
        	GlStateManager.translate(0, -0.1D, 0);
        }
        
        
    }

    @Override
    protected void preRenderCallback(EntityZephyr zephyr, float partialTickTime)
    {
        this.renderZephyrMovement(zephyr, partialTickTime);
    }
    
	@Override
	protected ResourceLocation getEntityTexture(EntityZephyr zephyr) 
	{
		return ZEPHYR_TEXTURE;
	}

}