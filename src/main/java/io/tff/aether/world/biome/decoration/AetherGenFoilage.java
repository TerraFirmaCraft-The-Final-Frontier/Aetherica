package io.tff.aether.world.biome.decoration;

import java.util.Random;

import io.tff.aether.blocks.natural.BlockAetherFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class AetherGenFoilage extends WorldGenerator
{

    private IBlockState plantState;

    public AetherGenFoilage()
    {
    	super();
    }

    public void setPlantBlock(IBlockState state)
    {
    	this.plantState = state;
    }

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
        for(int l = 0; l < 64; l++)
        {
            int i1 = (pos.getX() + random.nextInt(8)) - random.nextInt(8);
            int j1 = (pos.getY() + random.nextInt(4)) - random.nextInt(4);
            int k1 = (pos.getZ() + random.nextInt(8)) - random.nextInt(8);

            BlockPos newPos = new BlockPos(i1, j1, k1);

            if(world.isAirBlock(newPos) && ((BlockAetherFlower)this.plantState.getBlock()).canBlockStay(world, newPos, this.plantState))
            {
            	this.setBlockAndNotifyAdequately(world, newPos, this.plantState);
            }
        }

		return true;
	}
}