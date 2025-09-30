package com.cyberday1.theexpanse.litho.worldgen.placementcondition;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;

public final class AllOfPlacementCondition implements PlacementCondition {
    public static final MapCodec<AllOfPlacementCondition> CODEC = PlacementCondition.BASE_CODEC.listOf().fieldOf("conditions").xmap(AllOfPlacementCondition::new, AllOfPlacementCondition::conditions);

    private final List<PlacementCondition> conditions;

    public AllOfPlacementCondition(List<PlacementCondition> conditions) {
        this.conditions = new ArrayList<>(conditions);
    }

    public List<PlacementCondition> conditions() {
        return this.conditions;
    }

    public void appendCondition(PlacementCondition condition) {
        this.conditions.add(condition);
    }

    @Override
    public boolean test(Context context, BlockPos pos) {
        for (PlacementCondition condition : this.conditions) {
            if (!condition.test(context, pos)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public MapCodec<? extends PlacementCondition> codec() {
        return CODEC;
    }
}
