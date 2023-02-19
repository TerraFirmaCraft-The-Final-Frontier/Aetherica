package io.tff.aether.blocks.natural;

import java.util.Random;

import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import io.tff.aether.Aether;
import io.tff.aether.blocks.BlocksAether;
import io.tff.aether.items.util.DoubleDropHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public class BlockAetherGrass extends Block implements IGrowable
{
	public static final PropertyBool double_drop = PropertyBool.create(Aether.doubleDropNotifier());

    public static final PropertyBool SNOWY = PropertyBool.create("snowy");

    public static final PropertyBool dungeon_block = PropertyBool.create("dungeon_block");

	public BlockAetherGrass()
	{
		super(Material.GRASS);

		this.setTickRandomly(true);
		this.setHardness(0.2F);
		this.setCreativeTab(AetherCreativeTabs.blocks);
		this.setSoundType(SoundType.PLANT);
		this.setDefaultState(this.getDefaultState().withProperty(double_drop, Boolean.TRUE).withProperty(SNOWY, Boolean.FALSE).withProperty(dungeon_block, Boolean.FALSE));
	}

	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return state.withProperty(SNOWY, block == Blocks.SNOW || block == Blocks.SNOW_LAYER);
    }

	@Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable)
    {
        EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));

        switch (plantType)
        {
        	case Plains: return true;
        	case Beach: 
                boolean hasWater = (world.getBlockState(pos.east()).getMaterial() == Material.WATER ||
                world.getBlockState(pos.west()).getMaterial() == Material.WATER ||
                world.getBlockState(pos.north()).getMaterial() == Material.WATER ||
                world.getBlockState(pos.south()).getMaterial() == Material.WATER);
                return hasWater;
		default:
			break;
        }

    	return super.canSustainPlant(state, world, pos, direction, plantable);
    }

	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		world.setBlockState(pos, state.withProperty(double_drop, Boolean.FALSE).withProperty(dungeon_block, Boolean.FALSE));
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
		int meta = 0;
		
		if (!state.getValue(double_drop))
		{
			meta |= 1;
		}

        if (!state.getValue(dungeon_block))
        {
            meta |= 2;
        }

		return meta;
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(double_drop, (meta & 1) == 0).withProperty(dungeon_block, (meta & 2) == 0);
    }

	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		DoubleDropHelper.dropBlock(player, state, pos, double_drop);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, double_drop, SNOWY, dungeon_block);
	}

	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (!world.isRemote)
        {
            if (world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getLightOpacity(world, pos) > 2)
            {
                boolean shouldContainDoubleDrop = ((boolean)state.getValue(BlockAetherDirt.double_drop));
            	world.setBlockState(pos, BlocksAether.aether_dirt.getDefaultState().withProperty(double_drop, shouldContainDoubleDrop));
            }
            else
            {
                if (world.getLightFromNeighbors(pos.up()) >= 9)
                {
                    for (int i = 0; i < 4; ++i)
                    {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
                        IBlockState iblockstate = world.getBlockState(blockpos.up());
                        IBlockState iblockstate1 = world.getBlockState(blockpos);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !world.isBlockLoaded(blockpos))
                        {
                            return;
                        }

                        if (iblockstate1.getBlock() == BlocksAether.aether_dirt && world.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(world, blockpos.up()) <= 2)
                        {
                            boolean shouldContainDoubleDrop = ((boolean)iblockstate1.getValue(BlockAetherDirt.double_drop));
                        	world.setBlockState(blockpos, BlocksAether.aether_grass.getDefaultState().withProperty(double_drop, shouldContainDoubleDrop));
                        	return;
                        }
                    }
                }
            }
        }
    }

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlocksAether.aether_dirt);
    }
	
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return true;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        BlockPos blockpos = pos.up();

        for (int i = 0; i < 128; ++i)
        {
            BlockPos blockpos1 = blockpos;
            int j = 0;

            while (true)
            {
                if (j >= i / 16)
                {
                    if (worldIn.isAirBlock(blockpos1))
                    {
                        if (rand.nextInt(8) == 0)
                        {
                        	if (rand.nextInt(2) == 0)
                        	{
                            	worldIn.setBlockState(blockpos1, BlocksAether.purple_flower.getDefaultState(), 0);

                        	}
                        	else
                        	{
                            	worldIn.setBlockState(blockpos1, BlocksAether.white_flower.getDefaultState(), 0);
                        	}
                        }
                        else
                        {
                            IBlockState iblockstate1 = Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);

                            if (Blocks.TALLGRASS.canBlockStay(worldIn, blockpos1, iblockstate1))
                            {
                                worldIn.setBlockState(blockpos1, iblockstate1, 3);
                            }
                        }
                    }

                    break;
                }

                blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

                if (worldIn.getBlockState(blockpos1.down()).getBlock() != BlocksAether.aether_grass || worldIn.getBlockState(blockpos1).isNormalCube())
                {
                    break;
                }

                ++j;
            }
        }
    }


}