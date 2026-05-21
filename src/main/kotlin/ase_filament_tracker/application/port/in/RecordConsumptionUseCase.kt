package ase_filament_tracker.application.port.`in`

import ase_filament_tracker.application.dto.RecordConsumptionCommand

interface RecordConsumptionUseCase {
    fun recordConsumption(command: RecordConsumptionCommand)
}
