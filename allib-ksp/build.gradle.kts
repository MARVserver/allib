plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    kotlin("kapt")
}

dependencies {
    implementation(project(":allib-contracts"))
    implementation("com.google.auto.service:auto-service-annotations:1.1.1")
    implementation("com.squareup:kotlinpoet:1.16.0")
    implementation("com.squareup:kotlinpoet-ksp:1.16.0")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.22-1.0.17")
    kapt("com.google.auto.service:auto-service:1.1.1")
}

kotlin {
    jvmToolchain(21)
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
