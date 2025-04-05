package com.cyanogen.experienceobelisk.registries;

import com.cyanogen.experienceobelisk.ExperienceObelisk;
import com.cyanogen.experienceobelisk.item.*;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RegisterItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, ExperienceObelisk.MOD_ID);

    public static final Tier COGNITIVE_TIER = RegisterTiers.COGNITIVE_TIER;
    public static final ArmorMaterial COGNITIVE_ARMOR_MATERIAL = RegisterTiers.COGNITIVE_ARMOR_MATERIAL;
    public static final AttributeModifier HANDHELD_RANGE = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("experienceobelisk", "handheld_range"), 1.0, AttributeModifier.Operation.ADD_VALUE);
    public static final AttributeModifier ARMOR_RANGE = new AttributeModifier(ResourceLocation.fromNamespaceAndPath("experienceobelisk", "armor_range"), 0.5, AttributeModifier.Operation.ADD_VALUE);

    public static Item baseItem(){
        return new Item(new Item.Properties());
    }

    //-----CRAFTING INGREDIENTS-----//

    public static final DeferredHolder<Item, Item> COGNITIVE_FLUX = ITEMS.register("cognitive_flux", RegisterItems::baseItem);
    public static final DeferredHolder<Item, Item> COGNITIVE_AMALGAM = ITEMS.register("cognitive_amalgam", RegisterItems::baseItem);
    public static final DeferredHolder<Item, Item> COGNITIVE_ALLOY = ITEMS.register("cognitive_alloy", RegisterItems::baseItem);
    public static final DeferredHolder<Item, Item> COGNITIVE_CRYSTAL = ITEMS.register("cognitive_crystal", RegisterItems::baseItem);
    public static final DeferredHolder<Item, Item> ASTUTE_ASSEMBLY = ITEMS.register("astute_assembly", RegisterItems::baseItem);
    public static final DeferredHolder<Item, Item> PRIMORDIAL_ASSEMBLY = ITEMS.register("primordial_assembly", RegisterItems::baseItem);
    public static final DeferredHolder<Item, Item> FORGOTTEN_DUST = ITEMS.register("forgotten_dust", () -> new Item(new Item.Properties()){
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 100;
        }
    });
    public static final DeferredHolder<Item, Item> CALCARINE_MATRIX = ITEMS.register("calcarine_matrix", RegisterItems::baseItem);

    //-----COGNITIVE TOOLSET-----//

    public static final DeferredHolder<Item, SwordItem> COGNITIVE_SWORD = ITEMS.register("cognitive_sword",
            () -> new SwordItem(COGNITIVE_TIER, new Item.Properties().attributes(ItemAttributeModifiers.builder()
                    .add(Attributes.BLOCK_INTERACTION_RANGE, HANDHELD_RANGE, EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ENTITY_INTERACTION_RANGE, HANDHELD_RANGE, EquipmentSlotGroup.MAINHAND)
                    .build())));

    public static final DeferredHolder<Item, ShovelItem> COGNITIVE_SHOVEL = ITEMS.register("cognitive_shovel",
            () -> new ShovelItem(COGNITIVE_TIER, new Item.Properties()));

    public static final DeferredHolder<Item, PickaxeItem> COGNITIVE_PICKAXE = ITEMS.register("cognitive_pickaxe",
            () -> new PickaxeItem(COGNITIVE_TIER, 1, -2.8f, new Item.Properties()){
                @Override
                public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
                    return addRangeAttributeModifier(super.getDefaultAttributeModifiers(slot), slot, EquipmentSlot.MAINHAND, HANDHELD_RANGE);
                }
            });

    public static final DeferredHolder<Item, AxeItem> COGNITIVE_AXE = ITEMS.register("cognitive_axe",
            () -> new AxeItem(COGNITIVE_TIER, 6, -3.1f, new Item.Properties()){
                @Override
                public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
                    return addRangeAttributeModifier(super.getDefaultAttributeModifiers(slot), slot, EquipmentSlot.MAINHAND, HANDHELD_RANGE);
                }
            });

    public static final DeferredHolder<Item, HoeItem> COGNITIVE_HOE = ITEMS.register("cognitive_hoe",
            () -> new HoeItem(COGNITIVE_TIER, -2, -1, new Item.Properties()){
                @Override
                public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
                    return addRangeAttributeModifier(super.getDefaultAttributeModifiers(slot), slot, EquipmentSlot.MAINHAND, HANDHELD_RANGE);
                }
            });

    public static final DeferredHolder<Item, ArmorItem> COGNITIVE_HELMET = ITEMS.register("cognitive_helmet",
            () -> new ArmorItem(COGNITIVE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties()){
                @Override
                public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
                    return addRangeAttributeModifier(super.getDefaultAttributeModifiers(slot), slot, EquipmentSlot.HEAD, HEAD_RANGE);
                }
            });

    public static final DeferredHolder<Item, ArmorItem> COGNITIVE_CHESTPLATE = ITEMS.register("cognitive_chestplate",
            () -> new ArmorItem(COGNITIVE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()){
                @Override
                public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
                    return addRangeAttributeModifier(super.getDefaultAttributeModifiers(slot), slot, EquipmentSlot.CHEST, CHEST_RANGE);
                }
            });

    public static final DeferredHolder<Item, ArmorItem> COGNITIVE_LEGGINGS = ITEMS.register("cognitive_leggings",
            () -> new ArmorItem(COGNITIVE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties()){
                @Override
                public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
                    return addRangeAttributeModifier(super.getDefaultAttributeModifiers(slot), slot, EquipmentSlot.LEGS, LEGS_RANGE);
                }
            });

    public static final DeferredHolder<Item, ArmorItem> COGNITIVE_BOOTS = ITEMS.register("cognitive_boots",
            () -> new ArmorItem(COGNITIVE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties()){
                @Override
                public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
                    return addRangeAttributeModifier(super.getDefaultAttributeModifiers(slot), slot, EquipmentSlot.FEET, FEET_RANGE);
                }
            });

    public static final DeferredHolder<Item, FishingRodItem> COGNITIVE_ROD = ITEMS.register("cognitive_rod",
            () -> new FishingRodItem(new Item.Properties().defaultDurability(2200)){
                @Override
                public int getEnchantmentValue() {
                    return 15;
                }

                @Override
                public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
                    return addRangeAttributeModifier(super.getDefaultAttributeModifiers(slot), slot, EquipmentSlot.MAINHAND, HANDHELD_RANGE);
                }
            });

    public static final DeferredHolder<Item, ShearsItem> COGNITIVE_SHEARS = ITEMS.register("cognitive_shears",
            () -> new ShearsItem(new Item.Properties().defaultDurability(2200)){

                @Override
                public int getEnchantmentValue(ItemStack stack) {
                    return 15;
                }

                @Override
                public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
                    return addRangeAttributeModifier(super.getDefaultAttributeModifiers(slot), slot, EquipmentSlot.MAINHAND, HANDHELD_RANGE);
                }
            });

    public static Multimap<Attribute, AttributeModifier> addRangeAttributeModifier(Multimap<Attribute, AttributeModifier> attributeMap,
                                                                                   EquipmentSlot slot, EquipmentSlot validSlot, AttributeModifier modifier){

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(attributeMap);
        if(slot.equals(validSlot) && !attributeMap.containsValue(modifier)){
            builder.put(ForgeMod.BLOCK_REACH.get(), modifier);
            builder.put(ForgeMod.ENTITY_REACH.get(), modifier);
        }
        return builder.build();
    }

    //-----FUNCTIONAL ITEMS-----//

    public static final DeferredHolder<Item> ATTUNEMENT_STAFF = ITEMS.register("attunement_staff",
            () -> new AttunementStaffItem(new Item.Properties()));

    public static final DeferredHolder<Item> ENLIGHTENED_AMULET = ITEMS.register("enlightened_amulet",
            () -> new EnlightenedAmuletItem(new Item.Properties()));

    public static final DeferredHolder<BucketItem> COGNITIUM_BUCKET = ITEMS.register("cognitium_bucket",
            () -> new BucketItem(RegisterFluids.COGNITIUM, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredHolder<Item> NIGHTMARE_BOTTLE = ITEMS.register("nightmare_bottle",
            () -> new BottleNightmareItem(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item> DAYDREAM_BOTTLE = ITEMS.register("daydream_bottle",
            () -> new BottleDaydreamItem(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item> BIBLIOPHAGE = ITEMS.register("bibliophage",
            () -> new BibliophageItem(new Item.Properties()));

    public static final DeferredHolder<Item> EXPERIENCE_JELLY = ITEMS.register("experience_jelly",
            () -> new ExperienceJellyItem(new Item.Properties()));

    public static final DeferredHolder<Item> MENDING_NEUROGEL = ITEMS.register("mending_neurogel",
            () -> new NeurogelMendingItem(new Item.Properties()));

    public static final DeferredHolder<Item> POSEIDON_FLASK = ITEMS.register("flask_of_poseidon",
            () -> new FlaskPoseidonItem(new Item.Properties()));

    public static final DeferredHolder<Item> HADES_FLASK = ITEMS.register("flask_of_hades",
            () -> new FlaskHadesItem(new Item.Properties()));

    public static final DeferredHolder<Item> CHAOS_FLASK = ITEMS.register("flask_of_chaos",
            () -> new FlaskChaosItem(new Item.Properties()));

    public static final DeferredHolder<Item> TRANSFORMING_FOCUS = ITEMS.register("transforming_focus",
            () -> new TransformingFocusItem(new Item.Properties()));

    //-----FUNCTIONAL BLOCK ITEMS-----//

    public static final DeferredHolder<Item> EXPERIENCE_OBELISK_ITEM = ITEMS.register("experience_obelisk",
            () -> new ExperienceObeliskItem(RegisterBlocks.EXPERIENCE_OBELISK.get(), new Item.Properties()));

    public static final DeferredHolder<Item> EXPERIENCE_FOUNTAIN_ITEM = ITEMS.register("experience_fountain",
            () -> new ExperienceFountainItem(RegisterBlocks.EXPERIENCE_FOUNTAIN.get(), new Item.Properties()));

    public static final DeferredHolder<Item> PRECISION_DISPELLER_ITEM = ITEMS.register("precision_dispeller",
            () -> new PrecisionDispellerItem(RegisterBlocks.PRECISION_DISPELLER.get(), new Item.Properties()));

    public static final DeferredHolder<Item> MOLECULAR_METAMORPHER_ITEM = ITEMS.register("molecular_metamorpher",
            () -> new MolecularMetamorpherItem(RegisterBlocks.MOLECULAR_METAMORPHER.get(), new Item.Properties()));

    public static final DeferredHolder<Item> ACCELERATOR_ITEM = ITEMS.register("accelerator",
            () -> new BlockItem(RegisterBlocks.ACCELERATOR.get(), new Item.Properties()));

    public static final DeferredHolder<Item> LINEAR_ACCELERATOR_ITEM = ITEMS.register("linear_accelerator",
            () -> new BlockItem(RegisterBlocks.LINEAR_ACCELERATOR.get(), new Item.Properties()));

    public static final DeferredHolder<Item> ENCHANTED_BOOKSHELF_ITEM = ITEMS.register("enchanted_bookshelf",
            () -> new BlockItem(RegisterBlocks.ENCHANTED_BOOKSHELF.get(), new Item.Properties()));

    public static final DeferredHolder<Item> ARCHIVERS_BOOKSHELF_ITEM = ITEMS.register("archivers_bookshelf",
            () -> new BlockItem(RegisterBlocks.ARCHIVERS_BOOKSHELF.get(), new Item.Properties()));

    public static final DeferredHolder<Item> INFECTED_BOOKSHELF_ITEM = ITEMS.register("infected_bookshelf",
            () -> new BlockItem(RegisterBlocks.INFECTED_BOOKSHELF.get(), new Item.Properties()));

    public static final DeferredHolder<Item> INFECTED_ENCHANTED_BOOKSHELF_ITEM = ITEMS.register("infected_enchanted_bookshelf",
            () -> new BlockItem(RegisterBlocks.INFECTED_ENCHANTED_BOOKSHELF.get(), new Item.Properties()));

    public static final DeferredHolder<Item> INFECTED_ARCHIVERS_BOOKSHELF_ITEM = ITEMS.register("infected_archivers_bookshelf",
            () -> new BlockItem(RegisterBlocks.INFECTED_ARCHIVERS_BOOKSHELF.get(), new Item.Properties()));

    public static final DeferredHolder<Item> FLUORESCENT_AGAR_ITEM = ITEMS.register("fluorescent_agar",
            () -> new BlockItem(RegisterBlocks.FLUORESCENT_AGAR.get(), new Item.Properties()));

    public static final DeferredHolder<Item> NUTRIENT_AGAR_ITEM = ITEMS.register("nutrient_agar",
            () -> new BlockItem(RegisterBlocks.NUTRIENT_AGAR.get(), new Item.Properties()));

    public static final DeferredHolder<Item> INSIGHTFUL_AGAR_ITEM = ITEMS.register("insightful_agar",
            () -> new BlockItem(RegisterBlocks.INSIGHTFUL_AGAR.get(), new Item.Properties()));

    public static final DeferredHolder<Item> EXTRAVAGANT_AGAR_ITEM = ITEMS.register("extravagant_agar",
            () -> new BlockItem(RegisterBlocks.EXTRAVAGANT_AGAR.get(), new Item.Properties()));

    //-----BLOCK ITEMS-----//

    public static final DeferredHolder<Item> COGNITIVE_ALLOY_BLOCK_ITEM = ITEMS.register("cognitive_alloy_block",
            () -> new BlockItem(RegisterBlocks.COGNITIVE_ALLOY_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<Item> COGNITIVE_CRYSTAL_BLOCK_ITEM = ITEMS.register("cognitive_crystal_block",
            () -> new BlockItem(RegisterBlocks.COGNITIVE_CRYSTAL_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<Item> WHISPERGLASS_ITEM = ITEMS.register("whisperglass",
            () -> new BlockItem(RegisterBlocks.WHISPERGLASS_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<Item> FORGOTTEN_DUST_BLOCK_ITEM = ITEMS.register("forgotten_dust_block",
            () -> new BlockItem(RegisterBlocks.FORGOTTEN_DUST_BLOCK.get(), new Item.Properties()){
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return 400;
                }
            });

    //-----DUMMY ITEM-----//

    public static final DeferredHolder<Item> DUMMY_SWORD = ITEMS.register("dummy_sword", RegisterItems::baseItem);


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
