package marv.allib.api;

import marv.allib.contracts.AlibCore;
import marv.allib.registry.AlibRegistry;
import marv.allib.registry.LifecycleManager;
import marv.allib.scheduler.SchedulerBridge;

import java.util.Optional;

public class Alib {

    public static void init(org.bukkit.plugin.Plugin plugin) {
        if (AlibCore.isInitialized()) {
            return;
        }
        AlibCore.init(plugin);
        LifecycleManager.setupAutoCleanup();
    }

    public static void init() {
        init(null);
    }

    public static org.bukkit.plugin.Plugin getCorePlugin() {
        return AlibCore.getCorePlugin();
    }

    public static SchedulerBridge scheduler() {
        return SchedulerBridge.getInstance();
    }

    public static Class<AlibRegistry> registry() {
        return AlibRegistry.class;
    }

    public static <T> Optional<T> getService(Class<T> serviceClass) {
        return AlibRegistry.get(serviceClass);
    }

    public static <T> Optional<T> getServiceVersioned(Class<T> serviceClass, String versionRange) {
        return AlibRegistry.getVersioned(serviceClass, versionRange);
    }

    public static boolean isInitialized() {
        return AlibCore.isInitialized();
    }
}
