package marv.allib.adapter;

import marv.allib.contracts.AlibAdapter;
import marv.allib.contracts.IAlibService;
import marv.allib.registry.AlibRegistry;
import org.bukkit.Bukkit;

public abstract class AbstractAdapter implements IAlibService {

    protected abstract String getTargetPluginId();
    protected abstract String getSupportedVersionRange();
    protected abstract void onLoad();
    protected abstract void onUnload();

    @Override
    @SuppressWarnings("unchecked")
    public final void load() {
        String pluginId = getTargetPluginId();
        if (!Bukkit.getPluginManager().isPluginEnabled(pluginId)) {
            throw new IllegalStateException("Target plugin " + pluginId + " is not enabled");
        }

        AlibAdapter annotation = getClass().getAnnotation(AlibAdapter.class);
        String version = annotation != null ? annotation.version() : "unknown";

        onLoad();
        Class<Object> iface = (Class<Object>) getServiceInterface();
        AlibRegistry.register(iface, this, version, "allib-adapter");
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void unload() {
        onUnload();
        Class<Object> iface = (Class<Object>) getServiceInterface();
        AlibRegistry.unregister(iface, this);
    }

    protected abstract Class<?> getServiceInterface();
}
