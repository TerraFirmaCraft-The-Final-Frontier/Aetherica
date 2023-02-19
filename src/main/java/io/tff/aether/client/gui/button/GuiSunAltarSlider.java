package io.tff.aether.client.gui.button;

import io.tff.aether.networking.AetherNetworkingManager;
import io.tff.aether.networking.packets.PacketSetTime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class GuiSunAltarSlider extends GuiButton
{

    public float sliderValue;

    public boolean dragging = false;

    private World world;

    public GuiSunAltarSlider(World world, int par2, int par3, String par5Str)
    {
        super(1, par2, par3, 150, 20, par5Str);

        this.world = world;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean par1)
    {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            if (this.dragging)
            {
                this.sliderValue = (float)(mouseX - (this.x + 4)) / (float)(this.width - 8);

            	long shouldTime = (long)(24000L * sliderValue);
            	long worldTime = world.getWorldInfo().getWorldTime();
            	long remainder = worldTime % 24000L;
            	long add = shouldTime > remainder ? shouldTime - remainder : shouldTime + 24000 - remainder;

            	world.getWorldInfo().setWorldTime(worldTime + add);
                if (this.sliderValue < 0.0F)
                {
                    this.sliderValue = 0.0F;
                }

                if (this.sliderValue > 1.0F)
                {
                    this.sliderValue = 1.0F;
                }

            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue * (float)(this.width - 8)), this.y, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.x + (int)(this.sliderValue * (float)(this.width - 8)) + 4, this.y, 196, 66, 4, 20);
        }
    }

    @Override
	public void drawButton(Minecraft par1Minecraft, int mouseX, int mouseY, float partialTicks)
    {
    	this.sliderValue = (this.world.getWorldInfo().getWorldTime() % 24000) / 24000.0F;

		super.drawButton(par1Minecraft, mouseX, mouseY, partialTicks);
	}

    @Override
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        if (super.mousePressed(par1Minecraft, par2, par3))
        {
            this.sliderValue = (float)(par2 - (this.x + 4)) / (float)(this.width - 8);

            if (this.sliderValue < 0.0F)
            {
                this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F)
            {
                this.sliderValue = 1.0F;
            }

            this.dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY)
    {
        this.dragging = false;

        AetherNetworkingManager.sendToServer(new PacketSetTime(this.sliderValue, Minecraft.getMinecraft().player.dimension));
    }

}