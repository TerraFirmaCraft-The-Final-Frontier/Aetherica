package io.tff.aether.networking.packets;

import io.tff.aether.Aether;
import io.tff.aether.api.AetherAPI;
import io.tff.aether.entities.projectile.crystals.EntityIceyBall;
import io.tff.aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketIceParticles extends AetherPacket<PacketIceParticles>
{
    private int entityID;

    public PacketIceParticles()
    {

    }

    public PacketIceParticles(EntityIceyBall iceyBall)
    {
        this.entityID = iceyBall.getEntityId();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);
    }

    @Override
    public void handleClient(PacketIceParticles message, EntityPlayer player)
    {
        if (player != null && player.world != null)
        {
            EntityIceyBall parent = (EntityIceyBall) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                Aether.proxy.spawnSplode(parent.world, parent.posX, parent.posY, parent.posZ);
            }
        }
    }

    @Override
    public void handleServer(PacketIceParticles message, EntityPlayer player)
    {

    }
}
