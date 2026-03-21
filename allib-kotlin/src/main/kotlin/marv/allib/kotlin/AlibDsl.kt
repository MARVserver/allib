package marv.allib.kotlin

import marv.allib.api.Alib
import marv.allib.registry.AlibRegistry

public object AlibDsl {

    @DslMarker
    public annotation class AlibDslMarker

    public fun alib(block: AlibScope.() -> Unit) {
        Alib.init()
        val scope = AlibScope()
        scope.block()
    }

    public fun <T : Any> registry(block: RegistryScope<T>.() -> Unit) {
        val scope = RegistryScope<T>()
        scope.block()
    }
}

@AlibDsl.AlibDslMarker
public class AlibScope {

    public fun scheduler(block: SchedulerDsl.SchedulerScope.() -> Unit) {
        SchedulerDsl.SchedulerScope().block()
    }

    public fun <T : Any> service(clazz: Class<T>, instance: T, version: String) {
        AlibRegistry.register(clazz, instance, version)
    }

    public fun <T : Any> getService(clazz: Class<T>): T? {
        return AlibRegistry.get(clazz).orElse(null)
    }

    public fun <T : Any> getServiceVersioned(clazz: Class<T>, range: String): T? {
        return AlibRegistry.getVersioned(clazz, range).orElse(null)
    }
}

@AlibDsl.AlibDslMarker
public class RegistryScope<T : Any> {

    private var serviceClass: Class<T>? = null
    private var instance: T? = null
    private var version: String? = null

    public fun service(clazz: Class<T>) {
        serviceClass = clazz
    }

    public fun instance(obj: T) {
        instance = obj
    }

    public fun version(ver: String) {
        version = ver
    }

    public fun register() {
        val sc = serviceClass ?: throw IllegalStateException("service class not set")
        val inst = instance ?: throw IllegalStateException("instance not set")
        val ver = version ?: throw IllegalStateException("version not set")
        AlibRegistry.register(sc, inst, ver)
    }
}
