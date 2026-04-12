package ase_filament_tracker.domain.model.PrintJob

import ase_filament_tracker.domain.event.DomainEvent
import ase_filament_tracker.domain.model.filament.FilamentId
import java.time.Instant

data class PrintJobCompletedEvent(
    val printJobId: PrintJobId,
    val filamentId: FilamentId,
    val usedMaterial: MaterialUsage,
    override val occurredOn: Instant = Instant.now()
) : DomainEvent
