//? if fabric && 1.20.1 {
/*package dev.worldgen.theexpanse.loaders.fabric;

import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TheExpanseRepositorySource implements RepositorySource {
    @Override
    public void loadPacks(@NotNull Consumer<Pack> consumer) {
        TheExpanseFabric.PACKS.forEach(consumer);
    }
}
*///?}