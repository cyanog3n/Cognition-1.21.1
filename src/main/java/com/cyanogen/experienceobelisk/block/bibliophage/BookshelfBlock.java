package com.cyanogen.experienceobelisk.block.bibliophage;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class BookshelfBlock extends Block {

    public final float enchantPowerBonus;

    public BookshelfBlock(float enchantPowerBonus) {
        super(Properties.of().mapColor(MapColor.WOOD).strength(1.5F).sound(SoundType.WOOD).ignitedByLava());
        this.enchantPowerBonus = enchantPowerBonus;
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
        return enchantPowerBonus;
    }


}
