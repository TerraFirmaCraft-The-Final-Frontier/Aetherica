package io.tff.aether.client.renders.entities;

import io.tff.aether.client.renders.entities.layer.LayerValkyrieHalo;
import io.tff.aether.entities.bosses.EntityValkyrie;
import io.tff.aether.client.models.entities.ValkyrieModel;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class ValkyrieRenderer extends RenderLiving<EntityValkyrie>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation("aether", "textures/entities/valkyrie/valkyrie.png");

	public ValkyrieRenderer(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new ValkyrieModel(), 0.3F);
		this.addLayer(new LayerValkyrieHalo(rendermanagerIn));
	}

    protected void preRenderCallback(EntityValkyrie valkyrie, float partialTickTime)
    {
        ((ValkyrieModel)this.getMainModel()).sinage = valkyrie.sinage;
        ((ValkyrieModel)this.getMainModel()).gonRound = valkyrie.onGround;
        //((ValkyrieModel)this.getMainModel()).halow = true;
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityValkyrie entity) 
	{
		return TEXTURE;
	}

}
