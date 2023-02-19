package io.tff.aether.blocks.decorative;

import java.util.Random;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockQuicksoilGlass extends BlockBreakable
{

	public BlockQuicksoilGlass()
	{
		super(Material.GLASS, false);

		this.setDefaultSlipperiness(1.1F);
		this.setLightLevel(0.7375F);
		this.setHardness(0.2F);
		this.setLightOpacity(0);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
    protected boolean canSilkHarvest()
    {
        return true;
    }

}