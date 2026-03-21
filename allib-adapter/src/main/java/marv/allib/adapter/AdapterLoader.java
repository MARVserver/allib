package marv.allib.adapter;

import marv.allib.contracts.AlibAdapter;
import marv.allib.contracts.IAlibService;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class AdapterLoader {

    private static final List<IAlibService> LOADED_ADAPTERS = new ArrayList<>();

    public static void loadAdapters() {
        ServiceLoader.load(IAlibService.class, AdapterLoader.class.getClassLoader())
            .forEach(adapter -> {
                try {
                    adapter.load();
                    LOADED_ADAPTERS.add(adapter);
                    Bukkit.getLogger().info("[allib] Loaded adapter: " + adapter.serviceId());
                } catch (Exception e) {
                    Bukkit.getLogger().warning("[allib] Failed to load adapter " + adapter.serviceId() + ": " + e.getMessage());
                }
            });
    }

    public static List<IAlibService> getLoadedAdapters() {
        return List.copyOf(LOADED_ADAPTERS);
    }

    public static void unloadAdapters() {
        LOADED_ADAPTERS.forEach(adapter -> {
            try {
                adapter.unload();
            } catch (Exception e) {
                Bukkit.getLogger().warning("[allib] Failed to unload adapter " + adapter.serviceId());
            }
        });
        LOADED_ADAPTERS.clear();
    }
}
