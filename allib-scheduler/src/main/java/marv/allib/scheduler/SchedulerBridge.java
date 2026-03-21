package marv.allib.scheduler;

import marv.allib.contracts.ISchedulerProvider;
import marv.allib.contracts.ScheduledTask;
import marv.allib.contracts.ServerEnvironment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

public class SchedulerBridge implements ISchedulerProvider {

    private static final SchedulerBridge INSTANCE = new SchedulerBridge();
    private final ISchedulerProvider provider;

    private SchedulerBridge() {
        this.provider = createProvider();
    }

    private static ISchedulerProvider createProvider() {
        if (ServerEnvironment.isFolia()) {
            try {
                return (ISchedulerProvider) Class.forName("marv.allib.scheduler.FoliaSchedulerProvider")
                        .getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to load FoliaSchedulerProvider", e);
            }
        }
        if (Runtime.version().feature() >= 21) {
            return new VirtualThreadProvider();
        }
        return new BukkitSchedulerProvider();
    }

    public static SchedulerBridge getInstance() {
        return INSTANCE;
    }

    @Override
    public ScheduledTask runRegion(Location loc, Runnable task) {
        return provider.runRegion(loc, task);
    }

    @Override
    public ScheduledTask runAsync(Runnable task) {
        return provider.runAsync(task);
    }

    @Override
    public ScheduledTask runLater(long ticks, Runnable task) {
        return provider.runLater(ticks, task);
    }

    @Override
    public ScheduledTask runRegionLater(Location loc, long ticks, Runnable task) {
        return provider.runRegionLater(loc, ticks, task);
    }
}
