package io.tff.aether.client.renders.entities.layer;

import io.tff.aether.entities.passive.mountable.EntityFlyingCow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import io.tff.aether.client.models.entities.FlyingCowWingModel;

public class FlyingCowWingLayer implements LayerRenderer<EntityFlyingCow>
{

	private static final ResourceLocation TEXTURE_WINGS = new ResourceLocation("aether_legacy", "textures/entities/flying_cow/wings.png");

	private RenderManager renderManager;

	private FlyingCowWingModel model;

	public FlyingCowWingLayer(RenderManager manager)
	{
		this.renderManager = manager;
		this.model = new FlyingCowWingModel();
	}

	@Override
	public void doRenderLayer(EntityFlyingCow cow, float limbSwing, float prevLimbSwing, float partialTicks, float rotation, float interpolateRotation, float prevRotationPitch, float scale)
	{
		this.renderManager.renderEngine.bindTexture(TEXTURE_WINGS);
		this.model.render(cow, limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale);
	}

	@Override
	public boolean shouldCombineTextures() 
	{
		return true;
	}

}