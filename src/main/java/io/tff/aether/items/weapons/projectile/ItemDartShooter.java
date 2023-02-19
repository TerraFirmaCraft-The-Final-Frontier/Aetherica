package io.tff.aether.items.weapons.projectile;

import io.tff.aether.entities.projectile.darts.EntityDartBase;
import io.tff.aether.entities.projectile.darts.EntityDartEnchanted;
import io.tff.aether.entities.projectile.darts.EntityDartGolden;
import io.tff.aether.entities.projectile.darts.EntityDartPoison;
import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import io.tff.aether.registry.sounds.SoundsAether;
import io.tff.aether.items.ItemsAether;
import io.tff.aether.items.util.EnumDartShooterType;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemDartShooter extends Item
{

	public ItemDartShooter()
	{
		this.maxStackSize = 1;
		this.setHasSubtypes(true);
		this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
	public boolean isFull3D()
	{
		return false;
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return stack.getMetadata() == 2 ? EnumRarity.RARE : super.getRarity(stack);
    }

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
    	if (this.isInCreativeTab(tab))
        {
            for (int meta = 0; meta < EnumDartShooterType.values().length; ++meta)
            {
            	subItems.add(new ItemStack(this, 1, meta));
            }
        }
    }

	private int consumeItem(EntityPlayer player, Item itemID, int maxDamage)
	{
		IInventory inv = player.inventory;

		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);

			if (stack == ItemStack.EMPTY)
			{
				continue;
			}

			int damage = stack.getItemDamage();

			if (maxDamage != 3)
			{
				if (stack.getItem() == itemID && stack.getItemDamage() == maxDamage)
				{
					if (!player.capabilities.isCreativeMode)
					{
						stack.shrink(1);
					}

					if (stack.getCount() == 0)
					{
						stack = ItemStack.EMPTY;
					}

					inv.setInventorySlotContents(i, stack);

					return damage;
				}
			}
			if (maxDamage == 3 && stack.getItem() == itemID)
			{
				if (!player.capabilities.isCreativeMode)
				{
					stack.shrink(1);
				}

				if (stack.getCount() == 0)
				{
					stack = ItemStack.EMPTY;
				}

				inv.setInventorySlotContents(i, stack);

				return 3;
			}
		}

		return -1;
	}

	@Override
	public String getTranslationKey(ItemStack itemstack)
	{
		return this.getTranslationKey() + "_" + EnumDartShooterType.getType(itemstack.getItemDamage()).toString();
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer, EnumHand hand)
	{
		ItemStack heldItem = entityplayer.getHeldItem(hand);
		int consume;

		if (!(entityplayer.capabilities.isCreativeMode))
		{
			consume = this.consumeItem(entityplayer, ItemsAether.dart, heldItem.getItemDamage());
		}
		else
		{
			consume = heldItem.getItemDamage();
		}

		if (consume != -1)
		{
			world.playSound(entityplayer, entityplayer.getPosition(), SoundsAether.dart_shooter_shoot, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));

			EntityDartBase dart = null;

			if (consume == 1)
			{
				dart = new EntityDartPoison(world, entityplayer);
			}
			else if (consume == 2)
			{
				dart = new EntityDartEnchanted(world, entityplayer);
			}
			else if (consume == 0)
			{
				dart = new EntityDartGolden(world, entityplayer);
			}

			if (!world.isRemote)
			{
				dart.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 1.0F, 1.0F);
				dart.setNoGravity(true);
				world.spawnEntity(dart);

				if (!(entityplayer.capabilities.isCreativeMode))
				{
					dart.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
				}
				if ((entityplayer.capabilities.isCreativeMode))
				{
					dart.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
				}
			}
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
	}

}