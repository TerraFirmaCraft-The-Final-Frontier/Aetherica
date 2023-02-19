package io.tff.aether.items.armor;

import io.tff.aether.client.models.ModelColoredArmor;
import io.tff.aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import io.tff.aether.Aether;
import io.tff.aether.items.ItemsAether;

public class ItemAetherArmor extends ItemArmor
{

	private String[] defualt_location = new String[] {"textures/models/armor/iron_layer_1.png", "textures/models/armor/iron_layer_2.png"};

	private boolean shouldDefualt = false;

	private int colorization = -1;

	private String armorName;

	private Item source = null;

	public ItemAetherArmor(EntityEquipmentSlot armorType, ArmorMaterial material, String name, Item repair)
	{
		super(material, 0, armorType);

		this.source = repair;
		this.armorName = name;
	}

	public ItemAetherArmor(EntityEquipmentSlot armorType, ArmorMaterial material, String name, Item repair, int hex)
	{
		this(armorType, material, name, repair);

		this.source = repair;
		this.armorName = name;
		this.colorization = hex;
		this.shouldDefualt = true;
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
    	if (tab == AetherCreativeTabs.armor || tab == CreativeTabs.SEARCH)
    	{
            items.add(new ItemStack(this));
    	}
    }

    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
		return this.colorization != -1 ? this.colorization : 0;
    }

    public int getColorization(ItemStack stack)
    {
    	return this.colorization;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
    	boolean leggings = this.getTranslationKey().contains("leggings");
    	String type1 = leggings ? "layer_2" : "layer_1";

        return this.shouldDefualt ? (leggings ? defualt_location[1] : defualt_location[0]) : Aether.modAddress() + "textures/armor/" + this.armorName + "_" + type1 + ".png";
    }

    @SideOnly(Side.CLIENT)
    public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default)
    {
    	if (shouldDefualt)
    	{
    		return new ModelColoredArmor(this.getTranslationKey().contains("leggings") ? 0.5F : 1.0F, this, armorSlot);
    	}

        return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
    }

    @Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
	    return source == null ? false : repair.getItem() == source;
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return !this.armorName.contains("zanite") && !this.armorName.contains("gravitite")? ItemsAether.aether_loot : super.getRarity(stack);
    }
}