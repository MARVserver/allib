plugins {
    id("java-library")
}

dependencies {
    compileOnly(project(":allib-api"))
    compileOnly(project(":allib-contracts"))
    compileOnly(project(":allib-registry"))
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    compileOnly("net.dmulloy2:ProtocolLib:5.3.0")
    compileOnly("net.luckperms:api:5.4")
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}
