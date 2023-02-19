package io.tff.aether.containers.slots;

import io.tff.aether.tile_entities.TileEntityIncubator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import io.tff.aether.items.ItemsAether;

public class SlotIncubator extends Slot
{

	private TileEntityIncubator incubator;

	private EntityPlayer player;

    public SlotIncubator(TileEntityIncubator inv, int slot, int x, int y, EntityPlayer player)
    {
        super((IInventory) inv, slot, x, y);
        this.incubator = (TileEntityIncubator) inv;
        this.player = player;
    }

    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() == ItemsAether.moa_egg;
    }

    public int getSlotStackLimit()
    {
        return 1;
    }

    @Override
    public void putStack(ItemStack stack)
    {
    	super.putStack(stack);

    	this.incubator.owner = player;
    }

}