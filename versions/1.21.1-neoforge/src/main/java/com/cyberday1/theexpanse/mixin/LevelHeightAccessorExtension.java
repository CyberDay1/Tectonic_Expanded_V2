package com.cyberday1.theexpanse.mixin;

/**
 * Bridge interface that exposes the NeoForge 1.21.1 height accessors while compiling against
 * the existing LevelHeightAccessor type. Implemented via mixin to keep call sites using the
 * new {@code getMinY}/{@code getMaxY} contract without introducing reflection.
 */
public interface LevelHeightAccessorExtension {
    int getMinY();

    int getMaxY();
}
