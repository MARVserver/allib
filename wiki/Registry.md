# Service Registry

Allib は、DI（依存性の注入）の代わりに利用できる軽量なサービスレジストリを提供します。

## 🎯 登録

```java
AlibRegistry.register(IMyService.class, new MyServiceImpl());
```

## 🔍 取得

```java
Optional<IMyService> service = Alib.getService(IMyService.class);
```

## ⚡ バージョン管理
特定のバージョン範囲に一致するサービスを要求することができます。

```java
// "1.2.0" 以上の実装を要求
Optional<IMyService> service = Alib.getServiceVersioned(IMyService.class, ">=1.2.0");
```
