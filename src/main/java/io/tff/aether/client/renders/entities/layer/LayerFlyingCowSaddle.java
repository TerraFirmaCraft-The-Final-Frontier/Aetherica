package io.tff.aether.client.renders.entities.layer;

import io.tff.aether.entities.passive.mountable.EntityFlyingCow;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import io.tff.aether.client.models.entities.FlyingCowModel;
import io.tff.aether.client.renders.entities.FlyingCowRenderer;

@SideOnly(Side.CLIENT)
public class LayerFlyingCowSaddle implements LayerRenderer<EntityFlyingCow>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("aether", "textures/entities/flying_cow/saddle.png");

    private final FlyingCowRenderer cowRenderer;
    private final FlyingCowModel cowModel = new FlyingCowModel(0.5F);

    public LayerFlyingCowSaddle(FlyingCowRenderer cowRendererIn)
    {
        this.cowRenderer = cowRendererIn;
    }

    public void doRenderLayer(EntityFlyingCow entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.getSaddled())
        {
            this.cowRenderer.bindTexture(TEXTURE);
            this.cowModel.setModelAttributes(this.cowRenderer.getMainModel());
            this.cowModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}