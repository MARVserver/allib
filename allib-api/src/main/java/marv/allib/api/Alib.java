package marv.allib.api;

import marv.allib.registry.AlibRegistry;
import marv.allib.registry.LifecycleManager;
import marv.allib.scheduler.SchedulerBridge;

import java.util.Optional;

public class Alib {

    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            return;
        }
        LifecycleManager.setupAutoCleanup();
        initialized = true;
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
        return initialized;
    }
}
