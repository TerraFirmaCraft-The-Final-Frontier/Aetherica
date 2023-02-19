package io.tff.aether.client.renders.entities;

import io.tff.aether.client.renders.entities.layer.LayerValkyrieHalo;
import io.tff.aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import io.tff.aether.client.models.entities.ValkyrieModel;

public class ValkyrieQueenRenderer extends RenderLiving<EntityValkyrieQueen>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation("aether", "textures/bosses/valkyrie_queen/valkyrie_queen.png");

	public ValkyrieQueenRenderer(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new ValkyrieModel(), 0.3F);
		this.addLayer(new LayerValkyrieHalo(rendermanagerIn));
	}

    protected void preRenderCallback(EntityValkyrieQueen valkyrie, float partialTickTime)
    {
        ((ValkyrieModel)this.getMainModel()).sinage = valkyrie.sinage;
        ((ValkyrieModel)this.getMainModel()).gonRound = valkyrie.onGround;
        //((ValkyrieModel)this.getMainModel()).halow = true;
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityValkyrieQueen entity) 
	{
		return TEXTURE;
	}

}
