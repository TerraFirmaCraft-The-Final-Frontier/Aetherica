package io.tff.aether.blocks.natural;

import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import io.tff.aether.AetherConfig;
import io.tff.aether.blocks.util.EnumCloudType;
import io.tff.aether.blocks.util.IAetherMeta;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAercloud extends Block implements IAetherMeta
{

	public static final PropertyEnum<EnumCloudType> cloud_type = PropertyEnum.create("aether_aercloud", EnumCloudType.class);

	public BlockAercloud()
	{
		super(Material.ICE);

		this.setHardness(0.2F);
		this.setSoundType(SoundType.CLOTH);
		this.setCreativeTab(AetherCreativeTabs.blocks);
		this.setDefaultState(this.blockState.getBaseState().withProperty(cloud_type, EnumCloudType.Cold));
	}

	public static int getHexColor(ItemStack stack)
	{
		if (stack.getMetadata() == 1)
		{
			if (AetherConfig.visual_options.updated_aercloud_colors)
			{
				return 0x71d2ff;
			}
			
			else
			{
				return 0xCCFFFF;
			}
		}
		
		else if (stack.getMetadata() == 2)
		{
			if (AetherConfig.visual_options.updated_aercloud_colors)
			{
				return 0xffe082;
			}
			
			else
			{
				return 0xFFFF80;
			}
		}

		return 16777215; //Default color
	}

	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
	{
    	entity.fallDistance = 0;

    	if (((EnumCloudType)state.getValue(cloud_type)).equals(EnumCloudType.Blue))
    	{
			if (entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;

				//player.addStat(AchievementsAether.blue_cloud);

				if (player.isSneaking())
				{
		    		if (entity.motionY < 0)
		    		{
		    			entity.motionY *= 0.005D;
		    		}

		    		return;
				}

				entity.motionY = 2.0D;
			}
			else
			{
				if (entity instanceof EntityArrow)
				{
					if (entity.ticksExisted >= 1200)
					{
						entity.setDead();
					}
				}

				entity.motionY = 2.0D;
			}

			if (world.isRemote)
			{
				for (int count = 0; count < 50; count++)
				{
					double xOffset = pos.getX() + world.rand.nextDouble();
					double yOffset = pos.getY() + world.rand.nextDouble();
					double zOffset = pos.getZ() + world.rand.nextDouble();

					world.spawnParticle(EnumParticleTypes.WATER_SPLASH, xOffset, yOffset, zOffset, 0, 0, 0);
				}
			}
    	}
    	else
    	{
    		if (((EnumCloudType)state.getValue(cloud_type)).equals(EnumCloudType.Pink))
    		{
    			if (entity.ticksExisted % 20 == 0 && entity instanceof EntityLivingBase)
    			{
    				((EntityLivingBase)entity).heal(1.0F);
    			}
    		}

    		if (entity.motionY < 0)
    		{
    			entity.motionY *= 0.005D;
    		}
    	}
	}

	@Override
	public String getMetaName(ItemStack stack)
	{
		return ((EnumCloudType)this.getStateFromMeta(stack.getItemDamage()).getValue(cloud_type)).getName();
	}

	@Override
    public int damageDropped(IBlockState state)
    {
        return ((EnumCloudType)state.getValue(cloud_type)).getMeta();
    }

	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (int j = 0; j < EnumCloudType.values().length; ++j)
        {
        	EnumCloudType cloud_type = EnumCloudType.values()[j];

			list.add(new ItemStack(this, 1, cloud_type.getMeta()));
        }
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(cloud_type, EnumCloudType.getType(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumCloudType)state.getValue(cloud_type)).getMeta();
    }

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {cloud_type});
    }

	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (worldIn.getBlockState(pos.offset(side)) != iblockstate)
        {
            return true;
        }

        if (block == this)
        {
            return false;
        }

        return !worldIn.getBlockState(pos.offset(side)).doesSideBlockRendering(worldIn, pos.offset(side), side.getOpposite());
    }

	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return ((EnumCloudType)blockState.getValue(cloud_type)).getMeta() != 1 ? new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.01D, 1.0D) : NULL_AABB;
	}

}