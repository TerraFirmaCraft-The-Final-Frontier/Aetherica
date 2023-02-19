package io.tff.aether.blocks.dungeon;

import io.tff.aether.entities.bosses.EntityFireMinion;
import io.tff.aether.entities.bosses.EntityValkyrie;
import io.tff.aether.entities.hostile.EntitySentry;
import io.tff.aether.registry.sounds.SoundsAether;
import io.tff.aether.blocks.BlocksAether;
import io.tff.aether.blocks.util.EnumStoneType;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDungeonTrap extends BlockDungeonBase
{

	public BlockDungeonTrap()
	{
		super(true);
		this.setBlockUnbreakable();
	}

	@Override
    public void onEntityWalk(World world, BlockPos pos, Entity entityIn)
    {
		IBlockState state = world.getBlockState(pos);
    	EnumStoneType type = (EnumStoneType) state.getValue(dungeon_stone);

    	if (entityIn instanceof EntityPlayer)
    	{
    		Block block = world.getBlockState(pos).getBlock();
 
        	world.setBlockState(pos, BlocksAether.dungeon_block.getDefaultState().withProperty(dungeon_stone, EnumStoneType.getType(block.getMetaFromState(state))));

        	if (type == EnumStoneType.Carved || type == EnumStoneType.Sentry)
        	{
        		EntitySentry sentry = new EntitySentry(world, pos.getX() + 0.5F, pos.getY() + 1D, pos.getZ() + 0.5F);
        		if (!world.isRemote)
        		world.spawnEntity(sentry);
        	}
        	else if (type == EnumStoneType.Angelic || type == EnumStoneType.Light_angelic)
        	{
        		EntityValkyrie valkyrie = new EntityValkyrie(world);
        		valkyrie.setPosition(pos.getX() + 0.5D, pos.getY() + 1D, pos.getZ() + 0.5D);
        		if (!world.isRemote)
        		world.spawnEntity(valkyrie);
        	}
        	else if (type == EnumStoneType.Hellfire || type == EnumStoneType.Light_hellfire)
        	{
        		EntityFireMinion minion = new EntityFireMinion(world);
        		minion.setPosition(pos.getX() + 0.5D, pos.getY() + 1D, pos.getZ() + 0.5D);
        		if (!world.isRemote)
        		world.spawnEntity(minion);
        	}

        	world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundsAether.dungeon_trap, SoundCategory.BLOCKS, 1.0F, 1.5F);
    	}
    }

}