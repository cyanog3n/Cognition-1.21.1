package com.cyanogen.experienceobelisk.network.shared;

import com.cyanogen.experienceobelisk.ExperienceObelisk;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * This is sent from the client to the server upon any inventory change that needs to be synced.
 * As of now this is only used by the JEI transfer handler for the Molecular Metamorpher
 */
public record UpdateInventory(CompoundTag container, CompoundTag inventory) implements CustomPacketPayload {

    public static final StreamCodec<ByteBuf, UpdateInventory> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(CompoundTag.CODEC),
            UpdateInventory::container,
            ByteBufCodecs.fromCodec(CompoundTag.CODEC),
            UpdateInventory::inventory,
            UpdateInventory::new
    );

    public static final Type<UpdateInventory> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ExperienceObelisk.MOD_ID,
            "update_inventory"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleClient(UpdateInventory packet, IPayloadContext context) {
    }

    public static void handleServer(UpdateInventory packet, IPayloadContext context) {

        context.enqueueWork(() -> {

            if (!context.player().level().isClientSide) {
                ServerPlayer player = (ServerPlayer) context.player();

                ListTag inventoryList = (ListTag) packet.inventory.get("Inventory");
                ListTag containerList = packet.container.getList("Container", 9);

                if(inventoryList != null){
                    player.getInventory().load(inventoryList);

                    for(Slot slot : player.containerMenu.slots){
                        CompoundTag tag = containerList.getCompound(slot.index);
                        ItemStack stack = ItemStack.parseOptional(player.level().registryAccess(), tag);

                        slot.set(stack);
                    }
                }

            }

        });
    }

}
