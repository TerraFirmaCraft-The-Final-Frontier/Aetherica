package io.tff.aether.items.tools;

import io.tff.aether.blocks.BlocksAether;
import io.tff.aether.entities.block.EntityFloatingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import io.tff.aether.items.util.EnumAetherToolType;

public class ItemGravititeTool extends ItemAetherTool
{

	public ItemGravititeTool(EnumAetherToolType toolType) 
	{
		super(ToolMaterial.DIAMOND, toolType);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return repair.getItem() == getItemFromBlock(BlocksAether.enchanted_gravitite);
	}

	@Override
	@SuppressWarnings("deprecation")
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	EntityFloatingBlock entity = new EntityFloatingBlock(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, world.getBlockState(pos));

		ItemStack heldItem = player.getHeldItem(hand);

    	if ((this.getDestroySpeed(heldItem, world.getBlockState(pos)) == this.efficiency || ForgeHooks.isToolEffective(world, pos, heldItem)) && world.isAirBlock(pos.up()))
    	{
        	if (world.getTileEntity(pos) != null || world.getBlockState(pos).getBlock().getBlockHardness(world.getBlockState(pos), world, pos) == -1.0F)
        	{
        		return EnumActionResult.FAIL;
        	}

        	if (!world.isRemote)
        	{
        		world.spawnEntity(entity);
        	}

        	heldItem.damageItem(4, player);
    	}

        return EnumActionResult.SUCCESS;
    }

}