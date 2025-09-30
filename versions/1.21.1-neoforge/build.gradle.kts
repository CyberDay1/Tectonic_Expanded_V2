import org.gradle.jvm.tasks.Jar

val mcVersion = property("deps.minecraft") as String

group = "com.cyberday1"
version = "2.0.0+mc${mcVersion}-neoforge"

neoForge {
    version = "21.1.39-beta"
    neoFormVersion = "1.21.1-20240808.144430"
}

tasks.named<Jar>("jar") {
    archiveBaseName.set("TectonicExpanded")
}

tasks.register("sanityCompile") {
    dependsOn("compileJava")
}
