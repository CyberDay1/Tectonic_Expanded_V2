import org.gradle.jvm.tasks.Jar

val modVersion = rootProject.property("mod_version") as String
val mcVersion = property("deps.minecraft") as String

group = "com.cyberday1"
version = "${modVersion}+mc${mcVersion}-neoforge"

neoForge {
    version = "21.5.39-beta"
    neoFormVersion = "1.21.5-20250325.162830"
}

tasks.named<Jar>("jar") {
    archiveBaseName.set("TectonicExpanded")
    archiveVersion.set("${modVersion}+mc${mcVersion}-neoforge")
}

tasks.register("sanityCompile") {
    dependsOn("compileJava")
}
