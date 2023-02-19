package io.tff.aether.networking.packets;

import io.tff.aether.events.DialogueClickedEvent;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketDialogueClicked extends AetherPacket<PacketDialogueClicked>
{

	public String dialogueName;

	public int dialogueId;

	public PacketDialogueClicked()
	{
		
	}

	public PacketDialogueClicked(String dialogueName, int dialogueId)
	{
		this.dialogueName = dialogueName;
		this.dialogueId = dialogueId;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.dialogueName = ByteBufUtils.readUTF8String(buf);
		this.dialogueId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.dialogueName);
		buf.writeInt(this.dialogueId);
	}

	@Override
	public void handleClient(PacketDialogueClicked message, EntityPlayer player) 
	{

	}

	@Override
	public void handleServer(PacketDialogueClicked message, EntityPlayer player)
	{
		if (player != null)
		{
			MinecraftForge.EVENT_BUS.post(new DialogueClickedEvent(player, message.dialogueName, message.dialogueId));
		}
	}

}