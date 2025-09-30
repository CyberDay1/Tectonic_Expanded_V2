package com.cyberday1.theexpanse.litho.worldgen.modifier.util;

import com.cyberday1.theexpanse.litho.worldgen.densityfunction.MarkerFunction;
import com.cyberday1.theexpanse.litho.worldgen.densityfunction.MergedDensityFunction;
import com.cyberday1.theexpanse.litho.worldgen.densityfunction.OriginalMarkerDensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;

public final class DensityFunctionWrapper {
    private DensityFunctionWrapper() {
    }

    public static DensityFunction wrap(DensityFunction wrapped, DensityFunction wrapper) {
        if (wrapped instanceof MergedDensityFunction merged) {
            DensityFunction original = merged.original();
            return new MergedDensityFunction(original, wrapped, wrapper.mapAll(value -> {
                if (isMarker(value)) {
                    if (value instanceof OriginalMarkerDensityFunction) {
                        return original;
                    }
                    return wrapped;
                }
                return value;
            }));
        }

        return new MergedDensityFunction(wrapped, wrapped, wrapper.mapAll(value -> {
            if (isMarker(value)) {
                return wrapped;
            }
            return value;
        }));
    }

    private static boolean isMarker(DensityFunction function) {
        return function instanceof DensityFunctions.HolderHolder holder && holder.function().value() instanceof MarkerFunction;
    }
}
