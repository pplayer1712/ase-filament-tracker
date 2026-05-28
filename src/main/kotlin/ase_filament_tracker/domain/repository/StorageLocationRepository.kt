package ase_filament_tracker.domain.repository

import ase_filament_tracker.domain.model.StorageLocation.LocationPath
import ase_filament_tracker.domain.model.StorageLocation.StorageLocation
import ase_filament_tracker.domain.model.StorageLocation.StorageLocationId

interface StorageLocationRepository {
    fun save(storageLocation: StorageLocation)
    fun findById(id: StorageLocationId): StorageLocation?
    fun existsByPath(path: LocationPath): Boolean
}
