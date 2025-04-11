package com.cyanogen.experienceobelisk.network.precision_dispeller;

import com.cyanogen.experienceobelisk.ExperienceObelisk;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * This is sent from the client to the server whenever a SelectablePanel in the Precision Dispeller screen is selected or deselected.
 *             Upon reception, the item in the output slot is updated to the appropriate state.
 */
public record UpdateSlot(int slot, ItemStack stack) implements CustomPacketPayload {

    public static final StreamCodec<ByteBuf, UpdateSlot> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            UpdateSlot::slot,
            ByteBufCodecs.fromCodec(ItemStack.CODEC),
            UpdateSlot::stack,
            UpdateSlot::new
    );

    public static final Type<UpdateSlot> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ExperienceObelisk.MOD_ID,
            "precision_dispeller_update_slot"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleClient(UpdateSlot packet, IPayloadContext context) {
    }

    public static void handleServer(UpdateSlot packet, IPayloadContext context) {

        context.enqueueWork(() -> {

            if (!context.player().level().isClientSide) {
                ServerPlayer player = (ServerPlayer) context.player();
                player.containerMenu.getSlot(packet.slot).set(packet.stack);
            }

        });
    }

}
