# Allib
[![en](https://img.shields.io/badge/lang-en-red.svg)](./README_en.md)

![Allib Image](./image.png)

Allib は、Minecraft サーバー（Folia, Paper, Spigot）向けの強力な汎用ライブラリおよびコアプラグインです。
サーバーエンジンの差異を吸収し、開発者がロジックに集中できる環境を提供します。

## 🌟 主な機能

- **🚀 Region-Aware Scheduler**: Folia のリージョンベース、Paper/Spigot の標準的なスケジューリングを同一の API で操作可能。
- **🛡️ Service Registry**: モジュール間やプラグイン間での疎結合なインスタンス管理を実現。
- **🔥 Kotlin Coroutines Support**: スケジューラーと統合された非同期処理 DSL を提供。
- **⚙️ Integrated Plugin**: 単体でプラグインとして動作し、他のプラグインから共通ライブラリとして依存可能。

## 📦 ビルドと導入

### ビルド
全ての依存関係を内包した FatJar をビルドします。
```bash
./gradlew shadowJar
```
出力先: `build/libs/allib-1.0.0.jar`

### 導入
1. 生成された JAR を `plugins` フォルダに入れます。
2. 自身のプラグインの `plugin.yml` に `depend: [Allib]` を追加します。

## 📖 使い方 (概要)

### スケジューラー (例: Java)
```java
Alib.scheduler().runRegion(location, () -> {
    // Folia ではリージョン内、Paper/Spigot ではメインスレッドで安全に実行
});
```

## 📦 依存関係としての利用

### Gradle (Kotlin DSL)
```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    // APIのみ
    compileOnly("com.github.MARVserver.allib:allib-api:1.1.0")
    // Kotlin DSLを含む場合
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

詳細なドキュメントは [Wiki](./wiki/Home.md) を参照してください。