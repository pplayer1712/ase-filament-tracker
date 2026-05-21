package ase_filament_tracker.application.service

import ase_filament_tracker.application.dto.AddFilamentCommand
import ase_filament_tracker.application.port.`in`.AddFilamentUseCase
import ase_filament_tracker.domain.model.filament.Color
import ase_filament_tracker.domain.model.filament.Diameter
import ase_filament_tracker.domain.model.filament.Filament
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.model.filament.Weight
import ase_filament_tracker.domain.repository.FilamentRepository

class FilamentApplicationService(
    private val filamentRepository: FilamentRepository
) : AddFilamentUseCase {

    override fun addFilament(command: AddFilamentCommand): FilamentId {
        val filament = Filament(
            id = FilamentId.generate(),
            color = Color(command.colorName, command.colorHex),
            diameter = Diameter(command.diameterInMm),
            remainingWeight = Weight(command.initialWeightInGrams)
        )
        
        filamentRepository.save(filament)
        return filament.id
    }
}
