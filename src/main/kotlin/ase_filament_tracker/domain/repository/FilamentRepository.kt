package ase_filament_tracker.domain.repository

import ase_filament_tracker.domain.model.filament.Filament
import ase_filament_tracker.domain.model.filament.FilamentId

interface FilamentRepository {
    fun save(filament: Filament): Filament
    fun findById(id: FilamentId): Filament?
    fun findAll(): List<Filament>
    fun delete(id: FilamentId)
}
