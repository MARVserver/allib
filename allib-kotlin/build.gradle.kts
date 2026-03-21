plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}

dependencies {
    compileOnly(project(":allib-contracts"))
    compileOnly(project(":allib-scheduler"))
    compileOnly(project(":allib-registry"))
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

kotlin {
    jvmToolchain(21)
}

artifacts {
    archives jar
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
