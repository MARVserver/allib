plugins {
    id("java-library")
}

dependencies {
    compileOnly(project(":allib-contracts"))
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
}

tasks.named<JavaCompile>("compileJava") {
    exclude("**/FoliaSchedulerProvider.java")
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}
