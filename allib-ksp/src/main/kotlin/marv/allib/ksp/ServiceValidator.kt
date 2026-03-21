package marv.allib.ksp

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType

object ServiceValidator {

    fun validate(visitor: AlibKspProcessor.ServiceCollectorVisitor, logger: KSPLogger): List<String> {
        val errors = mutableListOf<String>()
        val services = visitor.getServices()

        for (service in services) {
            validateServiceId(service, errors)
            validateVersion(service, errors)
            validateDependencies(service, services, errors)
        }

        return errors
    }

    private fun validateServiceId(service: AlibKspProcessor.ServiceInfo, errors: MutableList<String>) {
        if (service.id.isBlank()) {
            errors.add("Service ${service.className}: id cannot be blank")
        }
        if (!service.id.matches(Regex("^[a-z][a-z0-9-]*$"))) {
            errors.add("Service ${service.className}: id must match pattern [a-z][a-z0-9-]*")
        }
    }

    private fun validateVersion(service: AlibKspProcessor.ServiceInfo, errors: MutableList<String>) {
        if (!service.version.matches(Regex("^\\d+\\.\\d+\\.\\d+.*"))) {
            errors.add("Service ${service.className}: version must be valid semver (e.g. 1.0.0)")
        }
    }

    private fun validateDependencies(
        service: AlibKspProcessor.ServiceInfo,
        allServices: List<AlibKspProcessor.ServiceInfo>,
        errors: MutableList<String>
    ) {
        for (dep in service.dependencies) {
            val exists = allServices.any { it.id == dep }
            if (!exists) {
                errors.add("Service ${service.className}: dependency '$dep' not found")
            }
        }
    }

    fun validateImplementsIAlibService(classDeclaration: KSClassDeclaration, logger: KSPLogger): Boolean {
        val iAlibServiceName = "marv.allib.contracts.IAlibService"
        val superTypes = classDeclaration.superTypes

        for (superType in superTypes) {
            val resolved = superType.resolve()
            if (resolved.declaration.qualifiedName?.asString() == iAlibServiceName) {
                return true
            }
        }

        logger.error(
            "@AlibService annotated class ${classDeclaration.qualifiedName} must implement IAlibService",
            classDeclaration
        )
        return false
    }
}
