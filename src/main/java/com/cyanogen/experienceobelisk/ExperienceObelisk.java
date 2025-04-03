package com.cyanogen.experienceobelisk;

import com.cyanogen.experienceobelisk.config.Config;
import com.cyanogen.experienceobelisk.event.EventHandler;
import com.cyanogen.experienceobelisk.gui.ExperienceObeliskScreen;
import com.cyanogen.experienceobelisk.gui.MolecularMetamorpherScreen;
import com.cyanogen.experienceobelisk.gui.PrecisionDispellerScreen;
import com.cyanogen.experienceobelisk.network.PacketHandler;
import com.cyanogen.experienceobelisk.registries.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExperienceObelisk.MOD_ID)

public class ExperienceObelisk
{
    public static final String MOD_ID = "experienceobelisk";

    public ExperienceObelisk() {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);

        RegisterItems.register(eventBus);
        RegisterCreativeTab.register(eventBus);
        RegisterBlocks.register(eventBus);
        RegisterBlockEntities.register(eventBus);
        RegisterFluids.register(eventBus);
        RegisterMenus.register(eventBus);
        RegisterRecipes.register(eventBus);
        RegisterSounds.register(eventBus);

        PacketHandler.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    private void clientSetup(final FMLClientSetupEvent event){
        MenuScreens.register(RegisterMenus.EXPERIENCE_OBELISK_MENU.get(), ExperienceObeliskScreen::new);
        MenuScreens.register(RegisterMenus.PRECISION_DISPELLER_MENU.get(), PrecisionDispellerScreen::new);
        MenuScreens.register(RegisterMenus.MOLECULAR_METAMORPHER_MENU.get(), MolecularMetamorpherScreen::new);

        ItemBlockRenderTypes.setRenderLayer(RegisterFluids.COGNITIUM.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegisterFluids.COGNITIUM_FLOWING.get(), RenderType.translucent());
    }


}
