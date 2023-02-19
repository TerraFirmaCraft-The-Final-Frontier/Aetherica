package io.tff.aether.client.renders.entities;

import io.tff.aether.client.renders.entities.layer.LayerPhygHalo;
import io.tff.aether.client.renders.entities.layer.LayerPhygSaddle;
import io.tff.aether.client.renders.entities.layer.PhygWingLayer;
import io.tff.aether.entities.passive.mountable.EntityPhyg;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class PhygRenderer extends RenderLiving<EntityPhyg>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation("aether", "textures/entities/phyg/phyg.png");

	public PhygRenderer(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new ModelPig(), 0.7F);
		this.addLayer(new PhygWingLayer(rendermanagerIn));
		this.addLayer(new LayerPhygHalo(rendermanagerIn));
		this.addLayer(new LayerPhygSaddle(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPhyg entity)
	{
		return TEXTURE;
	}

}