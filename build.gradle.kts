plugins {
    `java-library`
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `maven-publish`
}

group = "marv.allib"
version = "1.1.0"

repositories {
    mavenCentral()
    maven("https://mvn.lumine.io/repository/maven-public/")
}

dependencies {
    implementation(project(":allib-contracts"))
    implementation(project(":allib-scheduler"))
    implementation(project(":allib-registry"))
    implementation(project(":allib-api"))
    implementation(project(":allib-adapter"))
    implementation(project(":allib-kotlin"))
}

subprojects {
    apply(plugin = "maven-publish")
    group = "marv.allib"
    version = "1.1.0"

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://repo.dmulloy2.net/repository/public/")
        maven("https://nexus.lucko.me/repository/maven-releases/")
        maven("https://jitpack.io")
    }
}

// Set a default version to avoid manifest errors
version = "1.0.0"

tasks.named<Jar>("jar") {
    enabled = false
}

val shadowJar = tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    archiveBaseName.set("allib")
    
    manifest {
        attributes["Implementation-Title"] = "Allib"
        attributes["Implementation-Version"] = project.version
        attributes["Main-Class"] = "marv.allib.plugin.AllibPlugin"
    }
    
    // Essential for shading libraries like Kotlin and SPI/Service loading
    mergeServiceFiles()
    
    // Standard shadowing excludes
    exclude("META-INF/*.RSA")
    exclude("META-INF/*.SF")
    exclude("META-INF/*.DSA")
    exclude("META-INF/INDEX.LIST")
    exclude("META-INF/DEPENDENCIES")
}

tasks.named("build") {
    dependsOn(shadowJar)
}
