package com.cyanogen.experienceobelisk.utils;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Optional;

public class EnchantmentUtils {

    public static int getEnchantmentLevel(ItemStack stack, ResourceKey<Enchantment> enchantmentKey){

        for(Object2IntMap.Entry<Holder<Enchantment>> entry : stack.getTagEnchantments().entrySet()){
            if(entry.getKey().is(enchantmentKey)){
                return entry.getIntValue();
            }
        }
        return 0;
    }

    public static int getEnchantmentLevel(ItemStack stack, ResourceLocation enchantmentLocation){
        for(Object2IntMap.Entry<Holder<Enchantment>> entry : stack.getTagEnchantments().entrySet()){
            if(entry.getKey().is(enchantmentLocation)){
                return entry.getIntValue();
            }
        }
        return 0;
    }

    public static Optional<Holder.Reference<Enchantment>> getEnchantmentHolder(RegistryAccess access, ResourceKey<Enchantment> enchantmentKey){
        return access.registryOrThrow(Registries.ENCHANTMENT).getHolder(enchantmentKey);
    }

    public static Optional<Holder.Reference<Enchantment>> getEnchantmentHolder(RegistryAccess access, ResourceLocation enchantmentLocation){
        return access.registryOrThrow(Registries.ENCHANTMENT).getHolder(enchantmentLocation);
    }

}
