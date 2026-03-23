plugins {
    id("java-library")
}

dependencies {
    api(project(":allib-contracts"))
    api(project(":allib-scheduler"))
    api(project(":allib-registry"))
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}
