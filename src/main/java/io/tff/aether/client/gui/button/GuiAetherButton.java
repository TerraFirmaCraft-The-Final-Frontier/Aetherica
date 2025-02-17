package io.tff.aether.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiAetherButton extends GuiButton
{

    protected static final ResourceLocation buttonTextures = new ResourceLocation("aether", "textures/gui/buttons.png");

	public int scrollMax = 100;

	public int scrollHeight = this.scrollMax;

	public int scrollMin = 115;

	public int scrollCrop = 20;

	public int scrollCropMax = 90;

	public boolean retracting = false;

	public GuiAetherButton(int i, int j, int k, String s)
	{
		super(i, j, k, s);
	}

	@Override
	public int getHoverState(boolean flag)
	{
		byte byte0 = 1;

		if (!this.enabled)
		{
			byte0 = 0;
		}
		else if (flag)
		{
			if (byte0 < 2)
			{
				byte0++;
			}
			if (this.scrollCrop < this.scrollCropMax)
			{
				this.scrollCrop += 4;
			}
			if (this.scrollHeight < this.scrollMin)
			{
				this.scrollHeight += 4;
			}
		}
		else
		{
			if (this.scrollCrop > this.scrollCropMax)
			{
				this.scrollCrop -= 4;
			}
			if (this.scrollHeight > this.scrollMax)
			{
				this.scrollHeight -= 4;
			}
			if (this.scrollHeight == this.scrollMax)
			{
				this.retracting = false;
			}
		}

		return byte0;
	}

	@Override
    public void drawButton(Minecraft minecraft, int i, int j, float partialTicks)
	{
		if (!this.visible)
		{
			return;
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.renderEngine.bindTexture(buttonTextures);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		boolean flag = i >= this.x && j >= this.y && i < this.x + this.width && j < this.y + this.height;
		int k = this.getHoverState(flag);
		this.drawTexturedModalRect(this.x + this.scrollHeight - 90, this.y, 0, 46 + k * 20, this.width / 2, this.height);
		this.drawTexturedModalRect(this.x + this.scrollHeight + this.width / 2 - 90, this.y, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
		this.mouseDragged(minecraft, i, j);

		GL11.glDisable(GL11.GL_BLEND);

		if (!this.enabled)
		{
			this.drawString(fontrenderer, this.displayString, this.x + this.width / 10 + this.scrollHeight - 80, this.y + (this.height - 8) / 2, -6250336);
		}
		else if (flag)
		{
			this.drawString(fontrenderer, this.displayString, this.x + this.width / 10 + this.scrollHeight - 80, this.y + (this.height - 8) / 2, 0x77cccc);
		}
		else
		{
			this.drawString(fontrenderer, this.displayString, this.x + this.width / 10 + this.scrollHeight - 80, this.y + (this.height - 8) / 2, 14737632);
		}
	}

	@Override
	protected void mouseDragged(Minecraft var1, int var2, int var3)
	{
	}

	@Override
	public void mouseReleased(int var1, int var2)
	{
	}

	@Override
	public boolean mousePressed(Minecraft var1, int var2, int var3)
	{
		return this.enabled && this.visible && var2 >= this.x && var3 >= this.y && var2 < this.x + this.width && var3 < this.y + this.height;
	}

}