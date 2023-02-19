package io.tff.aether.registry;

import io.tff.aether.dictionary.AetherDictionary;
import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import io.tff.aether.registry.sounds.SoundsAether;
import io.tff.aether.api.accessories.RecipeAccessoryDyes;
import io.tff.aether.entities.effects.PotionsAether;
import io.tff.aether.world.biome.BiomesAether;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import io.tff.aether.Aether;
import io.tff.aether.api.accessories.AetherAccessory;
import io.tff.aether.api.enchantments.AetherEnchantment;
import io.tff.aether.api.enchantments.AetherEnchantmentFuel;
import io.tff.aether.api.freezables.AetherFreezable;
import io.tff.aether.api.freezables.AetherFreezableFuel;
import io.tff.aether.api.moa.AetherMoaType;
import io.tff.aether.blocks.BlocksAether;
import io.tff.aether.entities.util.AetherMoaTypes;
import io.tff.aether.items.ItemsAether;
import io.tff.aether.world.AetherWorld;

public class AetherRegistryEvent 
{

	@SubscribeEvent
	public void onRegisterBlockEvent(RegistryEvent.Register<Block> event)
	{
		BlocksAether.registerBlocks(event.getRegistry());
	}

	@SubscribeEvent
	public void onRegisterItemEvent(RegistryEvent.Register<Item> event)
	{
		BlocksAether.registerItems(event.getRegistry());

		ItemsAether.itemRegistry = event.getRegistry();

		ItemsAether.initialization();
		AetherCreativeTabs.initialization();
	}

	@SubscribeEvent
	public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event)
	{
		BiomesAether.biomeRegistry = event.getRegistry();

		BiomesAether.initialization();
	}

	@SubscribeEvent
	public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event)
	{
		SoundsAether.soundRegistry = event.getRegistry();

		SoundsAether.initialization();
	}

	@SubscribeEvent
	public void onRegisterPotionEffect(RegistryEvent.Register<Potion> event)
	{
		PotionsAether.potionRegistry = event.getRegistry();

		PotionsAether.initialization();
	}

	@SubscribeEvent
	public void onRegisterCraftingEvent(RegistryEvent.Register<IRecipe> event)
	{
		AetherDictionary.initialization();

		event.getRegistry().register(new RecipeAccessoryDyes().setRegistryName("aether_dyed_gloves"));
	}

	@SubscribeEvent
	public void onRegisterMoaTypeEvent(RegistryEvent.Register<AetherMoaType> event)
	{
		AetherMoaTypes.moaRegistry = event.getRegistry();

		AetherMoaTypes.initialization();
	}

	@SubscribeEvent
	public void onRegisterAccessoryEvent(RegistryEvent.Register<AetherAccessory> event)
	{
		AetherRegistries.initializeAccessories(event.getRegistry());
	}


	@SubscribeEvent
	public void onRegisterEnchantmentEvent(RegistryEvent.Register<AetherEnchantment> event)
	{
		AetherRegistries.initializeEnchantments(event.getRegistry());
	}

	@SubscribeEvent
	public void onRegisterEnchantmentFuelEvent(RegistryEvent.Register<AetherEnchantmentFuel> event)
	{
		AetherRegistries.initializeEnchantmentFuel(event.getRegistry());
	}

	@SubscribeEvent
	public void onRegisterFreezableEvent(RegistryEvent.Register<AetherFreezable> event)
	{
		AetherRegistries.initializeFreezables(event.getRegistry());
	}

	@SubscribeEvent
	public void onRegisterFreezableFuelEvent(RegistryEvent.Register<AetherFreezableFuel> event)
	{
		AetherRegistries.initializeFreezableFuel(event.getRegistry());
	}

}