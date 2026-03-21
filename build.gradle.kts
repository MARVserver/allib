plugins {
    `java-library`
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

val shadowJar = tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    archiveBaseName.set("allib")
    
    dependencies {
        include(project(":allib-contracts"))
        include(project(":allib-scheduler"))
        include(project(":allib-registry"))
        include(project(":allib-api"))
        include(project(":allib-adapter"))
        include(project(":allib-kotlin"))
        
        exclude("META-INF/*.RSA")
        exclude("META-INF/*.SF")
        exclude("META-INF/*.DSA")
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/DEPENDENCIES")
    }
    
    mergeServiceFiles()
}

tasks.named("build") {
    dependsOn(shadowJar)
}

tasks.register<Jar>("allibJar") {
    archiveClassifier.set("")
    archiveBaseName.set("allib")
    manifest {
        attributes["Implementation-Title"] = "Allib"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = ""
    }
}
