package ase_filament_tracker.application.service

import ase_filament_tracker.application.port.`in`.DeleteFilamentUseCase
import ase_filament_tracker.application.port.`in`.GetFilamentUseCase
import ase_filament_tracker.domain.model.filament.Filament
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.repository.FilamentRepository

class FilamentQueryService(
    private val filamentRepository: FilamentRepository
) : GetFilamentUseCase, DeleteFilamentUseCase {

    override fun getAllFilaments(): List<Filament> {
        return filamentRepository.findAll()
    }

    override fun getFilamentById(id: FilamentId): Filament? {
        return filamentRepository.findById(id)
    }

    override fun deleteFilament(id: FilamentId) {
        filamentRepository.delete(id)
    }
}
