package marv.allib.registry;

import java.util.Objects;

public record ServiceEntry<T>(
    Class<T> serviceClass,
    T instance,
    String version,
    String pluginId
) {
    public ServiceEntry {
        Objects.requireNonNull(serviceClass, "serviceClass cannot be null");
        Objects.requireNonNull(instance, "instance cannot be null");
        Objects.requireNonNull(version, "version cannot be null");
        Objects.requireNonNull(pluginId, "pluginId cannot be null");
    }
}
