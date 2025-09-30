import org.gradle.jvm.tasks.Jar

val modVersion = rootProject.property("mod_version") as String
val mcVersion = property("deps.minecraft") as String

group = "com.cyberday1"
version = "${modVersion}+mc${mcVersion}-neoforge"

neoForge {
    version = "21.1.39-beta"
    neoFormVersion = "1.21.1-20240808.144430"
}

dependencies {
    implementation("org.spongepowered:mixin:0.8.5")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.github.zafarkhaja:java-semver:0.10.2")
    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("org.antlr:antlr4-runtime:4.13.1")
}

tasks.named<Jar>("jar") {
    archiveBaseName.set("TectonicExpanded")
    archiveVersion.set("${modVersion}+mc${mcVersion}-neoforge")
}

tasks.register("sanityCompile") {
    dependsOn("compileJava")
}
