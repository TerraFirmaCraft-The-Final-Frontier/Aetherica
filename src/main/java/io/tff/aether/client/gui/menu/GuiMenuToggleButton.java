package io.tff.aether.client.gui.menu;

import io.tff.aether.Aether;
import io.tff.aether.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

public class GuiMenuToggleButton extends GuiButton
{
    public GuiMenuToggleButton(int xPos, int yPos)
    {
        super(50, xPos, yPos, 20, 20, "T");
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        super.drawButton(mc, mouseX, mouseY, partialTicks);

        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            int j = 16777215;

            if (this.hovered)
            {
                if (AetherConfig.visual_options.menu_enabled)
                {

                    this.drawCenteredString(fontrenderer, I18n.format("gui.aether_menu.normal_theme"), (this.x + this.width) - 34, (this.height / 2) + 18, j);
                }
                else
                {
                    this.drawCenteredString(fontrenderer, I18n.format("gui.aether_menu.aether_theme"), (this.x + this.width) - 34, (this.height / 2) + 18, j);
                }
            }
        }
    }

    public GuiMenuToggleButton setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;

        return this;
    }

    public void mouseReleased(int mouseX, int mouseY)
    {
        AetherConfig.visual_options.menu_enabled = !AetherConfig.visual_options.menu_enabled;
        ConfigManager.sync(Aether.modid, Config.Type.INSTANCE);

        if (AetherConfig.visual_options.menu_enabled)
        {
            Minecraft.getMinecraft().displayGuiScreen(new AetherMainMenu());
        }
    }
}
