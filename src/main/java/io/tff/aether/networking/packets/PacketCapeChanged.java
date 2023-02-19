package io.tff.aether.networking.packets;

import io.tff.aether.api.AetherAPI;
import io.tff.aether.api.player.IPlayerAether;
import io.tff.aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketCapeChanged extends AetherPacket<PacketCapeChanged>
{
    public int entityID;

    public boolean renderCape;

    public PacketCapeChanged()
    {

    }

    public PacketCapeChanged(int entityID, boolean info)
    {
        this.entityID = entityID;

        this.renderCape = info;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityID = buf.readInt();

        this.renderCape = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityID);

        buf.writeBoolean(this.renderCape);
    }

    @Override
    public void handleClient(PacketCapeChanged message, EntityPlayer player)
    {
        if (player != null && player.world != null)
        {
            EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                IPlayerAether instance = AetherAPI.getInstance().get(parent);

                ((PlayerAether) instance).shouldRenderCape = message.renderCape;
            }
        }
    }

    @Override
    public void handleServer(PacketCapeChanged message, EntityPlayer player)
    {
        if (player != null && player.world != null && !player.world.isRemote)
        {
            EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

            if (parent != null)
            {
                IPlayerAether instance = AetherAPI.getInstance().get(parent);

                ((PlayerAether) instance).shouldRenderCape = message.renderCape;
            }
        }
    }
}
