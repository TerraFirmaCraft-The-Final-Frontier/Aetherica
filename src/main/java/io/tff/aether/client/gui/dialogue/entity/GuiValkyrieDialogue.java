package io.tff.aether.client.gui.dialogue.entity;

import io.tff.aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import io.tff.aether.networking.AetherNetworkingManager;
import io.tff.aether.networking.packets.PacketInitiateValkyrieFight;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;

import io.tff.aether.client.gui.dialogue.DialogueOption;
import io.tff.aether.client.gui.dialogue.GuiDialogue;
import io.tff.aether.items.ItemsAether;

public class GuiValkyrieDialogue extends GuiDialogue
{

	private EntityValkyrieQueen valkyrieQueen;

	private String title;

	private int medalSlotId = -1;

	public GuiValkyrieDialogue(EntityValkyrieQueen valkyrieQueen) 
	{
		super("[" + TextFormatting.YELLOW + valkyrieQueen.getBossName() + ", " + I18n.format("title.aether.valkyrie_queen.name") + TextFormatting.RESET + "]", new DialogueOption(I18n.format("gui.queen.dialog.0")), new DialogueOption(I18n.format("gui.queen.dialog.1")), new DialogueOption(I18n.format("gui.queen.dialog.2")));

		this.title = this.getDialogue();
		this.valkyrieQueen = valkyrieQueen;
	}

	@Override
	public void dialogueClicked(DialogueOption dialogue)
	{
		if (this.getDialogueOptions().size() == 3)
		{
			if (dialogue.getDialogueId() == 0)
			{
				this.addDialogueMessage(this.title + ": " + I18n.format("gui.queen.answer.0"));
				this.dialogueTreeCompleted();
			}
			else if (dialogue.getDialogueId() == 1)
			{
				DialogueOption medalDialogue = new DialogueOption(this.getMedalDiaulogue());

				this.addDialogueWithOptions(this.title + ": " + I18n.format("gui.queen.answer.1"), medalDialogue, new DialogueOption(I18n.format("gui.valkyrie.dialog.player.denyfight")));
			}
			else if (dialogue.getDialogueId() == 2)
			{
				this.addDialogueMessage(this.title + ": " + I18n.format("gui.queen.answer.2"));
				this.dialogueTreeCompleted();
			}
		}
		else
		{
			if (dialogue.getDialogueId() == 0)
			{
	        	if (this.mc.world.getDifficulty() == EnumDifficulty.PEACEFUL)
	        	{
	        		this.addDialogueMessage(this.title +  ": " + I18n.format("gui.queen.peaceful"));
					this.dialogueTreeCompleted();

					return;
	        	}

				if (this.medalSlotId != -1)
				{
					AetherNetworkingManager.sendToServer(new PacketInitiateValkyrieFight(this.medalSlotId, this.valkyrieQueen.getEntityId()));

					this.valkyrieQueen.setBossReady(true);
					this.addDialogueMessage(this.title + ": " + I18n.format("gui.valkyrie.dialog.ready"));
					this.dialogueTreeCompleted();
				}
				else
				{
					this.addDialogueMessage(this.title + ": " + I18n.format("gui.valkyrie.dialog.nomedals"));
					this.dialogueTreeCompleted();
				}
			}
			else if (dialogue.getDialogueId() == 1)
			{
				this.addDialogueMessage(this.title + ": " + I18n.format("gui.valkyrie.dialog.nofight"));
				this.dialogueTreeCompleted();
			}
		}
	}

	private String getMedalDiaulogue()
	{
		for (int slotId = 0; slotId < this.mc.player.inventory.mainInventory.size(); ++slotId)
		{
			ItemStack stack = this.mc.player.inventory.mainInventory.get(slotId);

			if (stack.getItem() == ItemsAether.victory_medal)
			{
				if (stack.getCount() >= 10)
				{
					this.medalSlotId = slotId;
					return I18n.format("gui.valkyrie.dialog.player.havemedals");
				}
				else
				{
					return I18n.format("gui.valkyrie.dialog.player.lackmedals") + " (" + stack.getCount() + "/10)";
				}
			}
		}

		return I18n.format("gui.valkyrie.dialog.player.lackmedals") + " (0/10)";
	}

}