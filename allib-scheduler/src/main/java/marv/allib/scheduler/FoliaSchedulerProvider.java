package marv.allib.scheduler;

import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import marv.allib.contracts.ISchedulerProvider;
import marv.allib.contracts.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

public class FoliaSchedulerProvider implements ISchedulerProvider {

    @Override
    public ScheduledTask runRegion(Location loc, Runnable task) {
        RegionScheduler scheduler = Bukkit.getRegionScheduler();
        ScheduledTaskAdapter adapter = new ScheduledTaskAdapter();
        scheduler.run(null, loc, scheduledTask -> {
            adapter.setTask(scheduledTask);
            task.run();
        });
        return adapter;
    }

    @Override
    public ScheduledTask runAsync(Runnable task) {
        ScheduledTaskAdapter adapter = new ScheduledTaskAdapter();
        Bukkit.getAsyncScheduler().runNow(null, scheduledTask -> {
            adapter.setTask(scheduledTask);
            task.run();
        });
        return adapter;
    }

    @Override
    public ScheduledTask runLater(long ticks, Runnable task) {
        ScheduledTaskAdapter adapter = new ScheduledTaskAdapter();
        Bukkit.getRegionScheduler().runDelayed(null, new org.bukkit.Location(null, 0, 0, 0), scheduledTask -> {
            adapter.setTask(scheduledTask);
            task.run();
        }, ticks);
        return adapter;
    }

    @Override
    public ScheduledTask runRegionLater(Location loc, long ticks, Runnable task) {
        ScheduledTaskAdapter adapter = new ScheduledTaskAdapter();
        Bukkit.getRegionScheduler().runDelayed(null, loc, scheduledTask -> {
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
