package ase_filament_tracker.domain.repository

import ase_filament_tracker.domain.model.MaterialProfile.MaterialProfile
import ase_filament_tracker.domain.model.MaterialProfile.MaterialProfileId

interface MaterialProfileRepository {
    fun save(profile: MaterialProfile): MaterialProfile
    fun findById(id: MaterialProfileId): MaterialProfile?
    fun findAll(): List<MaterialProfile>
    fun delete(id: MaterialProfileId)
}
