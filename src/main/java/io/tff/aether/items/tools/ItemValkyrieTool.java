package io.tff.aether.items.tools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import io.tff.aether.items.ItemsAether;
import io.tff.aether.items.util.EnumAetherToolType;

public class ItemValkyrieTool extends ItemAetherTool 
{

	public ItemValkyrieTool(EnumAetherToolType toolType) 
	{
		super(ToolMaterial.DIAMOND, toolType);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return false;
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }
}