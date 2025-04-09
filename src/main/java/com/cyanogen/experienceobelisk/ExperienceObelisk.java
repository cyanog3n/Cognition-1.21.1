package com.cyanogen.experienceobelisk;

import com.cyanogen.experienceobelisk.config.Config;
import com.cyanogen.experienceobelisk.event.EventHandler;
import com.cyanogen.experienceobelisk.network.PacketHandler;
import com.cyanogen.experienceobelisk.registries.*;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(ExperienceObelisk.MOD_ID)

public class ExperienceObelisk
{
    public static final String MOD_ID = "experienceobelisk";

    public ExperienceObelisk(IEventBus eventBus, ModContainer modContainer) {

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);

        RegisterItems.register(eventBus);
        RegisterCreativeTab.register(eventBus);
        RegisterBlocks.register(eventBus);
        RegisterBlockEntities.register(eventBus);
        RegisterFluids.register(eventBus);
        RegisterMenus.register(eventBus);
        RegisterRecipes.register(eventBus);
        RegisterSounds.register(eventBus);

        PacketHandler.init();

        NeoForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(new EventHandler());
    }

    private void clientSetup(final FMLClientSetupEvent event){
        //MenuScreens.register(RegisterMenus.EXPERIENCE_OBELISK_MENU.get(), ExperienceObeliskScreen::new);
        //MenuScreens.register(RegisterMenus.PRECISION_DISPELLER_MENU.get(), PrecisionDispellerScreen::new);
        //MenuScreens.register(RegisterMenus.MOLECULAR_METAMORPHER_MENU.get(), MolecularMetamorpherScreen::new);

        ItemBlockRenderTypes.setRenderLayer(RegisterFluids.COGNITIUM.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(RegisterFluids.COGNITIUM_FLOWING.get(), RenderType.translucent());
    }


}
