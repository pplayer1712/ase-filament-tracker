package ase_filament_tracker.application.port.`in`

import ase_filament_tracker.application.dto.AddFilamentCommand
import ase_filament_tracker.domain.model.filament.FilamentId

interface AddFilamentUseCase {
    fun addFilament(command: AddFilamentCommand): FilamentId
}
