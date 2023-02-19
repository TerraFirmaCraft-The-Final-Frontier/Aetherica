package io.tff.aether.client.renders.items.util;

import io.tff.aether.items.accessories.ItemAccessoryDyable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import io.tff.aether.blocks.BlocksAether;
import io.tff.aether.blocks.natural.BlockAercloud;
import io.tff.aether.items.ItemMoaEgg;
import io.tff.aether.items.accessories.ItemAccessory;
import io.tff.aether.items.armor.ItemAetherArmor;

public class AetherColor implements IItemColor, IBlockColor
{

	private Item item;

	public AetherColor(Item item)
	{
		this.item = item;
	}

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex)
	{
		if (this.item.getClass() == ItemAccessory.class)
		{
			return ((ItemAccessory)stack.getItem()).getColorFromItemStack(stack, 0);
		}

		if (this.item.getClass() == ItemAccessoryDyable.class)
		{
			return ((ItemAccessoryDyable)stack.getItem()).getColor(stack);
		}

		if (this.item instanceof ItemAetherArmor)
		{
			return ((ItemAetherArmor)stack.getItem()).getColorization(stack);
		}

		if (this.item instanceof ItemMoaEgg)
		{
			return ((ItemMoaEgg)stack.getItem()).getColorFromItemStack(stack);
		}

		if (this.item == Item.getItemFromBlock(BlocksAether.aercloud))
		{
			return BlockAercloud.getHexColor(stack);
		}

		return 0;
	}

	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex)
	{
		if (this.item == Item.getItemFromBlock(BlocksAether.aercloud))
		{
			return BlockAercloud.getHexColor(new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)));
		}

		return 0;
	}

}