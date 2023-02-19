package io.tff.aether.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemHealingStone extends ItemAetherFood 
{

	public ItemHealingStone()
	{
		super(0);

		this.setAlwaysEdible();
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return EnumRarity.RARE;
    }

	@Override
    public boolean hasEffect(ItemStack stack)
    {
    	return true;
    }

	@Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 610, 0));
    }

}