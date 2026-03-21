package marv.allib.scheduler;

import marv.allib.contracts.ISchedulerProvider;
import marv.allib.contracts.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class VirtualThreadProvider implements ISchedulerProvider {

    private final ExecutorService executor;

    public VirtualThreadProvider() {
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public ScheduledTask runRegion(Location loc, Runnable task) {
        ScheduledTaskAdapter adapter = new ScheduledTaskAdapter();
        executor.submit(() -> {
            adapter.markStarted();
            task.run();
        });
        return adapter;
    }

    @Override
    public ScheduledTask runAsync(Runnable task) {
        ScheduledTaskAdapter adapter = new ScheduledTaskAdapter();
        executor.submit(() -> {
            adapter.markStarted();
            task.run();
        });
        return adapter;
    }

    @Override
    public ScheduledTask runLater(long ticks, Runnable task) {
        ScheduledTaskAdapter adapter = new ScheduledTaskAdapter();
        Bukkit.getScheduler().runTaskLater(null, () -> {
            executor.submit(() -> {
                adapter.markStarted();
                task.run();
            });
        }, ticks);
        return adapter;
    }

    @Override
    public ScheduledTask runRegionLater(Location loc, long ticks, Runnable task) {
        return runLater(ticks, task);
    }

    private static class ScheduledTaskAdapter implements ScheduledTask {
        private final AtomicBoolean cancelled = new AtomicBoolean(false);
        private final AtomicBoolean started = new AtomicBoolean(false);

        public void markStarted() {
            started.set(true);
        }

        @Override
        public void run() {
            throw new UnsupportedOperationException("Virtual thread tasks cannot be explicitly run");
        }

        @Override
        public void cancel() {
            cancelled.set(true);
        }

        @Override
        public boolean isCancelled() {
            return cancelled.get();
        }

        @Override
        public boolean isDone() {
            return started.get() || cancelled.get();
        }
    }
}
