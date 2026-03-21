package marv.allib.contracts;

public interface ScheduledTask extends Runnable {
    void cancel();
    boolean isCancelled();
    boolean isDone();
}
