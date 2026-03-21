plugins {
    id("java-library")
}

repositories {
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://mvn.luckperms.net/releases")
}

dependencies {
    compileOnly(project(":allib-contracts"))
    compileOnly(project(":allib-registry"))
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
    compileOnly("net.luckperms:api:5.4")
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}
