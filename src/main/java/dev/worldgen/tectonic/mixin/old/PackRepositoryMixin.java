//? if fabric && 1.20.1 {
package dev.worldgen.tectonic.mixin.old;

import dev.worldgen.tectonic.loaders.fabric.TectonicRepositorySource;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Mixin(value = PackRepository.class, priority = 2000)
public class PackRepositoryMixin {

    @Shadow
    @Final
    @Mutable
    private Set<RepositorySource> sources;


    // To circumvent Fabric's lack of built-in pack ordering, Tectonic's built-in packs are in a separate repository source.
    @Inject(method = "<init>", at = @At("RETURN"))
    private void tectonic$addTectonicSource(RepositorySource[] repositorySources, CallbackInfo ci) {
        sources = new LinkedHashSet<>(sources);
        for (RepositorySource source : sources) {
            if (source instanceof BuiltInPackSource builtInSource && ((BuiltInPackSourceAccessor)builtInSource).getPackType() == PackType.SERVER_DATA) {
                sources.add(new TectonicRepositorySource());
                break;
            }
        }
    }

    // Swap out the TreeMap for a LinkedHashMap to prevent ordering issues.
    @ModifyVariable(method = "discoverAvailable", at = @At("STORE"), ordinal = 0)
    private Map<String, Pack> tectonic$linkedAvailableMap(Map<String, Pack> value) {
        return new LinkedHashMap<>();
    }
}
//?}