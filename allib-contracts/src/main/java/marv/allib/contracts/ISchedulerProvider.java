package marv.allib.contracts;

import org.bukkit.Location;

public interface ISchedulerProvider {
    ScheduledTask runRegion(org.bukkit.Location loc, Runnable task);

    ScheduledTask runRegion(org.bukkit.plugin.Plugin plugin, org.bukkit.Location loc, Runnable task);

    ScheduledTask runAsync(Runnable task);

    ScheduledTask runAsync(org.bukkit.plugin.Plugin plugin, Runnable task);

    ScheduledTask runLater(long ticks, Runnable task);

    ScheduledTask runLater(org.bukkit.plugin.Plugin plugin, long ticks, Runnable task);

    ScheduledTask runRegionLater(org.bukkit.Location loc, long ticks, Runnable task);

    ScheduledTask runRegionLater(org.bukkit.plugin.Plugin plugin, org.bukkit.Location loc, long ticks, Runnable task);
}
