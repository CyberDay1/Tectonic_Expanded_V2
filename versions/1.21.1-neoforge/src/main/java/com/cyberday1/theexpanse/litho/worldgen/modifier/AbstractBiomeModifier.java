package com.cyberday1.theexpanse.litho.worldgen.modifier;

import net.neoforged.neoforge.common.world.BiomeModifier;

public abstract class AbstractBiomeModifier implements Modifier {
    private final BiomeModifier neoforgeBiomeModifier;

    protected AbstractBiomeModifier(BiomeModifier neoforgeBiomeModifier) {
        this.neoforgeBiomeModifier = neoforgeBiomeModifier;
    }

    public BiomeModifier neoforgeBiomeModifier() {
        return this.neoforgeBiomeModifier;
    }

    @Override
    public ModifierPhase getPhase() {
        return ModifierPhase.NONE;
    }

    @Override
    public void applyModifier() {
    }
}
