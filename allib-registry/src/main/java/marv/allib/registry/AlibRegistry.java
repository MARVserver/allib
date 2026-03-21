package marv.allib.registry;

import marv.allib.contracts.IAlibService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class AlibRegistry {

    private static final Map<Class<?>, List<ServiceEntry<?>>> SERVICES = new ConcurrentHashMap<>();
    private static final LifecycleManager LIFECYCLE_MANAGER = new LifecycleManager();

    public static <T> void register(Class<T> serviceClass, T instance, String version) {
        register(serviceClass, instance, version, getCurrentPluginId());
    }

    public static <T> void register(Class<T> serviceClass, T instance, String version, String pluginId) {
        ServiceEntry<T> entry = new ServiceEntry<>(serviceClass, instance, version, pluginId);
        SERVICES.computeIfAbsent(serviceClass, k -> new ArrayList<>()).add(entry);

        if (instance instanceof IAlibService) {
            LIFECYCLE_MANAGER.track(pluginId, entry);
        }
    }

    public static <T> Optional<T> get(Class<T> serviceClass) {
        List<ServiceEntry<?>> entries = SERVICES.get(serviceClass);
        if (entries == null || entries.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(serviceClass.cast(entries.get(0).instance()));
    }

    public static <T> Optional<T> getVersioned(Class<T> serviceClass, String versionRange) {
        List<ServiceEntry<?>> entries = SERVICES.get(serviceClass);
        if (entries == null) {
            return Optional.empty();
        }

        VersionRange range = VersionRange.parse(versionRange);
        for (ServiceEntry<?> entry : entries) {
            if (range.matches(entry.version())) {
                return Optional.ofNullable(serviceClass.cast(entry.instance()));
            }
        }
        return Optional.empty();
    }

    public static <T> List<T> getAll(Class<T> serviceClass) {
        List<ServiceEntry<?>> entries = SERVICES.get(serviceClass);
        if (entries == null) {
            return List.of();
        }
        @SuppressWarnings("unchecked")
        List<T> result = entries.stream()
            .map(e -> serviceClass.cast(e.instance()))
            .toList();
        return result;
    }

    public static <T> void unregister(Class<T> serviceClass) {
        SERVICES.remove(serviceClass);
    }

    public static <T> void unregister(Class<T> serviceClass, T instance) {
        List<ServiceEntry<?>> entries = SERVICES.get(serviceClass);
        if (entries != null) {
            entries.removeIf(e -> e.instance() == instance);
            if (entries.isEmpty()) {
                SERVICES.remove(serviceClass);
            }
        }
    }

    public static void unregisterPlugin(String pluginId) {
        SERVICES.values().forEach(entries -> 
            entries.removeIf(e -> e.pluginId().equals(pluginId))
        );
        SERVICES.entrySet().removeIf(e -> e.getValue().isEmpty());
    }

    public static void clear() {
        SERVICES.clear();
        LIFECYCLE_MANAGER.clear();
    }

    private static String getCurrentPluginId() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            if (element.getClassName().endsWith("Plugin")) {
                try {
                    Class<?> pluginClass = Class.forName(element.getClassName());
                    Plugin plugin = Bukkit.getPluginManager().getPlugin(element.getClassName());
                    if (plugin != null) {
                        return plugin.getName();
                    }
                } catch (ClassNotFoundException ignored) {}
            }
        }
        return "unknown";
    }
}
