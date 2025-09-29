//? if fabric {
package dev.worldgen.theexpanse.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
//? if >1.20.1 {
import dev.worldgen.theexpanse.client.gui.ConfigScreen;
 //?} else {
/*import dev.worldgen.theexpanse.client.old.gui.ConfigScreen;
*///?}

public class TheExpanseModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::new;
    }
}
//?}
