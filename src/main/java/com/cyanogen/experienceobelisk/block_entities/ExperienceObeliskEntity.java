package com.cyanogen.experienceobelisk.block_entities;

import com.cyanogen.experienceobelisk.config.Config;
import com.cyanogen.experienceobelisk.network.experience_obelisk.UpdateContents;
import com.cyanogen.experienceobelisk.registries.RegisterBlockEntities;
import com.cyanogen.experienceobelisk.registries.RegisterFluids;
import com.cyanogen.experienceobelisk.registries.RegisterTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

import static com.cyanogen.experienceobelisk.utils.ExperienceUtils.levelsToXP;
import static com.cyanogen.experienceobelisk.utils.ExperienceUtils.xpToLevels;

public class ExperienceObeliskEntity extends BlockEntity implements GeoBlockEntity {

    public ExperienceObeliskEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.EXPERIENCE_OBELISK.get(), pos, state);
    }

    //-----------ANIMATIONS-----------//

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation IDLE = RawAnimation.begin().thenPlay("idle");
    protected static final RawAnimation IDLE_INACTIVE = RawAnimation.begin().thenPlay("idle.inactive");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, this::controller));
    }

    protected <E extends ExperienceObeliskEntity> PlayState controller(final AnimationState<E> state){

        BlockEntity entity = state.getAnimatable();
        AnimationController<E> controller = state.getController();
        RawAnimation animation = controller.getCurrentRawAnimation();
        RawAnimation animationToPlay;

        if(level != null
                && entity instanceof ExperienceObeliskEntity obelisk
                && obelisk.redstoneEnabled
                && !level.hasNeighborSignal(obelisk.getBlockPos())){
            animationToPlay = IDLE_INACTIVE;
        }
        else{
            animationToPlay = IDLE;
        }

        if(animation == null || !animation.equals(animationToPlay) || controller.hasAnimationFinished() || level.getGameTime() % 6000 == 0){
            controller.stop();
            controller.setAnimation(animationToPlay);
        }

        return PlayState.CONTINUE;

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    //-----------PASSIVE BEHAVIOR-----------//

    protected boolean redstoneEnabled = false;
    protected double radius = 2.5;

    public static <T> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {

        boolean isRedstonePowered = level.hasNeighborSignal(pos);

        if(blockEntity instanceof ExperienceObeliskEntity obelisk){

            boolean absorb = !obelisk.isRedstoneEnabled() || isRedstonePowered;
            double radius = obelisk.getRadius();
            int space = obelisk.getSpace();

            if(absorb && level.getGameTime() % 10 == 0){
                AABB area = new AABB(
                        pos.getX() - radius,
                        pos.getY() - radius,
                        pos.getZ() - radius,
                        pos.getX() + radius,
                        pos.getY() + radius,
                        pos.getZ() + radius);

                List<ExperienceOrb> list = level.getEntitiesOfClass(ExperienceOrb.class, area);

                if(!list.isEmpty()) for(int i = 0; i < Math.min(30,list.size()); i++){

                    ExperienceOrb orb = list.get(i);
                    CompoundTag tag = new CompoundTag();
                    orb.addAdditionalSaveData(tag);

                    int value = orb.value;
                    int count = tag.getInt("Count");

                    int amount = value * 20 * count;
                    if(space >= amount){
                        obelisk.fill(amount);
                        space = space - amount;
                        orb.discard();
                    }
                }
            }
        }
    }

    public boolean isRedstoneEnabled(){
        return this.redstoneEnabled;
    }

    public void setRedstoneEnabled(boolean state){
        this.redstoneEnabled = state;
        this.setChanged();
    }

    public double getRadius(){
        return this.radius;
    }

    public void setRadius(double radius){
        this.radius = radius;
        this.setChanged();
    }

    @Override
    public void setChanged() {
        if(this.level != null){
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
        }
        super.setChanged();
    }

    //-----------FLUID HANDLER-----------//

    protected FluidTank tank = experienceObeliskTank();

    private static final Fluid cognitium = RegisterFluids.COGNITIUM.get();

    public static final int capacity = (int) Math.min((Math.round((double) Config.COMMON.capacity.get() / 20) * 20), 2147483640);

    private FluidTank experienceObeliskTank() {
        return new FluidTank(capacity){

            @Override
            protected void onContentsChanged()
            {
                setChanged();
            }

            @Override
            public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
                return isFluidValid(stack);
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                String fluidName = BuiltInRegistries.FLUID.getKey(stack.getFluid()).toString();

                if(stack.getFluid() == cognitium){
                    return true;
                }
                else{
                    return stack.getFluid().is(RegisterTags.Fluids.EXPERIENCE) && Config.COMMON.allowedFluids.get().contains(fluidName);
                }
            }

            @Override
            public int fill(FluidStack resource, FluidAction action) {

                if(isFluidValid(resource)){
                    setChanged();
                    return super.fill(new FluidStack(cognitium, resource.getAmount()), action);
                }
                else{
                    return 0;
                }
            }

            @NotNull
            @Override
            public FluidStack drain(int maxDrain, FluidAction action) {
                setChanged();
                return super.drain(maxDrain, action);
            }

            @NotNull
            @Override
            public FluidStack drain(FluidStack resource, FluidAction action) {
                setChanged();
                return super.drain(resource, action);
            }

            @Override
            public void setFluid(FluidStack stack)
            {
                this.fluid = stack;
                setChanged();
            }

            @Override
            public int getTanks() {
                return 1;
            }
        };
    }

    public int fill(int amount){
        return tank.fill(new FluidStack(cognitium, amount), IFluidHandler.FluidAction.EXECUTE);
    }

    public void drain(int amount)
    {
        tank.drain(new FluidStack(cognitium, amount), IFluidHandler.FluidAction.EXECUTE);
    }

    public void setFluid(int amount)
    {
        tank.setFluid(new FluidStack(cognitium, amount));
    }

    public int getFluidAmount(){
        return tank.getFluidAmount();
    }

    public int getSpace(){ return tank.getSpace(); }

    public int getExperiencePoints(){
        return getFluidAmount() / 20;
    }

    public int getLevels(){
        return xpToLevels(getExperiencePoints());
    }

    public static @Nullable IFluidHandler getCapability(Level level, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, Direction direction) {
        //for modded compatibility

        if(blockEntity instanceof ExperienceObeliskEntity obelisk && direction != Direction.UP){
            return obelisk.tank;
        }
        else{
            return null;
        }
    }

    public @Nullable IFluidHandler getCapability(Direction direction) {
        //for internal usage, if I already know i'm querying an Experience Obelisk

        if(direction != Direction.UP){
            return experienceObeliskTank();
        }
        else{
            return null;
        }
    }

    //-----------NBT-----------//

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {

        super.loadAdditional(tag, provider);

        tank.readFromNBT(provider, tag);
        this.radius = tag.getDouble("Radius");
        this.redstoneEnabled = tag.getBoolean("isRedstoneControllable");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {

        super.saveAdditional(tag, provider);

        tank.writeToNBT(provider, tag);
        tag.putDouble("Radius", radius);
        tag.putBoolean("isRedstoneControllable", redstoneEnabled);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {

        super.handleUpdateTag(tag, provider);

        tank.readFromNBT(provider, tag);
        this.radius = tag.getDouble("Radius");
        this.redstoneEnabled = tag.getBoolean("isRedstoneControllable");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {

        CompoundTag tag = super.getUpdateTag(provider);

        tank.writeToNBT(provider, tag);
        tag.putDouble("Radius", radius);
        tag.putBoolean("isRedstoneControllable", redstoneEnabled);

        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    //-----------LOGIC-----------//

    public static long getTotalXP(Player player){
        return levelsToXP(player.experienceLevel) + Math.round(player.experienceProgress * player.getXpNeededForNextLevel());
    }

    public void handleRequest(String request, int levels, ServerPlayer sender){

        long playerXP = getTotalXP(sender);
        long finalXP;

        if(Objects.equals(request, UpdateContents.FILL) && this.getSpace() != 0){

            //-----FILLING-----//

            //final amount of experience points the player will have after storing n levels
            finalXP = levelsToXP(sender.experienceLevel - levels) + Math.round(sender.experienceProgress *
                    (levelsToXP(sender.experienceLevel - levels + 1) - levelsToXP(sender.experienceLevel - levels)));

            long addAmount = (playerXP - finalXP) * 20;

            //if amount to add exceeds remaining capacity
            if(sender.experienceLevel >= levels && addAmount >= this.getSpace()){
                sender.giveExperiencePoints(-this.fill(this.getSpace()) / 20); //fill up however much is left and deduct that amount frm player
            }
            //normal operation
            else if(sender.experienceLevel >= levels){

                this.fill((int) (addAmount));
                sender.giveExperienceLevels(-levels);

            }
            //if player has less than the required XP
            else if (playerXP >= 1){

                this.fill((int) (playerXP * 20));
                sender.setExperiencePoints(0);
                sender.setExperienceLevels(0);

            }
        }

        //-----DRAINING-----//

        else if(Objects.equals(request, UpdateContents.DRAIN)){

            int amount = this.getFluidAmount();

            finalXP = levelsToXP(sender.experienceLevel + levels) + Math.round(sender.experienceProgress *
                    (levelsToXP(sender.experienceLevel + levels + 1) - levelsToXP(sender.experienceLevel + levels)));

            long drainAmount = (finalXP - playerXP) * 20;

            //normal operation
            if(amount >= drainAmount){

                this.drain((int) drainAmount);
                sender.giveExperienceLevels(levels);

            }
            else if(amount >= 1){

                sender.giveExperiencePoints(amount / 20);
                this.setFluid(0);
            }
        }

        //-----FILL OR DRAIN ALL-----//

        else if(Objects.equals(request, UpdateContents.FILL_ALL)){

            if(playerXP * 20 <= this.getSpace()){
                this.fill((int) (playerXP * 20));
                sender.setExperiencePoints(0);
                sender.setExperienceLevels(0);
            }
            else{
                sender.giveExperiencePoints(-this.getSpace() / 20);
                this.setFluid(capacity);
            }

        }
        else if(Objects.equals(request, UpdateContents.DRAIN_ALL)){

            sender.giveExperiencePoints(this.getFluidAmount() / 20);
            this.setFluid(0);
        }
    }

}


