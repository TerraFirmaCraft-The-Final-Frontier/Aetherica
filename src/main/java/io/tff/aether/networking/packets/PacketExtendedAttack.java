package io.tff.aether.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketExtendedAttack extends AetherPacket<PacketExtendedAttack>
{
    int entityID;

    public PacketExtendedAttack()
    {

    }

    public PacketExtendedAttack(int entityID)
    {
        this.entityID = entityID;
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
    public void handleClient(PacketExtendedAttack message, EntityPlayer player)
    {

    }

    @Override
    public void handleServer(PacketExtendedAttack message, EntityPlayer player)
    {
        Entity entityFromID = player.world.getEntityByID(message.entityID);

        if (entityFromID != null)
        {
            player.attackTargetEntityWithCurrentItem(entityFromID);
        }
    }
}
