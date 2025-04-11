package com.cyanogen.experienceobelisk.event;

import com.cyanogen.experienceobelisk.gui.ExperienceObeliskScreen;
import com.cyanogen.experienceobelisk.gui.MolecularMetamorpherScreen;
import com.cyanogen.experienceobelisk.gui.PrecisionDispellerScreen;
import com.cyanogen.experienceobelisk.registries.RegisterMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class IModBusEventHandler {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRegisterItemDecorator(RegisterItemDecorationsEvent event){
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRegisterMenuScreens(RegisterMenuScreensEvent event){
        event.register(RegisterMenus.EXPERIENCE_OBELISK_MENU.get(), ExperienceObeliskScreen::new);
        event.register(RegisterMenus.PRECISION_DISPELLER_MENU.get(), PrecisionDispellerScreen::new);
        event.register(RegisterMenus.MOLECULAR_METAMORPHER_MENU.get(), MolecularMetamorpherScreen::new);

    }

}
