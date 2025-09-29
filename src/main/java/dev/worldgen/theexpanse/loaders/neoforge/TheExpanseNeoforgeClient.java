//? if neoforge {
/*package dev.worldgen.theexpanse.loaders.neoforge;

import dev.worldgen.theexpanse.TheExpanse;
import dev.worldgen.theexpanse.client.gui.ConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = TheExpanse.MOD_ID, dist = Dist.CLIENT)
public class TheExpanseNeoforgeClient {
    public TheExpanseNeoforgeClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, parent) -> new ConfigScreen(parent));
    }
}
*///?}