package io.tff.aether.items.tools;

import io.tff.aether.blocks.BlocksAether;
import net.minecraft.item.ItemStack;

import io.tff.aether.items.util.EnumAetherToolType;

public class ItemSkyrootTool extends ItemAetherTool
{

	public ItemSkyrootTool(EnumAetherToolType toolType)
	{
		super(ToolMaterial.WOOD, toolType);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return repair.getItem() == getItemFromBlock(BlocksAether.skyroot_plank);
	}
}