package io.tff.aether.networking.packets;

import io.tff.aether.api.AetherAPI;
import io.tff.aether.api.player.IPlayerAether;
import io.tff.aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSendPoisonTime extends AetherPacket<PacketSendPoisonTime>
{
    private int entityID;
    private int time;

    public PacketSendPoisonTime()
    {

    }

    public PacketSendPoisonTime(EntityPlayer thePlayer, int time)
    {
        this.entityID = thePlayer.getEntityId();
        this.time = time;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        this.time = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        buf.writeInt(this.time);
    }

    @Override
    public void handleClient(PacketSendPoisonTime message, EntityPlayer player)
    {
        if (player != null && player.world != null)
        {
            Entity entity = player.world.getEntityByID(message.entityID);

            if (entity instanceof EntityPlayer)
            {
                EntityPlayer parent = (EntityPlayer) entity;

                IPlayerAether iPlayerAether = AetherAPI.getInstance().get(parent);

                if (iPlayerAether != null)
                {
                    PlayerAether playerAether = (PlayerAether) iPlayerAether;

                    playerAether.poisonTime = message.time;
                }
            }
        }
    }

    @Override
    public void handleServer(PacketSendPoisonTime message, EntityPlayer player)
    {
        
    }
}
