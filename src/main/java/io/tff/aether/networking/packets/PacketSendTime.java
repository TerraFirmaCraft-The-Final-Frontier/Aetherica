package io.tff.aether.networking.packets;

import io.tff.aether.world.AetherWorldProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldProvider;

public class PacketSendTime extends AetherPacket<PacketSendTime>
{
    private long time;

    public PacketSendTime()
    {

    }

    public PacketSendTime(long time)
    {
        this.time = time;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.time = buf.readLong();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(this.time);
    }

    @Override
    public void handleClient(PacketSendTime message, EntityPlayer player)
    {
        if (player != null && player.world != null && player.world.provider != null)
        {
            WorldProvider provider = player.world.provider;

            if (provider instanceof AetherWorldProvider)
            {
                AetherWorldProvider providerAether = (AetherWorldProvider) provider;

                providerAether.setAetherTime(message.time);
            }
        }
    }

    @Override
    public void handleServer(PacketSendTime message, EntityPlayer player)
    {

    }
}
