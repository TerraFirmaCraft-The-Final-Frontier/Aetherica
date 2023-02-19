package io.tff.aether.client;

import java.util.List;

import io.tff.aether.client.gui.AetherLoadingScreen;
import io.tff.aether.client.gui.GuiEnterAether;
import io.tff.aether.client.gui.button.*;
import io.tff.aether.client.gui.inventory.GuiAccessories;
import io.tff.aether.client.gui.menu.AetherMainMenu;
import io.tff.aether.client.gui.menu.GuiMenuToggleButton;
import io.tff.aether.containers.inventory.InventoryAccessories;
import io.tff.aether.networking.AetherGuiHandler;
import io.tff.aether.networking.AetherNetworkingManager;
import io.tff.aether.networking.packets.*;
import io.tff.aether.player.PlayerAether;
import io.tff.aether.player.perks.AetherRankings;
import io.tff.aether.player.perks.util.EnumAetherPerkType;
import io.tff.aether.registry.sounds.SoundsAether;
import io.tff.aether.universal.fastcrafting.FastCraftingUtil;
import io.tff.aether.universal.pixelmon.PixelmonUtil;
import io.tff.aether.AetherConfig;
import io.tff.aether.api.AetherAPI;
import io.tff.aether.api.player.IPlayerAether;
import io.tff.aether.client.gui.button.*;
import io.tff.aether.items.ItemsAether;
import io.tff.aether.networking.packets.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AetherClientEvents
{

	private final Minecraft mc = FMLClientHandler.instance().getClient();

	private static boolean wasInAether = false;

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		TickEvent.Phase phase = event.phase;
		TickEvent.Type type = event.type;

		if (phase == TickEvent.Phase.END)
		{
			if (type.equals(TickEvent.Type.CLIENT))
			{
				if (!AetherConfig.visual_options.trivia_disabled)
				{
					if (!(mc.loadingScreen instanceof AetherLoadingScreen))
					{
						mc.loadingScreen = new AetherLoadingScreen(mc);
					}
				}
			}
		}

		if (mc.player != null)
		{
			if (AetherAPI.getInstance().get(mc.player).inPortalBlock())
			{
				if (this.mc.currentScreen instanceof GuiContainer)
				{
					this.mc.player.closeScreen();
				}
			}

			if (AetherAPI.getInstance().get(mc.player).shouldPortalSound())
			{
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundsAether.aether_portal_trigger,this.mc.world.rand.nextFloat() * 0.4F + 0.8F));

				AetherAPI.getInstance().get(mc.player).shouldPortalSound(false);
			}

			if (AetherAPI.getInstance().get(mc.player).shouldPortalTravelSound())
			{
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundsAether.aether_portal_travel, this.mc.world.rand.nextFloat() * 0.4F + 0.8F));

				AetherAPI.getInstance().get(mc.player).shouldPortalTravelSound(false);
				AetherNetworkingManager.sendToServer(new PacketShouldPortalTravelSound(mc.player, false));
			}
		}

	}

	@SubscribeEvent
	public void onUpdateJump(InputUpdateEvent event)
	{
		IPlayerAether playerAether = AetherAPI.getInstance().get(event.getEntityPlayer());
		boolean isJumping = event.getMovementInput().jump;

		if (isJumping != playerAether.isJumping())
		{
			AetherNetworkingManager.sendToServer(new PacketSendJump(event.getEntityPlayer().getUniqueID(), isJumping));
			playerAether.setJumping(isJumping);
		}
	}

	@SubscribeEvent
	public void onBowPulled(FOVUpdateEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;

		if (player == null || (player != null && player.getActiveItemStack() == null))
		{
			return;
		}

		Item item = player.getActiveItemStack().getItem();

		if (item == ItemsAether.phoenix_bow)
		{
			int i = player.getItemInUseMaxCount();
			float f1 = (float)i / 20.0F;

			if (f1 > 1.0F)
			{
				f1 = 1.0F;
			}
			else
			{
				f1 = f1 * f1;
			}

			float original = event.getFov();

			original *= 1.0F - f1 * 0.15F;

			event.setNewfov(original);
		}
	}

	private static final GuiAccessoryButton ACCESSORY_BUTTON = new GuiAccessoryButton(0, 0);

	private static final GuiMenuToggleButton MAIN_MENU_BUTTON = new GuiMenuToggleButton(0, 0);

	private static int previousSelectedTabIndex = -1;

	private static boolean shouldRemoveButton = false;

	@SubscribeEvent
	public void onScreenOpened(GuiScreenEvent.InitGuiEvent.Post event)
	{
		if (event.getGui() instanceof GuiContainer)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			Class<?> clazz = event.getGui().getClass();

			int guiLeft = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiLeft", "field_147003_i");
			int guiTop = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiTop", "field_147009_r");

			if (player.capabilities.isCreativeMode)
			{
				if (event.getGui() instanceof GuiContainerCreative)
				{
					if (((GuiContainerCreative)event.getGui()).getSelectedTabIndex() == CreativeTabs.INVENTORY.getIndex())
					{
						event.getButtonList().add(ACCESSORY_BUTTON.setPosition(guiLeft + 73, guiTop + 38));
						previousSelectedTabIndex = CreativeTabs.INVENTORY.getIndex();
					}
				}
			}
			else if (clazz == GuiInventory.class || FastCraftingUtil.isOverridenGUI(clazz) || PixelmonUtil.isOverridenInventoryGUI(clazz))
			{
				event.getButtonList().add(ACCESSORY_BUTTON.setPosition(guiLeft + 26, guiTop + 65));
			}

			if (clazz == GuiAccessories.class)
			{
				if (!shouldRemoveButton)
				{
					event.getButtonList().add(ACCESSORY_BUTTON.setPosition(guiLeft + 8, guiTop + 65));
				}
				else
				{
					shouldRemoveButton = false;
				}
			}
		}

		if (AetherConfig.visual_options.menu_button && event.getGui() instanceof GuiMainMenu)
		{
			event.getButtonList().add(MAIN_MENU_BUTTON.setPosition(event.getGui().width - 24, 4));
		}

		if (AetherConfig.visual_options.menu_enabled && event.getGui().getClass() == GuiMainMenu.class)
		{
			Minecraft.getMinecraft().displayGuiScreen(new AetherMainMenu());
		}

		if (event.getGui().getClass() == GuiCustomizeSkin.class)
		{
			if (Minecraft.getMinecraft().player != null)
			{
				int i = 8;

				for (GuiButton button : event.getButtonList())
				{
					if (button.id == 200)
					{
						button.y = button.y + 48;
					}
				}

				event.getButtonList().add(new GuiGlovesButton(event.getGui().width / 2 - 155 + i % 2 * 160, event.getGui().height / 6 + 24 * (i >> 1)));
				i++;
				event.getButtonList().add(new GuiGloveSizeButton(event.getGui().width / 2 - 155 + i % 2 * 160, event.getGui().height / 6 + 24 * (i >> 1)));
				i++;
				event.getButtonList().add(new GuiCapeButton(event.getGui().width / 2 - 155 + i % 2 * 160, event.getGui().height / 6 + 24 * (i >> 1)));

				if (AetherRankings.isRankedPlayer(Minecraft.getMinecraft().player.getUniqueID()))
				{
					i++;
					event.getButtonList().add(new GuiHaloButton(event.getGui().width / 2 - 155 + i % 2 * 160, event.getGui().height / 6 + 24 * (i >> 1)));

					if (AetherRankings.isDeveloper(Minecraft.getMinecraft().player.getUniqueID()))
					{
						for (GuiButton button : event.getButtonList())
						{
							if (button.id == 200)
							{
								button.y = button.y + 24;
							}
						}

						i++;
						event.getButtonList().add(new GuiGlowButton(event.getGui().width / 2 - 155 + i % 2 * 160, event.getGui().height / 6 + 24 * (i >> 1)));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onOpenGui(GuiOpenEvent event)
	{
		if (mc.player != null)
		{
			if (event.getGui() instanceof GuiDownloadTerrain)
			{
				GuiEnterAether enterAether = new GuiEnterAether(true);
				GuiEnterAether exitAether = new GuiEnterAether(false);

				if (mc.player.dimension == AetherConfig.dimension.aether_dimension_id)
				{
					event.setGui(enterAether);
					wasInAether = true;
				}

				else if (wasInAether)
				{
					event.setGui(exitAether);
					wasInAether = false;
				}
			}
		}
	}

	private static boolean canOpenAccessories = true;

	@SubscribeEvent
	public void onDrawGui(GuiScreenEvent.DrawScreenEvent.Pre event)
	{
		if (!AetherConfig.visual_options.menu_enabled && event.getGui().getClass() == AetherMainMenu.class)
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
		}

		if (!canOpenAccessories && !ACCESSORY_BUTTON.isMouseOver())
		{
			canOpenAccessories = true;
		}
	}

	@SubscribeEvent
	public void onMouseClicked(MouseInputEvent.Post event)
	{
		if (event.getGui() instanceof GuiContainerCreative)
		{
			GuiContainerCreative guiScreen = (GuiContainerCreative) event.getGui();
			List<GuiButton> buttonList = ObfuscationReflectionHelper.getPrivateValue(GuiScreen.class, (GuiScreen) guiScreen, 7);

			if (previousSelectedTabIndex != guiScreen.getSelectedTabIndex())
			{
				if (guiScreen.getSelectedTabIndex() == CreativeTabs.INVENTORY.getIndex() && !buttonList.contains(ACCESSORY_BUTTON))
				{
					int guiLeft = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiLeft", "field_147003_i");
					int guiTop = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiTop", "field_147009_r");

					buttonList.add(ACCESSORY_BUTTON.setPosition(guiLeft + 73, guiTop + 38));
				}
				else if (previousSelectedTabIndex == CreativeTabs.INVENTORY.getIndex())
				{
					buttonList.remove(ACCESSORY_BUTTON);
				}

				previousSelectedTabIndex = guiScreen.getSelectedTabIndex();
			}
		}
	}

	@SubscribeEvent
	public void onButtonPressed(GuiScreenEvent.ActionPerformedEvent.Pre event)
	{
		Class<?> clazz = event.getGui().getClass();

		if (clazz == GuiInventory.class && event.getButton().getClass() == GuiButtonImage.class && event.getButton().id == 10)
		{
			int guiLeft = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiLeft", "field_147003_i");
			int guiTop = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiTop", "field_147009_r");

			if (mc.currentScreen != null)
			{
				ACCESSORY_BUTTON.setPosition(mc.currentScreen.width - guiLeft - 74, guiTop + 65);
				canOpenAccessories = false;
			}
		}

		if (clazz != GuiAccessories.class && event.getButton().id == 18067 && canOpenAccessories)
		{
			AetherNetworkingManager.sendToServer(new PacketOpenContainer(AetherGuiHandler.accessories));
		}

		if (event.getButton().getClass() == GuiHaloButton.class)
		{
			PlayerAether player = (PlayerAether) AetherAPI.getInstance().get(Minecraft.getMinecraft().player);

			boolean enableHalo = !player.shouldRenderHalo;

			player.shouldRenderHalo = enableHalo;
			AetherNetworkingManager.sendToServer(new PacketPerkChanged(player.getEntity().getEntityId(), EnumAetherPerkType.Halo, player.shouldRenderHalo));
		}

		if (event.getButton().getClass() == GuiGlowButton.class)
		{
			PlayerAether player = (PlayerAether) AetherAPI.getInstance().get(Minecraft.getMinecraft().player);

			boolean enableGlow = !player.shouldRenderGlow;

			player.shouldRenderGlow = enableGlow;
			AetherNetworkingManager.sendToServer(new PacketPerkChanged(player.getEntity().getEntityId(), EnumAetherPerkType.Glow, player.shouldRenderGlow));
		}

		if (event.getButton().getClass() == GuiCapeButton.class)
		{
			PlayerAether player = (PlayerAether) AetherAPI.getInstance().get(Minecraft.getMinecraft().player);

			boolean enableCape = !player.shouldRenderCape;

			player.shouldRenderCape = enableCape;
			AetherNetworkingManager.sendToServer(new PacketCapeChanged(player.getEntity().getEntityId(), player.shouldRenderCape));
		}

		if (event.getButton().getClass() == GuiGlovesButton.class)
		{
			PlayerAether player = (PlayerAether) AetherAPI.getInstance().get(Minecraft.getMinecraft().player);

			boolean enableGloves = !player.shouldRenderGloves;

			player.shouldRenderGloves = enableGloves;
			AetherNetworkingManager.sendToServer(new PacketGlovesChanged(player.getEntity().getEntityId(), player.shouldRenderGloves));
		}

		if (event.getButton().getClass() == GuiGloveSizeButton.class)
		{
			PlayerAether player = (PlayerAether) AetherAPI.getInstance().get(Minecraft.getMinecraft().player);

			boolean gloveSize = !player.gloveSize;

			player.gloveSize = gloveSize;
			AetherNetworkingManager.sendToServer(new PacketGloveSizeChanged(player.getEntity().getEntityId(), player.gloveSize));
		}
	}

	@SubscribeEvent
	public void onRenderHand(RenderSpecificHandEvent event)
	{
		if (((PlayerAether) AetherAPI.getInstance().get(Minecraft.getMinecraft().player)).shouldRenderGloves)
		{
			PlayerGloveRenderer.renderItemFirstPerson(Minecraft.getMinecraft().player, event.getPartialTicks(), event.getInterpolatedPitch(), event.getHand(), event.getSwingProgress(), event.getItemStack(), event.getEquipProgress());
		}
	}

	@SubscribeEvent
	public void onInvisibilityPlayerUpdate(RenderPlayerEvent.Pre event)
	{
		EntityPlayer player = event.getEntityPlayer();
		IPlayerAether playerAether = AetherAPI.getInstance().get(player);

		if (playerAether != null)
		{
			if (playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.invisibility_cape)))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onTextureStichedEvent(TextureStitchEvent.Pre event)
	{
		for (int i = 0; i < InventoryAccessories.EMPTY_SLOT_NAMES.length; ++i)
		{
			event.getMap().registerSprite(new ResourceLocation("aether", "items/slots/" + InventoryAccessories.EMPTY_SLOT_NAMES[i]));
		}
	}

	@SubscribeEvent
	public void onKeyInputEvent(InputEvent.KeyInputEvent event)
	{
		if (Minecraft.getMinecraft().player != null)
		{
			if (AetherKeybinds.keyBindingAccessories.isPressed())
			{
				if (Minecraft.getMinecraft().currentScreen == null)
				{
					AetherNetworkingManager.sendToServer(new PacketOpenContainer(AetherGuiHandler.accessories));
					shouldRemoveButton = true;
				}
			}
		}
	}

	@SubscribeEvent
	public void onKeyboardScreenInputEvent(GuiScreenEvent.KeyboardInputEvent event)
	{
		if (Minecraft.getMinecraft().player != null)
		{
			int keyPressed = Keyboard.getEventKey();

			if (keyPressed == AetherKeybinds.keyBindingAccessories.getKeyCode() && Keyboard.getEventKeyState())
			{
				if (event.getGui() instanceof GuiAccessories)
				{
					Minecraft.getMinecraft().player.closeScreen();
				}
			}
		}
	}
}