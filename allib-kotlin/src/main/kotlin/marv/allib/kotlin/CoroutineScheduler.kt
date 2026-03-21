package marv.allib.kotlin

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.*
import marv.allib.contracts.ScheduledTask
import marv.allib.scheduler.SchedulerBridge
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin

public object AlibDispatchers {
    public val Bukkit: CoroutineContext = BukkitDispatcher + SupervisorJob()
    public val Region: CoroutineContext = BukkitDispatcher + RegionElement
    public val Async: CoroutineContext = Dispatchers.IO.limitedParallelism(10) + AsyncElement
}

private val BukkitDispatcher = Dispatchers.Unconfined

private object RegionElement : CoroutineContext.Element {
    override val key: CoroutineContext.Key<RegionElement> = Key
    object Key : CoroutineContext.Key<RegionElement>
}

private object AsyncElement : CoroutineContext.Element {
    override val key: CoroutineContext.Key<AsyncElement> = Key
    object Key : CoroutineContext.Key<AsyncElement>
}

public class CoroutineScheduler(private val plugin: JavaPlugin) {

    private val scope = CoroutineScope(AlibDispatchers.Bukkit)

    public fun launch(
        context: CoroutineContext = AlibDispatchers.Bukkit,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return scope.launch(context, start, block)
    }

    public fun <T> async(
        context: CoroutineContext = AlibDispatchers.Async,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
    ): Deferred<T> {
        return scope.async(context, start, block)
    }

    public fun runRegion(
        loc: Location,
        context: CoroutineContext = AlibDispatchers.Region,
        block: suspend () -> Unit
    ): ScheduledTask {
        return SchedulerBridge.getInstance().runRegion(loc) {
            scope.launch(context) { block() }
        }
    }

    public fun runLater(
        ticks: Long,
        context: CoroutineContext = AlibDispatchers.Bukkit,
        block: suspend () -> Unit
    ): ScheduledTask {
        return SchedulerBridge.getInstance().runLater(ticks) {
            scope.launch(context) { block() }
        }
    }

    public fun runRegionLater(
        loc: Location,
        ticks: Long,
        context: CoroutineContext = AlibDispatchers.Region,
        block: suspend () -> Unit
    ): ScheduledTask {
        return SchedulerBridge.getInstance().runRegionLater(loc, ticks) {
            scope.launch(context) { block() }
        }
    }

    public fun cancel() {
        scope.cancel()
    }
}

public fun CoroutineScope.launchRegion(
    loc: Location,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(AlibDispatchers.Region, start, block)
}

public fun CoroutineScope.launchAsync(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(AlibDispatchers.Async, start, block)
}
