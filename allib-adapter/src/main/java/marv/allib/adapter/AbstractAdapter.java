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
    public final void load() {
        String pluginId = getTargetPluginId();
        if (!Bukkit.getPluginManager().isPluginEnabled(pluginId)) {
            throw new IllegalStateException("Target plugin " + pluginId + " is not enabled");
        }

        AlibAdapter annotation = getClass().getAnnotation(AlibAdapter.class);
        String version = annotation != null ? annotation.version() : "unknown";

        onLoad();
        AlibRegistry.register(getServiceInterface(), this, version, "allib-adapter");
    }

    @Override
    public final void unload() {
        onUnload();
        AlibRegistry.unregister(getServiceInterface(), this);
    }

    protected abstract Class<?> getServiceInterface();
}
