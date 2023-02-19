package io.tff.aether.client.renders;

import io.tff.aether.client.renders.entities.*;
import io.tff.aether.client.renders.entities.layer.AccessoriesLayer;
import io.tff.aether.entities.block.EntityFloatingBlock;
import io.tff.aether.entities.block.EntityTNTPresent;
import io.tff.aether.entities.bosses.EntityFireMinion;
import io.tff.aether.entities.bosses.EntityValkyrie;
import io.tff.aether.entities.bosses.slider.EntitySlider;
import io.tff.aether.entities.bosses.sun_spirit.EntitySunSpirit;
import io.tff.aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import io.tff.aether.entities.passive.EntityAerwhale;
import io.tff.aether.entities.passive.EntityMiniCloud;
import io.tff.aether.entities.passive.EntitySheepuff;
import io.tff.aether.entities.projectile.EntityHammerProjectile;
import io.tff.aether.entities.projectile.EntityLightningKnife;
import io.tff.aether.entities.projectile.EntityPhoenixArrow;
import io.tff.aether.entities.projectile.EntityZephyrSnowball;
import io.tff.aether.entities.projectile.crystals.EntityFireBall;
import io.tff.aether.entities.projectile.crystals.EntityIceyBall;
import io.tff.aether.entities.projectile.crystals.EntityThunderBall;
import io.tff.aether.entities.projectile.darts.EntityDartBase;
import io.tff.aether.tile_entities.TileEntityChestMimic;
import io.tff.aether.tile_entities.TileEntityTreasureChest;
import io.tff.aether.client.renders.entities.*;
import io.tff.aether.client.renders.entities.layer.LayerElytraAether;
import io.tff.aether.tile_entities.TileEntitySkyrootBed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import io.tff.aether.blocks.BlocksAether;
import io.tff.aether.client.renders.entities.projectile.DartBaseRenderer;
import io.tff.aether.client.renders.entities.projectile.FireBallRenderer;
import io.tff.aether.client.renders.entities.projectile.HammerProjectileRenderer;
import io.tff.aether.client.renders.entities.projectile.IceyBallRenderer;
import io.tff.aether.client.renders.entities.projectile.LightningKnifeRenderer;
import io.tff.aether.client.renders.entities.projectile.PhoenixArrowRenderer;
import io.tff.aether.client.renders.entities.projectile.ThunderBallRenderer;
import io.tff.aether.client.renders.entities.projectile.ZephyrSnowballRenderer;
import io.tff.aether.entities.hostile.EntityAechorPlant;
import io.tff.aether.entities.hostile.EntityCockatrice;
import io.tff.aether.entities.hostile.EntityMimic;
import io.tff.aether.entities.hostile.EntitySentry;
import io.tff.aether.entities.hostile.EntityWhirlwind;
import io.tff.aether.entities.hostile.EntityZephyr;
import io.tff.aether.entities.passive.mountable.EntityAerbunny;
import io.tff.aether.entities.passive.mountable.EntityFlyingCow;
import io.tff.aether.entities.passive.mountable.EntityMoa;
import io.tff.aether.entities.passive.mountable.EntityParachute;
import io.tff.aether.entities.passive.mountable.EntityPhyg;
import io.tff.aether.entities.passive.mountable.EntitySwet;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.HashSet;
import java.util.List;

public class AetherEntityRenderingRegistry 
{

	public static void initialize()
	{
		/* Misc */
		register(EntityHammerProjectile.class, HammerProjectileRenderer.class);
		register(EntityFloatingBlock.class, FloatingBlockRenderer.class);
		register(EntityParachute.class, ParachuteRenderer.class);
		register(EntityZephyrSnowball.class, ZephyrSnowballRenderer.class);
		register(EntityPhoenixArrow.class, PhoenixArrowRenderer.class);
		register(EntityLightningKnife.class, LightningKnifeRenderer.class);

		/* Darts */
		register(EntityDartBase.class, DartBaseRenderer.class);

		/* Crystals */
		register(EntityFireBall.class, FireBallRenderer.class);
		register(EntityIceyBall.class, IceyBallRenderer.class);
		register(EntityThunderBall.class, ThunderBallRenderer.class);

		/* Bosses */
		register(EntitySlider.class, SliderRenderer.class);
		register(EntityValkyrieQueen.class, ValkyrieQueenRenderer.class);
		register(EntitySunSpirit.class, SunSpiritRenderer.class);

		/* Hostile */
		register(EntityMimic.class, MimicRenderer.class);
		register(EntitySentry.class, SentryRenderer.class);
		register(EntityAechorPlant.class, AechorPlantRenderer.class);
		register(EntityFireMinion.class, FireMinionRenderer.class);
		register(EntityZephyr.class, ZephyrRenderer.class);
		register(EntityValkyrie.class, ValkyrieRenderer.class);
		register(EntityCockatrice.class, CockatriceRenderer.class);

		/* Passive */
		register(EntityMoa.class, MoaRenderer.class);
		register(EntityPhyg.class, PhygRenderer.class);
		register(EntityFlyingCow.class, FlyingCowRenderer.class);
		register(EntitySheepuff.class, SheepuffRenderer.class);
		register(EntityAerwhale.class, AerwhaleRenderer.class);
		register(EntityAerbunny.class, AerbunnyRenderer.class);
		register(EntitySwet.class, SwetRenderer.class);
		register(EntityMiniCloud.class, MiniCloudRenderer.class);
		register(EntityTNTPresent.class, TNTPresentRenderer.class);
		register(EntityWhirlwind.class, WhirlwindRenderer.class);
	}

	@SuppressWarnings("deprecation")
	public static void registerTileEntities()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTreasureChest.class, new TreasureChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChestMimic.class, new ChestMimicRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkyrootBed.class, new SkyrootBedRenderer());

		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlocksAether.treasure_chest), 0, TileEntityTreasureChest.class);
		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlocksAether.chest_mimic), 0, TileEntityChestMimic.class);
	}

	public static void initializePlayerLayers()
	{
		for (RenderLivingBase<?> playerRender : new HashSet<>(Minecraft.getMinecraft().getRenderManager().getSkinMap().values()))
		{
			playerRender.addLayer(new AccessoriesLayer((ModelPlayer) playerRender.getMainModel()));
			playerRender.addLayer(new LayerElytraAether(playerRender));

			List<LayerRenderer<EntityLivingBase>> fieldOutside = ObfuscationReflectionHelper.getPrivateValue(RenderLivingBase.class, playerRender, "layerRenderers", "field_177097_h");
			fieldOutside.removeIf(layerRenderer -> layerRenderer.getClass() == LayerElytra.class);
		}
	}

	public static <ENTITY extends Entity> void register(Class<ENTITY> classes, final Class<? extends Render<ENTITY>> render)
	{
		RenderingRegistry.registerEntityRenderingHandler(classes, new IRenderFactory<ENTITY>() {

			@Override
			public Render<ENTITY> createRenderFor(RenderManager manager) 
			{
				try
				{
					return render.getConstructor(RenderManager.class).newInstance(manager);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				return null;
			}
		});
	}

}