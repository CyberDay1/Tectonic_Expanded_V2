import org.apache.commons.lang3.StringUtils
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.bundling.Zip
import org.gradle.jvm.tasks.Jar
import org.gradle.language.jvm.tasks.ProcessResources
import org.gradle.jvm.toolchain.JavaLanguageVersion
import java.util.Properties

plugins {
    id("dev.isxander.modstitch.base") version "0.5.15-unstable"
    id("dev.isxander.modstitch.publishing") version "0.5.15-unstable"
}

val cleanModVersion = project.findProperty("mod_version")?.toString() ?: "2.0.0"
val minecraftVersionProperty = project.property("minecraft_version") as String

group = "com.cyberday1"
version = "${cleanModVersion}+mc${minecraftVersionProperty}-neoforge"

base {
    archivesName.set("TectonicExpanded")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

fun prop(name: String, consumer: (prop: String) -> Unit) {
    (findProperty(name) as? String?)
        ?.let(consumer)
}

val declaredModVersion = cleanModVersion
require(version.toString().startsWith("$declaredModVersion+")) {
    "Root project version ($version) must begin with the mod_version property ($declaredModVersion)."
}

val modVersion = declaredModVersion
val minecraft = property("deps.minecraft") as String

val loader = when {
    modstitch.isModDevGradle -> "neoforge"
    else -> error("Unknown loader")
}

modstitch {
    minecraftVersion = minecraft

    // Alternatively use stonecutter.eval if you have a lot of versions to target.
    // https://stonecutter.kikugie.dev/stonecutter/guide/setup#checking-versions
    javaTarget = 21

    // If parchment doesnt exist for a version yet you can safely
    // omit the "deps.parchment" property from your versioned gradle.properties
    parchment {
        prop("deps.parchment") { mappingsVersion = it }
    }

    // This metadata is used to fill out the information inside
    // the metadata files found in the templates folder.
    metadata {
        modId = "theexpanse"
        modName = "The Expanse"
        modVersion = cleanModVersion
        modGroup = "com.cyberday1"

        fun <K, V> MapProperty<K, V>.populate(block: MapProperty<K, V>.() -> Unit) {
            block()
        }

        replacementProperties.populate {
            // You can put any other replacement properties/metadata here that
            // modstitch doesn't initially support. Some examples below.
            put("mod_issue_tracker", "https://github.com/CyberDay1/TheExpanse/issues")
        }
    }

    // ModDevGradle (NeoForge, Forge, Forgelike)
    moddevgradle {
        enable {
            prop("deps.forge") { forgeVersion = it }
            prop("deps.neoform") { neoFormVersion = it }
            prop("deps.neoforge") { neoForgeVersion = it }
            prop("deps.mcp") { mcpVersion = it }
        }

        // Configures client and server runs for MDG, it is not done by default
        defaultRuns()

        // This block configures the `neoforge` extension that MDG exposes by default,
        // you can configure MDG like normal from here
        configureNeoforge {
            runs.all {
                disableIdeRun()
            }
        }
    }

    mixin {
        // You do not need to specify mixins in any mods.json/toml file if this is set to
        // true, it will automatically be generated.
        addMixinsToModManifest = true

        configs.register("theexpanse")

        if (minecraft == "1.21.1") configs.register("theexpanse_1.21.1")
        if (minecraft == "1.21.5") configs.register("theexpanse_1.21.5")

        // Most of the time you wont ever need loader specific mixins.
        // If you do, simply make the mixin file and add it like so for the respective loader:
        // if (isLoom) configs.register("examplemod-fabric")
        // if (isModDevGradleRegular) configs.register("examplemod-neoforge")
        // if (isModDevGradleLegacy) configs.register("examplemod-forge")
    }
}

// Stonecutter constants for mod loaders.
// See https://stonecutter.kikugie.dev/stonecutter/guide/comments#condition-constants
val constraint: String = name.split("-").getOrElse(1) { "" }
stonecutter {
    consts(
        "neoforge" to (constraint == "neoforge")
    )
}

// All dependencies should be specified through modstitch's proxy configuration.
// Wondering where the "repositories" block is? Go to "stonecutter.gradle.kts"
// If you want to create proxy configurations for more source sets, such as client source sets,
// use the modstitch.createProxyConfigurations(sourceSets["client"]) function.
dependencies {
    modstitchModImplementation("maven.modrinth:lithostitched:${property("deps.lithostitched")}")

    //modstitchModImplementation("maven.modrinth:terralith:${property("deps.terralith")}")
    //modstitchModImplementation("maven.modrinth:clifftree:${property("deps.clifftree")}")
}

configurations.configureEach {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.ow2.asm" && requested.name == "asm") {
            useVersion("9.7")
            because("Align ASM dependency versions for NeoForge tooling")
        }
    }
}

