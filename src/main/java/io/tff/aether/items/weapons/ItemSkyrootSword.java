package io.tff.aether.items.weapons;

import io.tff.aether.blocks.BlocksAether;
import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.NonNullList;

public class ItemSkyrootSword extends ItemSword
{

	public ItemSkyrootSword() 
	{
		super(ToolMaterial.WOOD);
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
    	if (tab == AetherCreativeTabs.weapons || tab == CreativeTabs.SEARCH)
    	{
            items.add(new ItemStack(this));
    	}
    }

	@Override
	public boolean getIsRepairable(ItemStack repairingItem, ItemStack material)
	{
		return material.getItem() == Item.getItemFromBlock(BlocksAether.aether_log) || material.getItem() == Item.getItemFromBlock(BlocksAether.skyroot_plank);
	}

}