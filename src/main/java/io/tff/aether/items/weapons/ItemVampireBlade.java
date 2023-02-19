package io.tff.aether.items.weapons;

import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import io.tff.aether.items.ItemsAether;

public class ItemVampireBlade extends ItemSword
{

    public ItemVampireBlade()
    {
    	super(ToolMaterial.DIAMOND);

        this.setCreativeTab(AetherCreativeTabs.weapons);
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

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
		EntityPlayer player = (EntityPlayer)attacker;

		if(player.getHealth() < player.getMaxHealth())
		{
			player.heal(1.0F);
		}

        return super.hitEntity(stack, target, attacker);
    }

}