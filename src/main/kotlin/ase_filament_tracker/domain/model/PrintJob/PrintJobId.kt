package ase_filament_tracker.domain.model.PrintJob

import java.util.UUID

@JvmInline
value class PrintJobId(val value: UUID) {
    companion object {
        fun generate(): PrintJobId = PrintJobId(UUID.randomUUID())
    }
}
