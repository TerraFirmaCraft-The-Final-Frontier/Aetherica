package io.tff.aether.items.staffs;

import io.tff.aether.entities.passive.EntityMiniCloud;
import io.tff.aether.player.PlayerAether;
import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import io.tff.aether.api.AetherAPI;
import io.tff.aether.api.player.IPlayerAether;
import io.tff.aether.items.ItemsAether;

public class ItemCloudStaff extends Item
{

	public ItemCloudStaff()
	{
		this.setCreativeTab(AetherCreativeTabs.misc);
		this.setMaxDamage(60);
		this.setMaxStackSize(1);
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer, EnumHand hand)
    {
		IPlayerAether playerAether = AetherAPI.getInstance().get(entityplayer);
		ItemStack heldItem = entityplayer.getHeldItem(hand);

		if (((PlayerAether)playerAether).clouds.isEmpty())
		{
			EntityMiniCloud leftCloud = new EntityMiniCloud(world, entityplayer, 0);
			EntityMiniCloud rightCloud = new EntityMiniCloud(world, entityplayer, 1);

			((PlayerAether)playerAether).clouds.add(leftCloud);
			((PlayerAether)playerAether).clouds.add(rightCloud);

			world.spawnEntity(leftCloud);
			world.spawnEntity(rightCloud);

			heldItem.damageItem(1, entityplayer);

	    	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
		}
		else if (entityplayer.isSneaking())
		{
			for (Entity cloud : ((PlayerAether) playerAether).clouds)
			{
				if (cloud instanceof EntityMiniCloud)
				{
					((EntityMiniCloud) cloud).lifeSpan = 0;
				}
			}
		}

    	return new ActionResult<ItemStack>(EnumActionResult.PASS, heldItem);
    }

}