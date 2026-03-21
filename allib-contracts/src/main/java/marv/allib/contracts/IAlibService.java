package marv.allib.contracts;

public interface IAlibService {
    String serviceId();
    String version();
    default void load() {}
    default void unload() {}
}
