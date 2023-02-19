package io.tff.aether.blocks.decorative;

import javax.annotation.Nullable;

import io.tff.aether.entities.block.EntityTNTPresent;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import io.tff.aether.items.ItemsAether;

public class BlockPresent extends Block 
{

	public BlockPresent() 
	{
		super(Material.GRASS);

		this.setHardness(0.6F);
		this.setSoundType(SoundType.PLANT);
	}

	@Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
		int randomNumber = (int) (((9 - 6 + 1) * RANDOM.nextDouble()) + 6);
		int crateType = RANDOM.nextInt(4);

		if (crateType == 0)
		{
			for (int size = 1; size <= randomNumber; ++size)
			{
				if (!world.isRemote)
				{
					world.spawnEntity(new EntityXPOrb(world, pos.getX(), pos.getY(), pos.getZ(), size));
				}
			}
		}
		else if (crateType == 1)
		{
			if (RANDOM.nextInt(9) == 0)
			{
				spawnAsEntity(world, pos, new ItemStack(ItemsAether.candy_cane_sword));
			}
			else
			{
				for (int size = 1; size <= randomNumber; ++size)
				{
					spawnAsEntity(world, pos, new ItemStack(ItemsAether.ginger_bread_man));
				}
			}
		}
		else
		{
			EntityTNTPresent present = new EntityTNTPresent(world, pos.getX(), pos.getY(), pos.getZ());
			
			if (!world.isRemote)
			{
				world.spawnEntity(present);
			}

			world.playSound(null, pos, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
    }

}