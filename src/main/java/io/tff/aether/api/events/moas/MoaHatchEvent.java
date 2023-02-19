package io.tff.aether.api.events.moas;

import net.minecraft.tileentity.TileEntity;

import io.tff.aether.api.moa.AetherMoaType;

public class MoaHatchEvent extends MoaEvent
{

	private TileEntity incubator;

	public MoaHatchEvent(AetherMoaType moaType, TileEntity incubator)
	{
		super(moaType);

		this.incubator = incubator;
	}

	public TileEntity getTileEntity()
	{
		return this.incubator;
	}

}