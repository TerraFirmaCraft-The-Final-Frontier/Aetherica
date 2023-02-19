package io.tff.aether.networking.packets;

import io.tff.aether.api.AetherAPI;
import io.tff.aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketPortalItem extends AetherPacket<PacketPortalItem>
{
    private int entityID;
    private boolean getPortal;

    public PacketPortalItem()
    {

    }

    public PacketPortalItem(EntityPlayer thePlayer, boolean dialogue)
    {
        this.entityID = thePlayer.getEntityId();
        this.getPortal = dialogue;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        this.getPortal = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        buf.writeBoolean(this.getPortal);
    }

    @Override
    public void handleClient(PacketPortalItem message, EntityPlayer player)
    {
        if (player != null && player.world != null)
        {
            EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                ((PlayerAether) AetherAPI.getInstance().get(parent)).shouldGetPortal = message.getPortal;
            }
        }
    }

    @Override
    public void handleServer(PacketPortalItem message, EntityPlayer player)
    {

    }
}
