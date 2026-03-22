# Scheduler Bridge

Allib のスケジューラーは、マルチスレッドサーバー Folia と、シングルスレッドベースの Paper/Spigot の両方に対応しています。

## 🛠️ 基本的なメソッド

### `runRegion(Location loc, Runnable task)`
- **Folia**: 指定された `Location` が属するリージョンのスケジューラーで実行。
- **Paper/Spigot**: メインスレッドで同期的に実行。

### `runAsync(Runnable task)`
- 全てのプラットフォームで非同期スレッドを使用して実行。Java 21 以降では仮想スレッド（Virtual Threads）が試行されます。

### `runLater(long ticks, Runnable task)`
- 指定されたチック数（Tick）の遅延後に非同期実行。

---

## 💎 Kotlin DSL

Kotlin ユーザーは `allib-kotlin` モジュールの DSL を利用できます。

```kotlin
launch {
    // 非同期で 20 チック待機
    delay(20)
    
    // リージョン内にコンテキストを切り替え
    region(location) {
        // 安全にブロック操作やエンティティ操作が可能
    }
}
```
