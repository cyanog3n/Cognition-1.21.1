package com.cyanogen.experienceobelisk.registries;

import com.cyanogen.experienceobelisk.ExperienceObelisk;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class RegisterFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, ExperienceObelisk.MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ExperienceObelisk.MOD_ID);

    public static final ResourceLocation flowingTexture = ResourceLocation.fromNamespaceAndPath("experienceobelisk","block/cognitium");
    public static final ResourceLocation stillTexture = ResourceLocation.fromNamespaceAndPath("experienceobelisk","block/cognitium");
    //todo: find out resource location for textures
    //todo: hopefully they made this datadriven

    public static final DeferredHolder<FluidType, FluidType> COGNITIUM_FLUID_TYPE = FLUID_TYPES.register("cognitium",
            () -> new FluidType(FluidType.Properties.create()
                    .lightLevel(10)
                    .viscosity(200)
                    .canDrown(false)
                    .canSwim(false)
                    .canPushEntity(false)
                    .canConvertToSource(false)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));

    public static final BaseFlowingFluid.Properties COGNITIUM_PROPERTIES = new BaseFlowingFluid.Properties(COGNITIUM_FLUID_TYPE, () -> Fluids.WATER, () -> Fluids.FLOWING_WATER);
    public static final DeferredHolder<Fluid, Fluid> COGNITIUM = FLUIDS.register("cognitium", ()-> new BaseFlowingFluid.Source(COGNITIUM_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> COGNITIUM_FLOWING = FLUIDS.register("cognitium_flowing", ()-> new BaseFlowingFluid.Flowing(COGNITIUM_PROPERTIES));
    //todo: I may need to make a custom fluid class

    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);
        FLUID_TYPES.register(eventBus);
    }
}
