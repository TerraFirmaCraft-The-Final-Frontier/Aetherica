package io.tff.aether.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class PacketSetTime extends AetherPacket<PacketSetTime>
{

	public float timeVariable;

	public int dimensionId;

	public PacketSetTime()
	{
		
	}

	public PacketSetTime(float timeVariable, int dimensionId)
	{
		this.dimensionId = dimensionId;
		this.timeVariable = timeVariable;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.dimensionId = buf.readInt();
		this.timeVariable = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.dimensionId);
		buf.writeFloat(this.timeVariable);
	}

	@Override
	public void handleClient(PacketSetTime message, EntityPlayer player)
	{

	}

	@Override
	public void handleServer(PacketSetTime message, EntityPlayer player) 
	{
		message.setTime(message.timeVariable, message.dimensionId);
	}

    public void setTime(float sliderValue, int dimension)
    {
    	MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        for (int i = 0; i < server.worlds.length; ++i)
        {
        	long shouldTime = (long)(24000L * sliderValue);
        	long worldTime = server.worlds[i].getWorldInfo().getWorldTime();
        	long remainder = worldTime % 24000L;
        	long add = shouldTime > remainder ? shouldTime - remainder : shouldTime + 24000 - remainder;

            server.worlds[i].setWorldTime(worldTime + add);
        }
    }

}