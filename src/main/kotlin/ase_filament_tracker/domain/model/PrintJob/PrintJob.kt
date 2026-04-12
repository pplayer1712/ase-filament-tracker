package ase_filament_tracker.domain.model.PrintJob

import ase_filament_tracker.domain.event.DomainEvent
import ase_filament_tracker.domain.model.filament.FilamentId

enum class PrintJobStatus {
    IN_PROGRESS,
    FINISHED,
    CANCELLED
}

class PrintJob(
    val id: PrintJobId,
    val filamentId: FilamentId,
    val estimatedUsage: MaterialUsage,
    var status: PrintJobStatus = PrintJobStatus.IN_PROGRESS
) {
    private val _domainEvents = mutableListOf<DomainEvent>()
    val domainEvents: List<DomainEvent> get() = _domainEvents.toList()

    fun finishJob(actualUsage: MaterialUsage) {
        require(status == PrintJobStatus.IN_PROGRESS) { "Only full in-progress jobs can be finished." }
        
        status = PrintJobStatus.FINISHED
        
        _domainEvents.add(
            PrintJobCompletedEvent(
                printJobId = this.id,
                filamentId = this.filamentId,
                usedMaterial = actualUsage
            )
        )
    }

    fun clearDomainEvents() {
        _domainEvents.clear()
    }
}
