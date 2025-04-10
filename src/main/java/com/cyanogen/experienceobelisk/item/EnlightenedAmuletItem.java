package com.cyanogen.experienceobelisk.item;

import com.cyanogen.experienceobelisk.config.Config;
import com.cyanogen.experienceobelisk.registries.RegisterSounds;
import com.cyanogen.experienceobelisk.utils.ItemUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static com.cyanogen.experienceobelisk.block_entities.ExperienceFountainEntity.customName;

public class EnlightenedAmuletItem extends Item{

    public EnlightenedAmuletItem(Properties p) {
        super(p);
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {

        ItemStack stack = new ItemStack(this);
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("isActive", false);

        ItemUtils.saveCustomDataTag(stack, tag);

        return stack;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = ItemUtils.getCustomDataTag(stack);

        if(player.isShiftKeyDown()){
            boolean isActive = tag.getBoolean("isActive");
            if(isActive){
                tag.putBoolean("isActive", false);
                player.playSound(RegisterSounds.ENLIGHTENED_AMULET_DEACTIVATE.get(), 0.2f,0.8f);
            }
            else{
                tag.putBoolean("isActive", true);
                player.playSound(RegisterSounds.ENLIGHTENED_AMULET_ACTIVATE.get(), 0.2f,1f);
            }
            ItemUtils.saveCustomDataTag(stack, tag);
        }

        return super.use(level, player, hand);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return ItemUtils.getCustomDataTag(stack).getBoolean("isActive");
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isCurrentItem) {

        boolean isActive = ItemUtils.getCustomDataTag(stack).getBoolean("isActive");

        if(entity instanceof Player player && isActive && !level.isClientSide && level.getGameTime() % 10 == 0){

            final double radius = Config.COMMON.amuletRange.get();

            Vec3 pos = player.position();
            AABB area = new AABB(
                    pos.x() - radius,
                    pos.y() - radius,
                    pos.z() - radius,
                    pos.x() + radius,
                    pos.y() + radius,
                    pos.z() + radius);

            List<ExperienceOrb> list = level.getEntitiesOfClass(ExperienceOrb.class, area);

            int totalValue = 0;
            if(!list.isEmpty()){
                for(int i = 0; i < Math.min(30,list.size()); i++) {

                    ExperienceOrb orb = list.get(i);
                    CompoundTag tag = new CompoundTag();
                    orb.addAdditionalSaveData(tag);

                    boolean spawnedFromFountain = orb.hasCustomName() && Objects.equals(orb.getCustomName(), customName);
                    boolean ignore = Config.COMMON.amuletIgnoresFountainOrbs.get();
                    boolean shouldCollect = !(ignore && spawnedFromFountain);

                    if(shouldCollect){
                        int value = orb.value;
                        int count = tag.getInt("Count");
                        totalValue += value * count;
                        orb.discard();
                    }
                }

                ServerLevel server = (ServerLevel) level;

                if(totalValue < 32768){
                    ExperienceOrb orb = new ExperienceOrb(server, pos.x(), pos.y(), pos.z(), totalValue);
                    server.addFreshEntity(orb);
                }
                else{ //edge case if total value of orbs exceeds 32767
                    while(totalValue > 0){
                        int v = Math.min(totalValue, 32767);
                        ExperienceOrb orb = new ExperienceOrb(server, pos.x(), pos.y(), pos.z(), v);
                        server.addFreshEntity(orb);
                        totalValue = totalValue - v;
                    }
                }
            }
        }

        super.inventoryTick(stack, level, entity, slot, isCurrentItem);
    }

    //-----CUSTOM HOVER TEXT-----//

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        boolean isActive = ItemUtils.getCustomDataTag(stack).getBoolean("isActive");

        if(isActive){
            tooltipComponents.add(Component.translatable("tooltip.experienceobelisk.enlightened_amulet.active"));
        }
        else{
            tooltipComponents.add(Component.translatable("tooltip.experienceobelisk.enlightened_amulet.inactive"));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

}
