package io.tff.aether.entities.util;

import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraftforge.registries.IForgeRegistry;

import io.tff.aether.Aether;
import io.tff.aether.api.moa.AetherMoaType;
import io.tff.aether.api.moa.MoaProperties;

public class AetherMoaTypes
{

	public static IForgeRegistry<AetherMoaType> moaRegistry;

	public static AetherMoaType blue, orange, white, black;

	public static void initialization()
	{
		blue = register("blue", 0x7777FF, new MoaProperties(3, 0.3F));
		orange = register("orange", -0xC3D78, new MoaProperties(2, 0.6F));
		white = register("white", 0xFFFFFF, new MoaProperties(4, 0.3F));
		black = register("black", 0x222222, new MoaProperties(8, 0.3F));
	}

	public static AetherMoaType register(String name, int hexColor, MoaProperties properties)
	{
		AetherMoaType moaType = new AetherMoaType(hexColor, properties, AetherCreativeTabs.misc);

		moaRegistry.register(moaType.setRegistryName(Aether.locate(name)));

		return moaType;
	}

}