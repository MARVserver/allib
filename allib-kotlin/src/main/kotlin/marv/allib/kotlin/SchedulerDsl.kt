package marv.allib.kotlin

import marv.allib.contracts.ScheduledTask
import marv.allib.scheduler.SchedulerBridge
import org.bukkit.Location

public object SchedulerDsl {

    @DslMarker
    public annotation class SchedulerDslMarker

    public class SchedulerScope {
        
        public fun region(loc: Location, block: suspend () -> Unit): ScheduledTask {
            return SchedulerBridge.getInstance().runRegion(loc) {
                block.startCoroutine {}
            }
        }

        public fun async(block: suspend () -> Unit): ScheduledTask {
            return SchedulerBridge.getInstance().runAsync {
                block.startCoroutine {}
            }
        }

        public fun later(ticks: Long, block: suspend () -> Unit): ScheduledTask {
            return SchedulerBridge.getInstance().runLater(ticks) {
                block.startCoroutine {}
            }
        }

        public fun regionLater(loc: Location, ticks: Long, block: suspend () -> Unit): ScheduledTask {
            return SchedulerBridge.getInstance().runRegionLater(loc, ticks) {
                block.startCoroutine {}
            }
        }
    }

    public fun scheduler(block: SchedulerScope.() -> Unit) {
        SchedulerScope().block()
    }

    public inline fun <T> runBlocking(crossinline block: suspend () -> T): T {
        return kotlinx.coroutines.runBlocking { block() }
    }
}
