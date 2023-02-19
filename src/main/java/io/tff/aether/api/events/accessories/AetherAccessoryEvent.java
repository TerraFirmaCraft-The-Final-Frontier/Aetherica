package io.tff.aether.api.events.accessories;

import io.tff.aether.api.accessories.AetherAccessory;
import net.minecraftforge.fml.common.eventhandler.Event;

public class AetherAccessoryEvent extends Event
{

	private AetherAccessory accessory;

	public AetherAccessoryEvent(AetherAccessory accessory)
	{
		this.accessory = accessory;
	}

	public AetherAccessory getAetherAccessory()
	{
		return this.accessory;
	}

}