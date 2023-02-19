package io.tff.aether.client.renders.entities;

import io.tff.aether.entities.hostile.EntityWhirlwind;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class WhirlwindRenderer extends Render<EntityWhirlwind>
{

	public WhirlwindRenderer(RenderManager renderManager)
	{
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWhirlwind entity) 
	{
		return null;
	}

}