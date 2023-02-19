package io.tff.aether.networking.packets;

import io.tff.aether.api.player.IPlayerAether;
import io.tff.aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import io.tff.aether.api.AetherAPI;

public class PacketSendPoison extends AetherPacket<PacketSendPoison>
{

	private int entityID;

	public PacketSendPoison()
	{
		
	}

	public PacketSendPoison(EntityPlayer thePlayer)
	{
		this.entityID = thePlayer.getEntityId();
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
	public void handleClient(PacketSendPoison message, EntityPlayer player) 
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

					playerAether.setPoisoned();
				}
			}
		}
	}

	@Override
	public void handleServer(PacketSendPoison message, EntityPlayer player)
	{

	}

}