//? if fabric {
/*package dev.worldgen.tectonic.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
//? if >1.20.1 {
/^import dev.worldgen.tectonic.client.gui.ConfigScreen;
 ^///?} else {
import dev.worldgen.tectonic.client.old.gui.ConfigScreen;
//?}

public class TectonicModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::new;
    }
}
*///?}
