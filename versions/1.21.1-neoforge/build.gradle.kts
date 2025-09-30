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
    implementation("com.github.LlamaLad7:MixinExtras:73e5b74")
}

tasks.named<Jar>("jar") {
    archiveBaseName.set("TectonicExpanded")
    archiveVersion.set("${modVersion}+mc${mcVersion}-neoforge")
}

tasks.register("sanityCompile") {
    dependsOn("compileJava")
}