tasks.matching { it.name == "createMinecraftArtifacts" }.configureEach {
    outputs.cacheIf { true }
}

tasks {
    modstitch.finalJarTask {
        archiveVersion.set("${modVersion}+mc${minecraft}-neoforge")
    }
}

tasks.register("validateModVersion") {
    doLast {
        val props = Properties()
        file("gradle.properties").inputStream().use { props.load(it) }
        val expected = props.getProperty("mod_version")?.trim()

        val modsTomlFiles = listOf(
            "versions/1.21.1-neoforge/src/main/resources/META-INF/neoforge.mods.toml",
            "versions/1.21.5-neoforge/src/main/resources/META-INF/neoforge.mods.toml"
        )

        modsTomlFiles.forEach { path ->
            val modsToml = file(path)
            if (modsToml.exists()) {
                val actual = modsToml.readLines()
                    .firstOrNull { it.trim().startsWith("version=") }
                    ?.substringAfter('=')
                    ?.trim()
                    ?.trim('"')

                if (actual != expected) {
                    throw GradleException("Version mismatch: gradle.properties has '$expected' but $path has '$actual'")
                }
            }
        }
    }
}

tasks.register("validateMixinPaths") {
    doLast {
        fileTree("versions").matching { include("**/*.mixins.json") }.forEach { f ->
            val text = f.readText()
            if (text.contains("dev.worldgen")) {
                throw GradleException("Invalid mixin path found in $f: contains 'dev.worldgen'")
            }
        }
    }
}

if (name == "1.21.1-neoforge" || name == "1.21.5-neoforge") {
    val mcVersion = property("deps.minecraft") as String

    group = "com.cyberday1"
    version = "${modVersion}+mc${mcVersion}-neoforge"

    tasks.withType<ProcessResources>().configureEach {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        filesMatching("META-INF/neoforge.mods.toml") {
            expand(mapOf("mod_version" to modVersion))
        }
    }

    tasks.named<Jar>("jar") {
        archiveBaseName.set("TectonicExpanded")
        archiveVersion.set("${modVersion}+mc${mcVersion}-neoforge")
    }

    tasks.register("sanityCompile") {
        dependsOn("compileJava")
    }
}

if (name == "1.21.5-neoforge") {
    val targets = listOf(":1.21.1-neoforge", ":1.21.5-neoforge")

    tasks.register("distZipAll", Zip::class) {
        archiveBaseName.set("TectonicExpanded")
        archiveVersion.set(version.toString())
        destinationDirectory.set(rootProject.layout.buildDirectory.dir("dist"))
        dependsOn(targets.map { "$it:build" })

        targets.forEach { path ->
            from(project(path).layout.buildDirectory.dir("libs")) {
                include("*.jar")
            }
        }

        from(rootProject.layout.projectDirectory.file("LICENSE"))
        into("/")
    }

    tasks.named("build") {
        finalizedBy("distZipAll")
    }
}

publishMods {
    changelog = """
        A changelog for sure!
    """.trimIndent()
    type = BETA
    modLoaders.add(loader)
    file = modstitch.finalJarTask.flatMap { it.archiveFile }
    displayName = "v%s ~ %s %s".format(modVersion, StringUtils.capitalize(loader), property("deps.minecraft"))

    dryRun = false

    modrinth {
        accessToken.set(providers.environmentVariable("TOKEN_MR"))
        projectId.set("lWDHr9jE")

        if (minecraft == "1.21.1") minecraftVersions.add("1.21.1")
        if (minecraft == "1.21.5") minecraftVersions.add("1.21.5")

        requires("lithostitched")
        optional("worldgen-patches")
        incompatible("continents")
    }

    curseforge {
        accessToken.set(providers.environmentVariable("TOKEN_CF"))
        projectId.set("686836")

        if (minecraft == "1.21.1") minecraftVersions.add("1.21.1")
        if (minecraft == "1.21.5") minecraftVersions.add("1.21.5")

        requires("lithostitched")
        optional("worldgen-patches")
        incompatible("continents")
    }
}

tasks.named("build") {
    dependsOn("validateModVersion")
    dependsOn("validateMixinPaths")
}
