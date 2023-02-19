package io.tff.aether.items.dungeon;

import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import io.tff.aether.items.ItemsAether;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemVictoryMedal extends Item
{

	public ItemVictoryMedal()
	{
		super();

		this.setMaxStackSize(10);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

}