package io.tff.aether.player.capability;

import io.tff.aether.CommonProxy;
import io.tff.aether.player.PlayerAetherEvents;
import io.tff.aether.player.perks.AetherRankings;

public class PlayerAetherManager
{

	public static void initialization()
	{
		AetherRankings.initialization();

		CommonProxy.registerEvent(new PlayerAetherEvents());
	}

}