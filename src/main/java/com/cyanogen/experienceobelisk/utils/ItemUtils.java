package com.cyanogen.experienceobelisk.utils;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;

public class ItemUtils {

    public static CompoundTag getCustomDataTag(ItemStack stack){
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.of(new CompoundTag())).copyTag();
    }

    public static void saveCustomDataTag(ItemStack stack, CompoundTag tag){
        DataComponentPatch patch = DataComponentPatch.builder().set(DataComponents.CUSTOM_DATA, CustomData.of(tag)).build();
        stack.applyComponents(patch);
    }

    public static int getEnchantmentLevel(ItemStack stack, ResourceKey<Enchantment> enchantmentKey){
        //for vanilla enchantments

        for(Object2IntMap.Entry<Holder<Enchantment>> entry : stack.getTagEnchantments().entrySet()){
            if(entry.getKey().is(enchantmentKey)){
                return entry.getIntValue();
            }
        }

        return 0;
    }

    public static int getEnchantmentLevel(ItemStack stack, ResourceLocation enchantmentLocation){
        //for modded enchantments

        for(Object2IntMap.Entry<Holder<Enchantment>> entry : stack.getTagEnchantments().entrySet()){
            if(entry.getKey().is(enchantmentLocation)){
                return entry.getIntValue();
            }
        }

        return 0;
    }

}
