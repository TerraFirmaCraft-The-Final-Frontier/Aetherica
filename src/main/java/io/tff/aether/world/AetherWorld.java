package io.tff.aether.world;

import io.tff.aether.blocks.BlocksAether;
import io.tff.aether.entities.util.PlayerStartupLoot;
import io.tff.aether.world.biome.AetherBiome;
import io.tff.aether.world.gen.MapGenGoldenDungeon;
import io.tff.aether.world.gen.MapGenLargeColdAercloud;
import io.tff.aether.world.gen.MapGenSilverDungeon;
import io.tff.aether.AetherConfig;
import io.tff.aether.world.gen.components.ComponentGoldenDungeon;
import io.tff.aether.world.gen.components.ComponentGoldenIsland;
import io.tff.aether.world.gen.components.ComponentGoldenIslandStub;
import io.tff.aether.world.gen.components.ComponentLargeColdAercloud;
import io.tff.aether.world.gen.components.ComponentSilverDungeon;

import net.minecraft.block.Block;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.storage.loot.properties.EntityPropertyManager;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AetherWorld
{
	public static Biome aether_biome = new AetherBiome();

	public static DimensionType aether_dimension_type;

	public static List<Block> viableSpawningBlocks = new ArrayList<>(Collections.singletonList(BlocksAether.aether_grass));
	public static List<Block> viableGrassBlocks = new ArrayList<>(Arrays.asList(BlocksAether.aether_grass, BlocksAether.enchanted_aether_grass));
	public static List<Block> viableSoilBlocks = new ArrayList<>(Arrays.asList(BlocksAether.aether_grass, BlocksAether.enchanted_aether_grass, BlocksAether.aether_dirt));

	public static void initialization()
	{
		MapGenStructureIO.registerStructure(MapGenSilverDungeon.Start.class, "aether_legacy:silver_dungeon_start");
		MapGenStructureIO.registerStructure(MapGenGoldenDungeon.Start.class, "aether_legacy:golden_dungeon_start");

		MapGenStructureIO.registerStructure(MapGenLargeColdAercloud.Start.class, "aether_legacy:large_cold_aercloud_start");

		MapGenStructureIO.registerStructureComponent(ComponentLargeColdAercloud.class, "aether_legacy:large_cold_aercloud_component");

		MapGenStructureIO.registerStructureComponent(ComponentSilverDungeon.class, "aether_legacy:silver_dungeon_component");

		MapGenStructureIO.registerStructureComponent(ComponentGoldenDungeon.class, "aether_legacy:golden_dungeon_component");
		MapGenStructureIO.registerStructureComponent(ComponentGoldenIsland.class, "aether_legacy:golden_island_component");
		MapGenStructureIO.registerStructureComponent(ComponentGoldenIslandStub.class, "aether_legacy:golden_island_stub_component");

		aether_dimension_type = DimensionType.register("AetherI", "_aetherI", AetherConfig.dimension.aether_dimension_id, AetherWorldProvider.class, false);

		DimensionManager.registerDimension(AetherConfig.dimension.aether_dimension_id, aether_dimension_type);

		EntityPropertyManager.registerProperty(new PlayerStartupLoot.Serializer());
	}

}