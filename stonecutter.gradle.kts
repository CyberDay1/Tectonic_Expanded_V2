plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.21.5-neoforge"

stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) {
    group = "project"
    ofTask("build")
}

allprojects {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.neoforged.net/releases")
        }
        maven {
            url = uri("https://maven.parchmentmc.org")
        }
        maven {
            url = uri("https://jitpack.io")
            // fallback for MixinExtras
        }
        mavenLocal()
    }
}

tasks.register("distZipAll") {
    dependsOn(":1.21.5-neoforge:distZipAll")
}
