package io.tff.aether.client.gui;

import io.tff.aether.containers.ContainerFreezer;
import io.tff.aether.tile_entities.TileEntityFreezer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import io.tff.aether.Aether;

@SideOnly(Side.CLIENT)
public class GuiFreezer extends GuiContainer
{

	private static final ResourceLocation TEXTURE = Aether.locate("textures/gui/altar.png");

	private TileEntityFreezer freezer;

	public GuiFreezer(InventoryPlayer inventory, TileEntityFreezer tileEntity)
	{
		super(new ContainerFreezer(inventory, tileEntity));
		this.freezer = tileEntity;
	}

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
    	super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String freezerName = this.freezer.getDisplayName().getFormattedText();

		this.fontRenderer.drawString(freezerName, this.xSize / 2 - this.fontRenderer.getStringWidth(freezerName) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;

		if (this.freezer.isFreezing())
		{
			i1 = this.freezer.getFreezingTimeRemaining(12);
			this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
		}

		i1 = this.freezer.getFreezingProgressScaled(24);
		this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}

}