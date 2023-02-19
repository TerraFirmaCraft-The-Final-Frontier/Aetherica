package io.tff.aether.api.events.moas;

import net.minecraftforge.fml.common.eventhandler.Event;

import io.tff.aether.api.moa.AetherMoaType;

public class MoaEvent extends Event
{

	private AetherMoaType moaType;

	public MoaEvent(AetherMoaType moaType)
	{
		this.moaType = moaType;
	}

	public AetherMoaType getMoaType()
	{
		return this.moaType;
	}

}