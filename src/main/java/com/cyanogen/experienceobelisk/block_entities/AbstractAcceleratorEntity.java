package com.cyanogen.experienceobelisk.block_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractAcceleratorEntity extends BlockEntity {

    public AbstractAcceleratorEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    //-----------NBT-----------//

    public boolean redstoneEnabled = false;

    public void toggleRedstoneEnabled(){
        this.redstoneEnabled = !redstoneEnabled;
        setChanged();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {

        super.loadAdditional(tag, registries);

        this.redstoneEnabled = tag.getBoolean("isRedstoneControllable");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {

        super.saveAdditional(tag, registries);

        tag.putBoolean("isRedstoneControllable", redstoneEnabled);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {

        super.handleUpdateTag(tag, lookupProvider);

        this.redstoneEnabled = tag.getBoolean("isRedstoneControllable");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {

        CompoundTag tag = super.getUpdateTag(registries);

        tag.putBoolean("isRedstoneControllable", redstoneEnabled);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}
