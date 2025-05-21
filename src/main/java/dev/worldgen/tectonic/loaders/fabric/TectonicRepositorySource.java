//? if fabric && 1.20.1 {
package dev.worldgen.tectonic.loaders.fabric;

import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TectonicRepositorySource implements RepositorySource {
    @Override
    public void loadPacks(@NotNull Consumer<Pack> consumer) {
        TectonicFabric.PACKS.forEach(consumer);
    }
}
//?}