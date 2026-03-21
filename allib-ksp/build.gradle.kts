plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
}

dependencies {
    compileOnly(project(":allib-contracts"))
    compileOnly("com.google.auto.service:auto-service-annotations:1.1.1")
    compileOnly("com.squareup:kotlinpoet:1.16.0")
    compileOnly("com.squareup:kotlinpoet-ksp:1.16.0")
    compileOnly("com.google.devtools.ksp:symbol-processing-api:1.9.22-1.0.17")
    ksp("com.google.auto.service:auto-service:1.1.1")
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
