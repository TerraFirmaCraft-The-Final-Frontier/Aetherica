package io.tff.aether.networking.packets;

import io.tff.aether.entities.passive.mountable.EntitySwet;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSwetJump extends AetherPacket<PacketSwetJump>
{
    public int id;
    public boolean bool;

    public PacketSwetJump()
    {

    }

    public PacketSwetJump(int entity, boolean bool)
    {
        this.id = entity;
        this.bool = bool;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.id = buf.readInt();
        this.bool = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.id);
        buf.writeBoolean(this.bool);
    }

    @Override
    public void handleClient(PacketSwetJump message, EntityPlayer player)
    {
        if (player != null && player.world != null)
        {
            Entity entity = player.world.getEntityByID(message.id);

            if (entity instanceof EntitySwet)
            {
                ((EntitySwet) entity).midJump = message.bool;
            }
        }
    }

    @Override
    public void handleServer(PacketSwetJump message, EntityPlayer player)
    {

    }
}
