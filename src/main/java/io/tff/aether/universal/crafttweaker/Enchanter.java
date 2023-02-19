package io.tff.aether.universal.crafttweaker;


import io.tff.aether.api.enchantments.AetherEnchantment;
import io.tff.aether.api.enchantments.AetherEnchantmentFuel;
import io.tff.aether.api.freezables.AetherFreezable;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.aether.Enchanter")
public class Enchanter {

    public static final IForgeRegistry<AetherEnchantment> enchantments = GameRegistry.findRegistry(AetherEnchantment.class);
    public static final IForgeRegistry<AetherEnchantmentFuel> enchantmentFuels = GameRegistry.findRegistry(AetherEnchantmentFuel.class);

    @ZenMethod
    public static void registerEnchantment(IItemStack input, IItemStack output, int timeRequired) {
        CraftTweakerAPI.apply(new RegisterAction<>(enchantments, new AetherEnchantment(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(output), timeRequired)));
    }

    @ZenMethod
    public static void registerEnchantment(IItemStack repair, int timeRequired) {
        CraftTweakerAPI.apply(new RegisterAction<>(enchantments, new AetherEnchantment(CraftTweakerMC.getItemStack(repair), CraftTweakerMC.getItemStack(repair), timeRequired)));
    }

    @ZenMethod
    public static void registerEnchanterFuel(IItemStack input, int timeGiven) {
        CraftTweakerAPI.apply(new RegisterAction<>(enchantmentFuels, new AetherEnchantmentFuel(CraftTweakerMC.getItemStack(input), timeGiven)));
    }

    @ZenMethod
    public static void removeEnchantment(IItemStack input) {
        CraftTweakerAPI.apply(new RegisterAction<>(enchantments, new AetherEnchantment(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(null), 1)));
    }

}
