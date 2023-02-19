package io.tff.aether;

import io.tff.aether.advancements.AetherAdvancements;
import io.tff.aether.api.player.IPlayerAether;
import io.tff.aether.api.player.IPlayerAetherStorage;
import io.tff.aether.blocks.BlocksAether;
import io.tff.aether.entities.AetherEntities;
import io.tff.aether.events.AetherEntityEvents;
import io.tff.aether.networking.AetherNetworkingManager;
import io.tff.aether.player.capability.PlayerAetherManager;
import io.tff.aether.registry.AetherRegistryEvent;
import io.tff.aether.registry.sounds.SoundsAether;
import io.tff.aether.tile_entities.AetherTileEntities;
import io.tff.aether.universal.crafttweaker.AetherCraftTweakerPlugin;
import io.tff.aether.universal.reskillable.ReskillableTickHandler;
import io.tff.aether.world.AetherWorld;
import io.tff.aether.world.biome.BiomeStorage;
import io.tff.aether.world.storage.loot.conditions.LootConditionsAether;
import io.tff.aether.world.storage.loot.functions.LootFunctionsAether;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = "Aether", modid = Aether.modid, version = Aether.version, acceptedMinecraftVersions = "1.12.2", updateJSON = "https://raw.githubusercontent.com/Modding-Legacy/Aether-Legacy/master/aether-legacy-changelog.json")
public class Aether 
{

	public static final String modid = "aether_legacy";

	public static final String version = "1.5.3.2";

	@Instance(Aether.modid)
	public static Aether instance;

	@SidedProxy(modId = Aether.modid, clientSide = "io.tff.aether.client.ClientProxy", serverSide = "io.tff.aether.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInitialization(FMLPreInitializationEvent event)
	{
		CapabilityManager.INSTANCE.register(IPlayerAether.class, new IPlayerAetherStorage(), () -> null);

		BlocksAether.initialization();
		BlocksAether.initializeHarvestLevels();
		SoundsAether.initialization();
		LootConditionsAether.initialization();
		LootFunctionsAether.initialization();
		AetherAdvancements.initialization();
		AetherNetworkingManager.preInitialization();

		if(Loader.isModLoaded("crafttweaker"))
		{
			AetherCraftTweakerPlugin.preInitialization();
		}

		CommonProxy.registerEvent(new AetherRegistryEvent());

		proxy.preInitialization();
	}

	@EventHandler
	public void initialization(FMLInitializationEvent event)
	{
		PlayerAetherManager.initialization();
		AetherEntities.initialization();
		AetherTileEntities.initialization();
		BiomeStorage.handleBiomeConfig();
		AetherWorld.initialization();

		CommonProxy.registerEvent(new AetherEventHandler());
		CommonProxy.registerEvent(new AetherEntityEvents());

		if(Loader.isModLoaded("reskillable"))
		{
			CommonProxy.registerEvent(new ReskillableTickHandler());
		}

		proxy.initialization();
	}

	@EventHandler
	public void postInitialization(FMLPostInitializationEvent event)
	{
		proxy.postInitialization();

		FurnaceRecipes.instance().addSmeltingRecipeForBlock(BlocksAether.aether_log, new ItemStack(Items.COAL, 1, 1), 0.15F);
	}

	public static ResourceLocation locate(String location)
	{
		return new ResourceLocation(modid, location);
	}

	public static String modAddress()
	{
		return modid + ":";
	}

	public static String doubleDropNotifier()
	{
		return modid + "_double_drops";
	}

}