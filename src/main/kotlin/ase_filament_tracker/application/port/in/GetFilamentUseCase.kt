package ase_filament_tracker.application.port.`in`

import ase_filament_tracker.domain.model.filament.Filament
import ase_filament_tracker.domain.model.filament.FilamentId

interface GetFilamentUseCase {
    fun getAllFilaments(): List<Filament>
    fun getFilamentById(id: FilamentId): Filament?
}
