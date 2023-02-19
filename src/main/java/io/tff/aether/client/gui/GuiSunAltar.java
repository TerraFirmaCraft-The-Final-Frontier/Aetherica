package io.tff.aether.client.gui;

import io.tff.aether.client.gui.button.GuiSunAltarSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import io.tff.aether.Aether;

public class GuiSunAltar extends GuiScreen
{

	private static final ResourceLocation TEXTURE = Aether.locate("textures/gui/sun_altar.png");

	private World world;

	public GuiSunAltar()
	{

	}

	@Override
	public void initGui()
	{
		super.initGui();

		this.world = this.mc.world;

		this.addButton(new GuiSunAltarSlider(this.world, this.width / 2 - 75, this.height/2, I18n.format("gui.sun_altar.time")));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
        this.mc.renderEngine.bindTexture(TEXTURE);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        int xSize = 175;
        int ySize = 78;
        int j = (this.width - xSize) / 2;
        int k = (this.height - ySize) / 2;

        this.drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        this.fontRenderer.drawString(I18n.format("tile.sun_altar.name"), (this.width - this.fontRenderer.getStringWidth(I18n.format("tile.sun_altar.name"))) / 2, k + 20, 0x404040);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean doesGuiPauseGame() 
	{
		return false;
	}

	protected void keyTyped(char typedChar, int keyCode)
	{
		if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode))
		{
			this.mc.player.closeScreen();
		}
	}
}