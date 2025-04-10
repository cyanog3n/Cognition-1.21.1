package com.cyanogen.experienceobelisk.event;

import com.cyanogen.experienceobelisk.gui.ExperienceObeliskScreen;
import com.cyanogen.experienceobelisk.gui.MolecularMetamorpherScreen;
import com.cyanogen.experienceobelisk.gui.PrecisionDispellerScreen;
import com.cyanogen.experienceobelisk.item.NeurogelMendingItem;
import com.cyanogen.experienceobelisk.registries.RegisterMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.ItemStackedOnOtherEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;


public class EventHandler {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onTooltip(ItemTooltipEvent event){
        DescriptionTooltips.handleTooltip(event);
    }

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

    @SubscribeEvent
    public void onItemStackedOnOther(ItemStackedOnOtherEvent event){
        NeurogelMendingItem.handleItem(event);
    }

}
