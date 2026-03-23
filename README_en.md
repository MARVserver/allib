# Allib
[![ja](https://img.shields.io/badge/lang-ja-blue.svg)](./README.md)

![Allib Image](./image.png)

Allib is a powerful, generic library and core plugin designed for Minecraft servers (Folia, Paper, and Spigot).
It abstracts the differences between server engines, providing an environment where developers can focus solely on their plugin's logic.

## 🌟 Key Features

- **🚀 Region-Aware Scheduler**: Schedule tasks safely across Folia's region-based scheduling or Paper/Spigot's standard scheduling using a unified API.
- **🛡️ Service Registry**: Achieve loosely coupled instance management across different modules and plugins.
- **🔥 Kotlin Coroutines Support**: Provides an asynchronous processing DSL deeply integrated with the scheduler.
- **⚙️ Integrated Plugin**: Can run independently as a plugin or be depended upon as a shared library by other plugins.

## 📦 Building and Integration

### Build
Build a FatJar containing all dependencies.
```bash
./gradlew shadowJar
```
Output destination: `build/libs/allib-1.0.0.jar`
*(Note: Use `1.1.0` if you're using the latest versions configured)*

### Integration
1. Place the generated JAR file into the `plugins` folder.
2. Add `depend: [Allib]` to your own plugin's `plugin.yml`.

## 📖 Usage (Overview)

### Scheduler (Java Example)
```java
Alib.scheduler().runRegion(location, () -> {
    // Safely executes within the correct region on Folia, or on the main thread for Paper/Spigot
});
```

## 📦 Using as a Dependency

### Gradle (Kotlin DSL)
```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    // API only
    compileOnly("com.github.MARVserver.allib:allib-api:1.1.0")
    // Include Kotlin DSL
    compileOnly("com.github.MARVserver.allib:allib-kotlin:1.1.0")
}
```

### Maven (pom.xml)
```xml
<dependency>
    <groupId>com.github.MARVserver.allib</groupId>
    <artifactId>allib-api</artifactId>
    <version>1.1.0</version>
    <scope>provided</scope>
</dependency>
```

For more detailed technical documentation, please refer to the [Wiki](./wiki/Home.md) (Currently available in Japanese).
