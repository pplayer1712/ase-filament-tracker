package ase_filament_tracker.application.port.`in`

import ase_filament_tracker.domain.model.filament.FilamentId

interface DeleteFilamentUseCase {
    fun deleteFilament(id: FilamentId)
}
