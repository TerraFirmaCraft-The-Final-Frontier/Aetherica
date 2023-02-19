package io.tff.aether.containers.inventory;

import javax.annotation.Nullable;

import io.tff.aether.advancements.AetherAdvancements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

import io.tff.aether.items.ItemsAether;

public class InventoryLore extends InventoryBasic
{

	private EntityPlayer player;

	public InventoryLore(EntityPlayer player) 
	{
		super("Lore Item", false, 1);

		this.player = player;
	}

	@Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
    	if (this.player instanceof EntityPlayerMP && stack.getItem() == ItemsAether.lore_book)
    	{
    		AetherAdvancements.LORE_ITEM_TRIGGER.trigger((EntityPlayerMP) this.player, stack);
    	}

    	super.setInventorySlotContents(index, stack);
    }

}