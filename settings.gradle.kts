pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()

        // Modstitch
        maven("https://maven.isxander.dev/releases/")

        // MDG platform
        maven("https://maven.neoforged.net/releases/")

        // Modstitch has a transitive dependency on Fabric Loom for tooling APIs.
        // Keeping the FabricMC repository ensures the plugin resolves without
        // reintroducing Fabric modules to the project itself.
        maven("https://maven.fabricmc.net/")

        // Stonecutter
        maven("https://maven.kikugie.dev/releases")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.6+"
}

buildCache {
    local {
        isEnabled = true
    }
}

stonecutter {
    kotlinController = true
    centralScript = "build.gradle.kts"

    create(rootProject) {
        /**
         * @param mcVersion The base minecraft version.
         * @param loaders A list of loaders to target, supports "neoforge"(1.20.6+), "fabric" (1.14+), "vanilla"(any) or "forge" (<=1.20.1)
         */
        fun mc(mcVersion: String, name: String = mcVersion, loaders: Iterable<String>) =
            loaders.forEach { vers("$name-$it", mcVersion) }

        // Configure your targets here!
        mc("1.21.5", loaders = listOf("neoforge"))
        mc("1.21.1", loaders = listOf("neoforge"))

        // This is the default target.
        // https://stonecutter.kikugie.dev/stonecutter/guide/setup#settings-settings-gradle-kts
        vcsVersion = "1.21.5-neoforge"
    }
}

rootProject.name = "the-expanse"
