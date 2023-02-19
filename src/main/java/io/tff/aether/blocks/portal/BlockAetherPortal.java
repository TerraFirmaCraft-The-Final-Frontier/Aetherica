package io.tff.aether.blocks.portal;

import java.util.Random;

import io.tff.aether.entities.particles.ParticleAetherPortal;
import io.tff.aether.registry.sounds.SoundsAether;
import com.google.common.cache.LoadingCache;
import io.tff.aether.AetherConfig;
import io.tff.aether.api.AetherAPI;
import io.tff.aether.world.TeleporterAether;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAetherPortal extends BlockPortal
{

	public BlockAetherPortal() 
	{
		super();

		this.setHardness(-1);
		this.setResistance(900000F);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		int previousDimension = entity.dimension;
		int transferDimension = previousDimension == AetherConfig.dimension.aether_dimension_id ? 0 : AetherConfig.dimension.aether_dimension_id;

		if (entity instanceof EntityPlayer && !entity.isRiding())
		{
			AetherAPI.getInstance().get((EntityPlayer)entity).setInPortal();
		}
		else if (!entity.isRiding() && !entity.isBeingRidden() && entity.isNonBoss())
		{	
			if (entity.timeUntilPortal > 0)
	        {
				entity.timeUntilPortal = entity.getPortalCooldown();
	        }
			else if (!world.isRemote)
	        {
				entity.timeUntilPortal = 300;
				entity.changeDimension(transferDimension, new TeleporterAether(true, server.getWorld(transferDimension)));
	        }
		}
	}

	@Override
	public boolean trySpawnPortal(World worldIn, BlockPos pos)
	{
        if (AetherConfig.gameplay_changes.disable_portal)
        {
            return false;
        }

		AetherPortalSize aetherportal$size = new AetherPortalSize(worldIn, pos, EnumFacing.Axis.X);

        if (aetherportal$size.isValid() && aetherportal$size.portalBlockCount == 0)
        {
        	aetherportal$size.placePortalBlocks();

            return true;
        }
        else
        {
        	AetherPortalSize aetherportal$size1 = new AetherPortalSize(worldIn, pos, EnumFacing.Axis.Z);

            if (aetherportal$size1.isValid() && aetherportal$size1.portalBlockCount == 0)
            {
            	aetherportal$size1.placePortalBlocks();

                return true;
            }
            else
            {
                return false;
            }
        }
	}

	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)state.getValue(AXIS);

        if (enumfacing$axis == EnumFacing.Axis.X)
        {
        	AetherPortalSize blockportal$size = new AetherPortalSize(worldIn, pos, EnumFacing.Axis.X);

            if (!blockportal$size.isValid() || blockportal$size.portalBlockCount < blockportal$size.width * blockportal$size.height)
            {
            	worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
        else if (enumfacing$axis == EnumFacing.Axis.Z)
        {
            AetherPortalSize blockportal$size1 = new AetherPortalSize(worldIn, pos, EnumFacing.Axis.Z);

            if (!blockportal$size1.isValid() || blockportal$size1.portalBlockCount < blockportal$size1.width * blockportal$size1.height)
            {
            	worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
        if (rand.nextInt(100) == 0)
        {
        	world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundsAether.aether_portal_idling, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i)
        {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)((float)pos.getY() + rand.nextFloat());
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
            double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this)
            {
                d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                d3 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }
            else
            {
                d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
                d5 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }

            ParticleAetherPortal particle = new ParticleAetherPortal(world, d0, d1, d2, d3, d4, d5);
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
        }
	}

	@Override
    public BlockPattern.PatternHelper createPatternHelper(World worldIn, BlockPos p_181089_2_)
    {
        EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Z;
        AetherPortalSize blockportal$size = new AetherPortalSize(worldIn, p_181089_2_, EnumFacing.Axis.X);
        LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.createLoadingCache(worldIn, true);

        if (!blockportal$size.isValid())
        {
            enumfacing$axis = EnumFacing.Axis.X;
            blockportal$size = new AetherPortalSize(worldIn, p_181089_2_, EnumFacing.Axis.Z);
        }

        if (!blockportal$size.isValid())
        {
            return new BlockPattern.PatternHelper(p_181089_2_, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
        }
        else
        {
            int[] aint = new int[EnumFacing.AxisDirection.values().length];
            EnumFacing enumfacing = blockportal$size.rightDir.rotateYCCW();
            BlockPos blockpos = blockportal$size.bottomLeft.up(blockportal$size.getHeight() - 1);

            for (EnumFacing.AxisDirection enumfacing$axisdirection : EnumFacing.AxisDirection.values())
            {
                BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);

                for (int i = 0; i < blockportal$size.getWidth(); ++i)
                {
                    for (int j = 0; j < blockportal$size.getHeight(); ++j)
                    {
                        BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, j, 1);

                        if (blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getMaterial() != Material.AIR)
                        {
                            ++aint[enumfacing$axisdirection.ordinal()];
                        }
                    }
                }
            }

            EnumFacing.AxisDirection enumfacing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;

            for (EnumFacing.AxisDirection enumfacing$axisdirection2 : EnumFacing.AxisDirection.values())
            {
                if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()])
                {
                    enumfacing$axisdirection1 = enumfacing$axisdirection2;
                }
            }

            return new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection1 ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection1, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);
        }
    }

	@Override
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random)
	{
	}

}