package marv.allib.registry;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.EventExecutor;

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
            entries.forEach(this::unregisterEntry);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void unregisterEntry(ServiceEntry<?> entry) {
        AlibRegistry.unregister((Class<T>) entry.serviceClass(), (T) entry.instance());
    }

    public void clear() {
        pluginEntries.clear();
    }

    public static void setupAutoCleanup() {
        try {
            Listener listener = new Listener() {};
            EventExecutor executor = (l, event) -> {
                if (event instanceof PluginDisableEvent disableEvent) {
                    AlibRegistry.unregisterPlugin(disableEvent.getPlugin().getName());
                }
            };

            if (Bukkit.getPluginManager().getPlugins().length > 0) {
                Bukkit.getPluginManager().registerEvent(
                    PluginDisableEvent.class,
                    listener,
                    EventPriority.NORMAL,
                    executor,
                    Bukkit.getPluginManager().getPlugins()[0],
                    false
                );
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("[allib] Failed to setup auto-cleanup: " + e.getMessage());
        }
    }
}
