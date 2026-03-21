package marv.allib.api;

import marv.allib.contracts.ScheduledTask;
import marv.allib.scheduler.SchedulerBridge;
import org.bukkit.Location;

public class AlibScheduler {

    private static SchedulerBridge getBridge() {
        return SchedulerBridge.getInstance();
    }

    public static ScheduledTask runRegion(Location loc, Runnable task) {
        return getBridge().runRegion(loc, task);
    }

    public static ScheduledTask runAsync(Runnable task) {
        return getBridge().runAsync(task);
    }

    public static ScheduledTask runLater(long ticks, Runnable task) {
        return getBridge().runLater(ticks, task);
    }

    public static ScheduledTask runRegionLater(Location loc, long ticks, Runnable task) {
        return getBridge().runRegionLater(loc, ticks, task);
    }

    public static void runRegion(Location loc, Class<?> owner, Runnable task) {
        getBridge().runRegion(loc, task);
    }

    public static void runAsync(Class<?> owner, Runnable task) {
        getBridge().runAsync(task);
    }

    public static void runLater(Class<?> owner, long ticks, Runnable task) {
        getBridge().runLater(ticks, task);
    }
}
