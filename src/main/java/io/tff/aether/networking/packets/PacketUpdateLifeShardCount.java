package io.tff.aether.networking.packets;

import io.tff.aether.api.AetherAPI;
import io.tff.aether.api.player.IPlayerAether;
import io.tff.aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketUpdateLifeShardCount extends AetherPacket<PacketUpdateLifeShardCount>
{
    private int entityID;

    private int count;

    public PacketUpdateLifeShardCount()
    {

    }

    public PacketUpdateLifeShardCount(EntityPlayer thePlayer, int count)
    {
        this.entityID = thePlayer.getEntityId();
        this.count = count;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
        this.count = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
        buf.writeInt(this.count);
    }

    @Override
    public void handleClient(PacketUpdateLifeShardCount message, EntityPlayer player)
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

                    playerAether.lifeShardsUsed = message.count;
                }
            }
        }
    }

    @Override
    public void handleServer(PacketUpdateLifeShardCount message, EntityPlayer player)
    {

    }
}
