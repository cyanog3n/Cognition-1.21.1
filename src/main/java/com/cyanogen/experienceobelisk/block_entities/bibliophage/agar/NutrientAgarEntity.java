package com.cyanogen.experienceobelisk.block_entities.bibliophage.agar;

import com.cyanogen.experienceobelisk.registries.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class NutrientAgarEntity extends AbstractAgarEntity {

    public NutrientAgarEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntities.NUTRIENT_AGAR.get(), pos, state);
        infectivity = 0.005;
    }

}
