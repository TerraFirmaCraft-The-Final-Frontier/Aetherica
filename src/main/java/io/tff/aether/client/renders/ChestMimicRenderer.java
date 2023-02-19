package io.tff.aether.client.renders;

import io.tff.aether.tile_entities.TileEntityChestMimic;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityChest;

public class ChestMimicRenderer extends TileEntityChestRenderer
{

	@Override
	public void render(TileEntityChest par1TileEntityChest, double x, double y, double z, float partialTicks, int destroyStage, float alpha) 
    {
    	if (par1TileEntityChest == null)
    	{
    		TileEntityRendererDispatcher.instance.render(new TileEntityChestMimic(), 0, 0, 0, 0);
    		return;
    	}
    	
    	super.render(par1TileEntityChest, x, y, z, partialTicks, destroyStage, alpha);
    }

}