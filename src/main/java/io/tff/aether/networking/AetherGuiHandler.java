package io.tff.aether.networking;

import io.tff.aether.tile_entities.TileEntityEnchanter;
import io.tff.aether.tile_entities.TileEntityFreezer;
import io.tff.aether.tile_entities.TileEntityIncubator;
import io.tff.aether.tile_entities.TileEntityTreasureChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import io.tff.aether.api.AetherAPI;
import io.tff.aether.client.gui.GuiEnchanter;
import io.tff.aether.client.gui.GuiFreezer;
import io.tff.aether.client.gui.GuiIncubator;
import io.tff.aether.client.gui.GuiLore;
import io.tff.aether.client.gui.GuiTreasureChest;
import io.tff.aether.client.gui.inventory.GuiAccessories;
import io.tff.aether.containers.ContainerAccessories;
import io.tff.aether.containers.ContainerEnchanter;
import io.tff.aether.containers.ContainerFreezer;
import io.tff.aether.containers.ContainerIncubator;
import io.tff.aether.containers.ContainerLore;

public class AetherGuiHandler implements IGuiHandler
{

	public static final int accessories = 1, enchanter = 2, freezer = 3, incubator = 4, treasure_chest = 5, lore = 6;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == accessories)
		{
			return new ContainerAccessories(AetherAPI.getInstance().get(player).getAccessoryInventory(), player);
		}
		else if (ID == enchanter)
		{
			return new ContainerEnchanter(player.inventory, (TileEntityEnchanter) world.getTileEntity(new BlockPos(x, y, z)));
		}
		else if (ID == freezer)
		{
			return new ContainerFreezer(player.inventory, (TileEntityFreezer) world.getTileEntity(new BlockPos(x, y, z)));
		}
		else if (ID == incubator)
		{
			return new ContainerIncubator(player, player.inventory, (TileEntityIncubator) world.getTileEntity(new BlockPos(x, y, z)));
		}
		else if (ID == treasure_chest)
		{
			return new ContainerChest(player.inventory, (IInventory) world.getTileEntity(new BlockPos(x, y, z)), player);
		}
		else if (ID == lore)
		{
			return new ContainerLore(player.inventory);
		}

		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == accessories)
		{
			return new GuiAccessories(AetherAPI.getInstance().get(player));
		}
		else if (ID == enchanter)
		{
			return new GuiEnchanter(player.inventory, (TileEntityEnchanter) world.getTileEntity(new BlockPos(x, y, z)));
		}
		else if (ID == freezer)
		{
			return new GuiFreezer(player.inventory, (TileEntityFreezer) world.getTileEntity(new BlockPos(x, y, z)));
		}
		else if (ID == incubator)
		{
			return new GuiIncubator(player, player.inventory, (TileEntityIncubator) world.getTileEntity(new BlockPos(x, y, z)));
		}
		else if (ID == treasure_chest)
		{
			return new GuiTreasureChest(player.inventory, (TileEntityTreasureChest) world.getTileEntity(new BlockPos(x, y, z)));
		}
		else if (ID == lore)
		{
			return new GuiLore(player.inventory);
		}

		return null;
	}

}