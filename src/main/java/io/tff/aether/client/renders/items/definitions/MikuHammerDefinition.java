package io.tff.aether.client.renders.items.definitions;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

import io.tff.aether.Aether;

public class MikuHammerDefinition implements ItemMeshDefinition
{

	public ModelResourceLocation miku_hammer, miku, jeb_hammer;

	public MikuHammerDefinition()
	{
		this.miku_hammer = new ModelResourceLocation(Aether.modAddress() + "miku_hammer", "inventory");
		this.miku = new ModelResourceLocation(Aether.modAddress() + "hammer_projectile", "inventory");
		this.jeb_hammer = new ModelResourceLocation(Aether.modAddress() + "jeb_hammer", "inventory");
	}

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) 
	{
		if (stack.getItemDamage() == 1565)
		{
			return this.miku;
		}
		
		if (stack.getDisplayName().toLowerCase().equals("hammer of jeb"))
		{
			return this.jeb_hammer;
		}
		else
		{
			return this.miku_hammer;
		}
	}

}