import org.gradle.jvm.tasks.Jar

val mcVersion = property("deps.minecraft") as String

group = "com.cyberday1"
version = "2.0.0+mc${mcVersion}-neoforge"

neoForge {
    version = "21.5.39-beta"
    neoFormVersion = "1.21.5-20250325.162830"
}

tasks.named<Jar>("jar") {
    archiveBaseName.set("TectonicExpanded")
}

tasks.register("sanityCompile") {
    dependsOn("compileJava")
}
