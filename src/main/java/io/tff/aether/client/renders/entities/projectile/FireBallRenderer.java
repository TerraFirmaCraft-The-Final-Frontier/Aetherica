package io.tff.aether.client.renders.entities.projectile;

import io.tff.aether.entities.projectile.crystals.EntityFireBall;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import io.tff.aether.client.models.entities.CrystalModel;

public class FireBallRenderer extends RenderLiving<EntityFireBall>
{

	private CrystalModel model;

    public FireBallRenderer(RenderManager renderManager)
    {
		super(renderManager, new CrystalModel(), 0.25F);
		this.model = (CrystalModel)this.mainModel;
    }

    public void preRenderCallback(EntityFireBall hs, float f)
    {
		for(int i = 0; i < 3; i ++) 
		{
			model.sinage[i] = hs.sinage[i];
		}
		
		GlStateManager.translate(0, 0.3D, 0);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityFireBall entity)
	{
    	return new ResourceLocation("aether", "textures/entities/crystals/firoball.png");
	}

}