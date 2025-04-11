package com.cyanogen.experienceobelisk.registries;

import com.cyanogen.experienceobelisk.network.experience_obelisk.UpdateContents;
import com.cyanogen.experienceobelisk.network.experience_obelisk.UpdateRadius;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class RegisterPackets {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playBidirectional(UpdateContents.TYPE, UpdateContents.STREAM_CODEC,
                new DirectionalPayloadHandler<>(UpdateContents::handleClient, UpdateContents::handleServer));

        registrar.playBidirectional(UpdateRadius.TYPE, UpdateRadius.STREAM_CODEC,
                new DirectionalPayloadHandler<>(UpdateRadius::handleClient, UpdateRadius::handleServer));

    }


}
