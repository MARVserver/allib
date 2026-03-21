package marv.allib.scheduler;

import marv.allib.contracts.ISchedulerProvider;
import marv.allib.contracts.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class BukkitSchedulerProvider implements ISchedulerProvider {

    private final BukkitScheduler scheduler;

    public BukkitSchedulerProvider() {
        this.scheduler = Bukkit.getScheduler();
    }

    @Override
    public ScheduledTask runRegion(Location loc, Runnable task) {
        return runLater(1L, task);
    }

    @Override
    public ScheduledTask runAsync(Runnable task) {
        BukkitTask bukkitTask = scheduler.runTaskAsynchronously(null, task);
        return new BukkitScheduledTask(bukkitTask);
    }

    @Override
    public ScheduledTask runLater(long ticks, Runnable task) {
        BukkitTask bukkitTask = scheduler.runTaskLater(null, task, ticks);
        return new BukkitScheduledTask(bukkitTask);
    }

    @Override
    public ScheduledTask runRegionLater(Location loc, long ticks, Runnable task) {
        return runLater(ticks, task);
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
