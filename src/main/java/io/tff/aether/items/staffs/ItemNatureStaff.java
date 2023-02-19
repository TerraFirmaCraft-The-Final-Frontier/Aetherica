package io.tff.aether.items.staffs;

import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.item.Item;

public class ItemNatureStaff extends Item
{

	public ItemNatureStaff()
	{
		this.setCreativeTab(AetherCreativeTabs.misc);
		this.setMaxStackSize(1);
		this.setMaxDamage(100);
	}

}