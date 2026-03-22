package marv.allib.adapter;

import marv.allib.contracts.IAlibService;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class AdapterLoader {

    private static final List<IAlibService> LOADED_ADAPTERS = new ArrayList<>();

    public static void loadAdapters() {
        // Load via ServiceLoader (standard adapters)
        ServiceLoader.load(IAlibService.class, AdapterLoader.class.getClassLoader())
                .forEach(AdapterLoader::loadSingleAdapter);

        // Load via KSP generated loader (if available)
        try {
            Class<?> generatedLoader = Class.forName("marv.allib.ksp.generated.AlibServiceLoader");
            java.lang.reflect.Method loadMethod = generatedLoader.getMethod("loadServices");
            @SuppressWarnings("unchecked")
            List<IAlibService> generatedServices = (List<IAlibService>) loadMethod.invoke(null);
            if (generatedServices != null) {
                generatedServices.forEach(AdapterLoader::loadSingleAdapter);
            }
        } catch (ClassNotFoundException ignored) {
            // No generated services
        } catch (Exception e) {
            Bukkit.getLogger().warning("[allib] Failed to load generated services: " + e.getMessage());
        }
    }

    private static void loadSingleAdapter(IAlibService adapter) {
        try {
            adapter.load();
            LOADED_ADAPTERS.add(adapter);
            Bukkit.getLogger().info("[allib] Loaded adapter/service: " + adapter.serviceId());
        } catch (Exception e) {
            Bukkit.getLogger().warning("[allib] Failed to load " + adapter.serviceId() + ": " + e.getMessage());
        }
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
