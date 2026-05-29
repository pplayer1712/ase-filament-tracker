package ase_filament_tracker.application.dto

import java.util.UUID

data class CreatePrintJobCommand(
    val filamentId: UUID,
    val estimatedUsageInGrams: Double
)

data class FinishPrintJobCommand(
    val printJobId: UUID,
    val actualUsageInGrams: Double
)
