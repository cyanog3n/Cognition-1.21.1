package com.cyanogen.experienceobelisk.network.shared;

import com.cyanogen.experienceobelisk.ExperienceObelisk;
import com.cyanogen.experienceobelisk.block_entities.ExperienceObeliskEntity;
import com.cyanogen.experienceobelisk.block_entities.ExperienceReceivingEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;


public class UpdateRedstone implements CustomPacketPayload {

    /**
     * This is sent from the client to the server whenever a request to change the redstone sensitivity of an experience-receiving block is made
     */

    public final int posX;
    public final int posY;
    public final int posZ;
    public final boolean isControllable;

    public static final StreamCodec<ByteBuf, UpdateRedstone> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            UpdateRedstone::getPosX,
            ByteBufCodecs.VAR_INT,
            UpdateRedstone::getPosY,
            ByteBufCodecs.VAR_INT,
            UpdateRedstone::getPosZ,
            ByteBufCodecs.BOOL,
            UpdateRedstone::getControllable,
            UpdateRedstone::new
    );

    public static final Type<UpdateRedstone> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ExperienceObelisk.MOD_ID,
            "update_redstone"));

    public UpdateRedstone(BlockPos pos, boolean isControllable) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();
        this.isControllable = isControllable;
    }

    public UpdateRedstone(int posX, int posY, int posZ, boolean isControllable) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.isControllable = isControllable;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosZ() {
        return posZ;
    }

    public boolean getControllable() {
        return isControllable;
    }

    public static void handleClient(UpdateRedstone packet, IPayloadContext context) {
    }

    public static void handleServer(UpdateRedstone packet, IPayloadContext context) {

        context.enqueueWork(() -> {

            if(!context.player().level().isClientSide){

                ServerPlayer sender = (ServerPlayer) context.player();

                BlockEntity serverEntity = sender.level().getBlockEntity(new BlockPos(packet.posX, packet.posY, packet.posZ));

                if(serverEntity instanceof ExperienceObeliskEntity obelisk){
                    obelisk.setRedstoneEnabled(packet.isControllable);
                }
                else if(serverEntity instanceof ExperienceReceivingEntity receiver){
                    receiver.setRedstoneEnabled(packet.isControllable);
                }
            }

        });
    }

}
