package marv.allib.scheduler;

import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import marv.allib.contracts.AlibCore;
import marv.allib.contracts.ISchedulerProvider;
import marv.allib.contracts.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class FoliaSchedulerProvider implements ISchedulerProvider {

    @Override
    public ScheduledTask runRegion(Location loc, Runnable task) {
        return runLater(AlibCore.getCorePlugin(), 1L, task);
    }

    @Override
    public ScheduledTask runRegion(Plugin plugin, Location loc, Runnable task) {
        RegionScheduler scheduler = Bukkit.getRegionScheduler();
        ScheduledTaskAdapter adapter = new ScheduledTaskAdapter();
        scheduler.run(plugin, loc, scheduledTask -> {
            adapter.setTask(scheduledTask);
            task.run();
        });
        return adapter;
    }

    @Override
    public ScheduledTask runAsync(Runnable task) {
        return runAsync(AlibCore.getCorePlugin(), task);
    }

    @Override
    public ScheduledTask runAsync(Plugin plugin, Runnable task) {
        ScheduledTaskAdapter adapter = new ScheduledTaskAdapter();
        Bukkit.getAsyncScheduler().runNow(plugin, scheduledTask -> {
            adapter.setTask(scheduledTask);
            task.run();
        });
        return adapter;
    }

    @Override
    public ScheduledTask runLater(long ticks, Runnable task) {
        return runLater(AlibCore.getCorePlugin(), ticks, task);
    }

    @Override
    public ScheduledTask runLater(Plugin plugin, long ticks, Runnable task) {
        ScheduledTaskAdapter adapter = new ScheduledTaskAdapter();
        Bukkit.getRegionScheduler().runDelayed(plugin, new org.bukkit.Location(null, 0, 0, 0), scheduledTask -> {
            adapter.setTask(scheduledTask);
            task.run();
        }, ticks);
        return adapter;
    }

    @Override
    public ScheduledTask runRegionLater(Location loc, long ticks, Runnable task) {
        return runRegionLater(Alib.getCorePlugin(), loc, ticks, task);
    }

    @Override
    public ScheduledTask runRegionLater(Plugin plugin, Location loc, long ticks, Runnable task) {
        ScheduledTaskAdapter adapter = new ScheduledTaskAdapter();
        Bukkit.getRegionScheduler().runDelayed(plugin, loc, scheduledTask -> {
            adapter.setTask(scheduledTask);
            task.run();
        }, ticks);
        return adapter;
    }

    private static class ScheduledTaskAdapter implements ScheduledTask {
        private volatile io.papermc.paper.threadedregions.scheduler.ScheduledTask task;

        public void setTask(io.papermc.paper.threadedregions.scheduler.ScheduledTask task) {
            this.task = task;
        }

        @Override
        public void run() {
            task.run();
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
            return task.isCancelled();
        }
    }
}
