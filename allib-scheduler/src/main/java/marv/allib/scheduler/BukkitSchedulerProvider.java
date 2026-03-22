package marv.allib.scheduler;

import marv.allib.contracts.AlibCore;
import marv.allib.contracts.ISchedulerProvider;
import marv.allib.contracts.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class BukkitSchedulerProvider implements ISchedulerProvider {

    private final BukkitScheduler scheduler;

    public BukkitSchedulerProvider() {
        this.scheduler = Bukkit.getScheduler();
    }

    @Override
    public ScheduledTask runRegion(Location loc, Runnable task) {
        return runLater(AlibCore.getCorePlugin(), 1L, task);
    }

    @Override
    public ScheduledTask runRegion(Plugin plugin, Location loc, Runnable task) {
        return runLater(plugin, 1L, task);
    }

    @Override
    public ScheduledTask runAsync(Runnable task) {
        return runAsync(AlibCore.getCorePlugin(), task);
    }

    @Override
    public ScheduledTask runAsync(Plugin plugin, Runnable task) {
        BukkitTask bukkitTask = scheduler.runTaskAsynchronously(plugin, task);
        return new BukkitScheduledTask(bukkitTask);
    }

    @Override
    public ScheduledTask runLater(long ticks, Runnable task) {
        return runLater(AlibCore.getCorePlugin(), ticks, task);
    }

    @Override
    public ScheduledTask runLater(Plugin plugin, long ticks, Runnable task) {
        BukkitTask bukkitTask = scheduler.runTaskLater(plugin, task, ticks);
        return new BukkitScheduledTask(bukkitTask);
    }

    @Override
    public ScheduledTask runRegionLater(Location loc, long ticks, Runnable task) {
        return runLater(AlibCore.getCorePlugin(), ticks, task);
    }

    @Override
    public ScheduledTask runRegionLater(Plugin plugin, Location loc, long ticks, Runnable task) {
        return runLater(plugin, ticks, task);
    }

    private static class BukkitScheduledTask implements ScheduledTask {
        private final BukkitTask task;

        public BukkitScheduledTask(BukkitTask task) {
            this.task = task;
        }

        @Override
        public void run() {
            throw new UnsupportedOperationException("Bukkit scheduled tasks cannot be explicitly re-run");
        }

        @Override
        public void cancel() {
            task.cancel();
        }

        @Override
        public boolean isCancelled() {
            return task.isCancelled();
        }

        @Override
        public boolean isDone() {
            return task.isCancelled() || !task.getOwner().isEnabled();
        }
    }
}
