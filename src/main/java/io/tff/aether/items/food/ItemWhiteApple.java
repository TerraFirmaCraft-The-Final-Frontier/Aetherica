package io.tff.aether.items.food;

import io.tff.aether.player.PlayerAether;
import io.tff.aether.api.player.IPlayerAether;
import io.tff.aether.items.ItemsAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import io.tff.aether.api.AetherAPI;

public class ItemWhiteApple extends ItemAetherFood
{

	public ItemWhiteApple()
	{
		super(0);

		this.setAlwaysEdible();
	}

	@Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer playerEntity)
    {
		IPlayerAether player = AetherAPI.getInstance().get(playerEntity);
		((PlayerAether) player).setCured(300);

		if (!world.isRemote)
		{
			playerEntity.curePotionEffects(new ItemStack(ItemsAether.white_apple));
		}
    }

}