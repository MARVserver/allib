package marv.allib.contracts;

import org.bukkit.Location;

public interface ISchedulerProvider {
    ScheduledTask runRegion(Location loc, Runnable task);
    ScheduledTask runAsync(Runnable task);
    ScheduledTask runLater(long ticks, Runnable task);
    ScheduledTask runRegionLater(Location loc, long ticks, Runnable task);
}
