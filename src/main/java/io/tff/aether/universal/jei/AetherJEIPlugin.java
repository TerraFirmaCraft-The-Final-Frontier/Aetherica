package io.tff.aether.universal.jei;

import io.tff.aether.universal.jei.category.EnchanterRecipeCategory;
import io.tff.aether.universal.jei.category.FreezerRecipeCategory;
import io.tff.aether.universal.jei.wrapper.EnchanterRecipeWrapper;
import io.tff.aether.universal.jei.wrapper.FreezerRecipeWrapper;
import io.tff.aether.api.AetherAPI;
import io.tff.aether.api.enchantments.AetherEnchantment;
import io.tff.aether.api.freezables.AetherFreezable;
import io.tff.aether.blocks.BlocksAether;
import io.tff.aether.client.gui.GuiEnchanter;
import io.tff.aether.client.gui.GuiFreezer;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class AetherJEIPlugin implements IModPlugin
{

	@Override
	public void register(IModRegistry registry)
	{
		registry.handleRecipes(AetherEnchantment.class, new IRecipeWrapperFactory<AetherEnchantment>() {

			@Override
			public IRecipeWrapper getRecipeWrapper(AetherEnchantment recipe)
			{
				if (!recipe.getOutput().isEmpty())
				{
					return new EnchanterRecipeWrapper(recipe);
				}
				else
				{
					return null;
				}
			}
		}, "aether.enchantment");

		registry.handleRecipes(AetherFreezable.class, new IRecipeWrapperFactory<AetherFreezable>() {

			@Override
			public IRecipeWrapper getRecipeWrapper(AetherFreezable recipe)
			{
				if (!recipe.getOutput().isEmpty())
				{
					return new FreezerRecipeWrapper(recipe);
				}
				else
				{
					return null;
				}
			}
		}, "aether.freezable");

		registry.addRecipeCatalyst(new ItemStack(BlocksAether.enchanter), "aether.enchantment");
		registry.addRecipeCatalyst(new ItemStack(BlocksAether.freezer), "aether.freezable");

		registry.addRecipes(AetherAPI.getInstance().getEnchantmentValues(), "aether.enchantment");
		registry.addRecipes(AetherAPI.getInstance().getFreezableValues(), "aether.freezable");

		registry.addRecipeClickArea(GuiFreezer.class, 80, 35, 22, 15, "aether.freezable");
        registry.addRecipeClickArea(GuiEnchanter.class, 80, 35, 22, 15, "aether.enchantment");

    }

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		registry.addRecipeCategories(new EnchanterRecipeCategory(registry.getJeiHelpers().getGuiHelper()), new FreezerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry)
	{

	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry)
	{

	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
	{

	}

}