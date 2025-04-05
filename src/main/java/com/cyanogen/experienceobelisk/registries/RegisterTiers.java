package com.cyanogen.experienceobelisk.registries;

import com.cyanogen.experienceobelisk.ExperienceObelisk;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterTiers {

    public static Tier COGNITIVE_TIER = new Tier() {

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_IRON_TOOL;
        }

        @Override
        public int getUses() {
            return 835;
        }

        @Override
        public float getSpeed() {
            return 7.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 3.0F;
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(RegisterItems.COGNITIVE_ALLOY.get());
        }
    };

    public static Map<ArmorItem.Type, Integer> getCognitiveDefenseForSlot(){
        HashMap<ArmorItem.Type, Integer> map = new HashMap<>();
        map.put(ArmorItem.Type.HELMET, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 7);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.BOOTS, 2);
        return map;
    }

    public static ArmorMaterial COGNITIVE = new ArmorMaterial(
            getCognitiveDefenseForSlot(),
            15,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> COGNITIVE_TIER.getRepairIngredient(),
            List.of(new ArmorMaterial.Layer(ResourceLocation.withDefaultNamespace("cognitive"))), //todo: check how 1.21.1 handles armor layers
            1.0f,
            0);

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, ExperienceObelisk.MOD_ID);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> COGNITIVE_ARMOR_MATERIAL = ARMOR_MATERIALS.register("cognitive", () -> COGNITIVE);

}
