package ase_filament_tracker.application.port.`in`

import ase_filament_tracker.application.dto.AddMaterialProfileCommand
import ase_filament_tracker.domain.model.MaterialProfile.MaterialProfile
import ase_filament_tracker.domain.model.MaterialProfile.MaterialProfileId

interface MaterialProfileUseCase {
    fun addMaterialProfile(command: AddMaterialProfileCommand): MaterialProfileId
    fun getAllMaterialProfiles(): List<MaterialProfile>
    fun getMaterialProfileById(id: MaterialProfileId): MaterialProfile?
    fun deleteMaterialProfile(id: MaterialProfileId)
}
