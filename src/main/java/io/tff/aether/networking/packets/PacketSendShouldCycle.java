package io.tff.aether.networking.packets;

import io.tff.aether.world.AetherWorldProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldProvider;

public class PacketSendShouldCycle extends AetherPacket<PacketSendShouldCycle>
{
    private boolean shouldCycle;

    public PacketSendShouldCycle()
    {

    }

    public PacketSendShouldCycle(boolean shouldCycle)
    {
        this.shouldCycle = shouldCycle;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.shouldCycle = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(this.shouldCycle);
    }

    @Override
    public void handleClient(PacketSendShouldCycle message, EntityPlayer player)
    {
        if (player != null && player.world != null && player.world.provider != null)
        {
            WorldProvider provider = player.world.provider;

            if (provider instanceof AetherWorldProvider)
            {
                AetherWorldProvider providerAether = (AetherWorldProvider) provider;

                providerAether.setShouldCycleCatchup(message.shouldCycle);
            }
        }
    }

    @Override
    public void handleServer(PacketSendShouldCycle message, EntityPlayer player)
    {

    }
}
