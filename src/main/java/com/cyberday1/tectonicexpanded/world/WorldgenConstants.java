package com.cyberday1.tectonicexpanded.world;

public class WorldgenConstants {
    // CUSTOM: extended vertical range constants
    public static final int OVERWORLD_MIN_Y = -256;
    public static final int OVERWORLD_HEIGHT = 2000;
    public static final int OVERWORLD_LOGICAL_HEIGHT = 2000;
    public static final int OVERWORLD_MAX_Y = OVERWORLD_MIN_Y + OVERWORLD_HEIGHT - 1;

    // Section calculations
    public static final int SECTION_HEIGHT = 16;
    public static final int OVERWORLD_SECTION_COUNT = OVERWORLD_HEIGHT / SECTION_HEIGHT;

    // Helper methods
    public static int getSectionIndex(int y) {
        return (y - OVERWORLD_MIN_Y) / SECTION_HEIGHT;
    }

    public static boolean isInBounds(int y) {
        return y >= OVERWORLD_MIN_Y && y <= OVERWORLD_MAX_Y;
    }
}
