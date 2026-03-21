plugins {
    id("java-library")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
}

artifacts {
    archives jar
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}

tasks.withType<PublishToMavenLocal> {
    dependsOn(tasks.withType<Jar::class>)
}
