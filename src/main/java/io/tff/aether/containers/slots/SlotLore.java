package io.tff.aether.containers.slots;

import io.tff.aether.networking.AetherNetworkingManager;
import io.tff.aether.networking.packets.PacketCheckKey;
import io.tff.aether.registry.AetherLore;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class SlotLore extends Slot
{
    public SlotLore(IInventory inv, int slot, int x, int y)
    {
        super(inv, slot, x, y);
    }

    public boolean isItemValid(ItemStack stack)
    {
        if (FMLCommonHandler.instance().getSide().isClient())
        {
            if (I18n.hasKey(AetherLore.getLoreEntryKey(stack)))
            {
                AetherLore.hasKey = true;
                AetherNetworkingManager.sendToServer(new PacketCheckKey(true));
            }
            else
            {
                AetherLore.hasKey = false;
                AetherNetworkingManager.sendToServer(new PacketCheckKey(false));
            }
        }

        return AetherLore.hasKey;
    }
}
