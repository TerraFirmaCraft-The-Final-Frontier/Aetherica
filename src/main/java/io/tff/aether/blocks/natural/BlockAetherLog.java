package io.tff.aether.blocks.natural;

import io.tff.aether.items.tools.*;
import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import io.tff.aether.items.tools.*;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import io.tff.aether.Aether;
import io.tff.aether.blocks.BlocksAether;
import io.tff.aether.blocks.util.EnumLogType;
import io.tff.aether.blocks.util.IAetherMeta;
import io.tff.aether.items.ItemsAether;
import io.tff.aether.items.util.EnumAetherToolType;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public class BlockAetherLog extends BlockLog implements IAetherMeta
{

	public static final PropertyEnum<EnumLogType> wood_type = PropertyEnum.create("aether_logs", EnumLogType.class);

	public static final PropertyBool double_drop = PropertyBool.create(Aether.doubleDropNotifier());

	public BlockAetherLog()
	{
        super();

		this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
		this.setCreativeTab(AetherCreativeTabs.blocks);
		this.setDefaultState(this.getDefaultState().withProperty(wood_type, EnumLogType.Skyroot).withProperty(double_drop, Boolean.TRUE).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
	}

	@Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(wood_type, EnumLogType.getType(meta)).withProperty(double_drop, Boolean.FALSE).withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(facing.getAxis()));
    }

	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (worldIn.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5)))
        {
            for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4)))
            {
                IBlockState iblockstate = worldIn.getBlockState(blockpos);

                if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos))
                {
                    iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
                }
            }
        }
    }

	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stackIn)
	{
        player.addStat(StatList.getBlockStats(state.getBlock()));
        player.addExhaustion(0.025F);

        int size = state.getValue(double_drop).equals(true) ? 2 : 1;

        ItemStack stack = player.inventory.getCurrentItem();

		IBlockState defaults = BlocksAether.aether_log.getDefaultState();

		if (this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0)
        {
            java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();

            ItemStack itemstack = this.getSilkTouchDrop(state);

            if (state.getValue(wood_type) == EnumLogType.Oak)
            {
                Item item = Item.getItemFromBlock(this);

                itemstack =  new ItemStack(item, 1, EnumLogType.Oak.getMeta());
            }

            if (!itemstack.isEmpty())
            {
                items.add(itemstack);
            }

            net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
            for (ItemStack item : items)
            {
                spawnAsEntity(worldIn, pos, item);
            }
        }
		else
        {
            if (stack != null && stack.getItem() instanceof ItemAetherTool && ((ItemAetherTool)stack.getItem()).toolType == EnumAetherToolType.AXE)
            {
                if (stack.getItem() instanceof ItemZaniteTool || stack.getItem() instanceof ItemGravititeTool || stack.getItem() instanceof ItemValkyrieTool
                        || canItemHarvestAmber(stack))
                {
                    if (state.getValue(wood_type) == EnumLogType.Oak)
                    {
                        spawnAsEntity(worldIn, pos, new ItemStack(ItemsAether.golden_amber, 1 + RANDOM.nextInt(2)));
                    }

                    defaults.getBlock().dropBlockAsItem(worldIn, pos, defaults, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
                }
                else if (stack.getItem() instanceof ItemSkyrootTool)
                {
                    for (int i = 0; i < size; ++i)
                    {
                        defaults.getBlock().dropBlockAsItem(worldIn, pos, defaults, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
                    }
                }
                else
                {
                    defaults.getBlock().dropBlockAsItem(worldIn, pos, defaults, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
                }
            }
            else
            {
                spawnAsEntity(worldIn, pos, new ItemStack(defaults.getBlock()));
            }
        }
	}

	private boolean canItemHarvestAmber(ItemStack stack)
    {
        boolean flag = false;

        for (ItemStack itemStack : OreDictionary.getOres("canHarvestAetherAmber"))
        {
            if (itemStack.getItem() == stack.getItem())
            {
                flag = true;
            }
        }

        return flag;
    }

	@Override
	public String getMetaName(ItemStack stack) 
	{
		return ((EnumLogType)this.getStateFromMeta(stack.getItemDamage()).getValue(wood_type)).getName();
	}

	@Override
    public int damageDropped(IBlockState state)
    {
		return 0;
    }

	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
		IBlockState iblockstate = this.getDefaultState().withProperty(wood_type, EnumLogType.getType(meta)).withProperty(double_drop, Boolean.valueOf((meta % 4) < 2));

        switch (meta & 12)
        {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }

		return iblockstate;
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
		int meta = 0;
		meta = meta | ((EnumLogType)state.getValue(wood_type)).getMeta();

		if (!((Boolean)state.getValue(double_drop)).booleanValue())
		{
			meta |= 2;
		}

        switch ((BlockLog.EnumAxis)state.getValue(LOG_AXIS))
        {
        	case Y:
        		break;
            case X:
            	meta |= 4;
                break;
            case Z:
            	meta |= 8;
                break;
            case NONE:
            	meta |= 12;
        }

		return meta;
    }

	@Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {wood_type, double_drop, LOG_AXIS});
    }
	
	@Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
    }

}