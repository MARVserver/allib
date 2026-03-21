package marv.allib.registry;

import org.bukkit.Bukkit;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LifecycleManager {

    private final Map<String, List<ServiceEntry<?>>> pluginEntries = new ConcurrentHashMap<>();

    public void track(String pluginId, ServiceEntry<?> entry) {
        pluginEntries.computeIfAbsent(pluginId, k -> new CopyOnWriteArrayList<>()).add(entry);
    }

    public void unregisterPlugin(String pluginId) {
        List<ServiceEntry<?>> entries = pluginEntries.remove(pluginId);
        if (entries != null) {
            entries.forEach(entry -> {
                AlibRegistry.unregister(entry.serviceClass(), entry.instance());
            });
        }
    }

    public void clear() {
        pluginEntries.clear();
    }

    public static void setupAutoCleanup() {
        try {
            var disableHandler = new org.bukkit.event.EventExecutor() {
                @Override
                public void execute(Object listener, org.bukkit.event.Event event) throws Exception {
                    if (event instanceof org.bukkit.event.server.PluginDisableEvent disableEvent) {
                        AlibRegistry.unregisterPlugin(disableEvent.getPlugin().getName());
                    }
                }
            };

            Bukkit.getPluginManager().registerEvent(
                org.bukkit.event.server.PluginDisableEvent.class,
                (org.bukkit.Listener) () -> {},
                org.bukkit.event.EventPriority.NORMAL,
                disableHandler,
                LifecycleManager.class.getClassLoader(),
                false
            );
        } catch (Exception e) {
            Bukkit.getLogger().warning("[allib] Failed to setup auto-cleanup: " + e.getMessage());
        }
    }
}
