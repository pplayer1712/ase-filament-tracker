package ase_filament_tracker.domain.model.consumption

import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.model.filament.Weight
import java.time.Instant
import java.util.UUID

@JvmInline
value class ConsumptionEventId(val value: UUID) {
    companion object {
        fun generate() = ConsumptionEventId(UUID.randomUUID())
    }
}

enum class ConsumptionType {
    MANUAL, PRINT_JOB
}

class ConsumptionEvent(
    val id: ConsumptionEventId,
    val filamentId: FilamentId,
    val consumedWeight: Weight,
    val type: ConsumptionType,
    val occurredOn: Instant = Instant.now()
)
