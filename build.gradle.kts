plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("io.github.apdevteam.github-packages") version "1.2.2"
}

repositories {
    gradlePluginPortal()
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven { githubPackage("apdevteam/movecraft")(this) }
    maven { githubPackage("apdevteam/movecraft-worldguard")(this) }
    maven("https://jitpack.io")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    api("org.jetbrains:annotations-java5:24.1.0")
    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")
    compileOnly("net.countercraft:movecraft:+")
    compileOnly("net.countercraft.movecraft.worldguard:movecraft-worldguard:+")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.10")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
}

group = "com.snowleopard"
version = "3.0.0_beta-4"
description = "APTurrets"
java.toolchain.languageVersion = JavaLanguageVersion.of(21)

tasks.reobfJar {
    outputJar = layout.buildDirectory.file("libs/AP-Turrets.jar")
}

tasks.processResources {
    from(rootProject.file("LICENSE.md"))
    filesMatching("*.yml") {
        expand(mapOf("projectVersion" to project.version))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.snowleopard"
            artifactId = "ap-turrets"
            version = "${project.version}"

            artifact(tasks.reobfJar)
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/apdevteam/ap-turrets")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
