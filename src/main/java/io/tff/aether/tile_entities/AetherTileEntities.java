package io.tff.aether.tile_entities;

import io.tff.aether.Aether;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class AetherTileEntities 
{

	@SuppressWarnings("deprecation")
	public static void initialization()
	{
		GameRegistry.registerTileEntity(TileEntityEnchanter.class, Aether.modAddress() + "enchanter");
		GameRegistry.registerTileEntity(TileEntityFreezer.class, Aether.modAddress() + "freezer");
		GameRegistry.registerTileEntity(TileEntityIncubator.class, Aether.modAddress() + "incubator");
		GameRegistry.registerTileEntity(TileEntityTreasureChest.class, Aether.modAddress() + "treasure_chest");
		GameRegistry.registerTileEntity(TileEntityChestMimic.class, Aether.modAddress() + "chest_mimic");
		GameRegistry.registerTileEntity(TileEntitySkyrootBed.class, Aether.modAddress() + "skyroot_bed");
	}

}