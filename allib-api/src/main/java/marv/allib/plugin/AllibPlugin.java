package marv.allib.plugin;

import marv.allib.api.Alib;
import org.bukkit.plugin.java.JavaPlugin;

public final class AllibPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Alib.init(this);
        getLogger().info("[Allib] Initialized successfully.");
    }

    @Override
    public void onDisable() {
        getLogger().info("[Allib] Disabled successfully.");
    }
}
