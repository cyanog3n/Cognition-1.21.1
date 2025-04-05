package com.cyanogen.experienceobelisk.registries;

import com.cyanogen.experienceobelisk.ExperienceObelisk;
import com.cyanogen.experienceobelisk.renderer.ExperienceFountainBlockRenderer;
import com.cyanogen.experienceobelisk.renderer.ExperienceObeliskBlockRenderer;
import com.cyanogen.experienceobelisk.renderer.MolecularMetamorpherBlockRenderer;
import com.cyanogen.experienceobelisk.renderer.PrecisionDispellerBlockRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExperienceObelisk.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterRenderers {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(RegisterBlockEntities.EXPERIENCE_OBELISK.get(), ExperienceObeliskBlockRenderer::new);
        event.registerBlockEntityRenderer(RegisterBlockEntities.EXPERIENCE_FOUNTAIN.get(), ExperienceFountainBlockRenderer::new);
        event.registerBlockEntityRenderer(RegisterBlockEntities.PRECISION_DISPELLER.get(), PrecisionDispellerBlockRenderer::new);
        event.registerBlockEntityRenderer(RegisterBlockEntities.MOLECULAR_METAMORPHER.get(), MolecularMetamorpherBlockRenderer::new);
    }
}
