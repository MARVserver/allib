package marv.allib.contracts;

public class AlibCore {
    private static org.bukkit.plugin.Plugin corePlugin;
    private static boolean initialized = false;

    public static void init(org.bukkit.plugin.Plugin plugin) {
        if (initialized) {
            return;
        }
        corePlugin = plugin;
        initialized = true;
    }

    public static org.bukkit.plugin.Plugin getCorePlugin() {
        return corePlugin;
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
