package ase_filament_tracker.application.dto

import java.util.UUID

data class RecordConsumptionCommand(
    val filamentId: UUID,
    val amountInGrams: Double,
    val isPrintJob: Boolean = false,
    val includePurgeLineWaste: Boolean = true
)
