package com.cyberday1.theexpanse.litho.worldgen.placementmodifier;

import com.cyberday1.theexpanse.litho.worldgen.placementcondition.PlacementCondition;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public final class ConditionPlacement extends PlacementFilter {
    public static final MapCodec<ConditionPlacement> CODEC = PlacementCondition.CODEC.fieldOf("condition").xmap(ConditionPlacement::new, ConditionPlacement::condition);
    public static final PlacementModifierType<ConditionPlacement> TYPE = () -> CODEC;

    private final PlacementCondition condition;

    public ConditionPlacement(PlacementCondition condition) {
        this.condition = condition;
    }

    public PlacementCondition condition() {
        return this.condition;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        return this.condition.test(context, pos);
    }

    @Override
    public PlacementModifierType<?> type() {
        return TYPE;
    }
}
