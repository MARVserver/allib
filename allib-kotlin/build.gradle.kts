plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
}

dependencies {
    api(project(":allib-contracts"))
    api(project(":allib-scheduler"))
    api(project(":allib-registry"))
    api(project(":allib-api"))
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

kotlin {
    jvmToolchain(21)
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
