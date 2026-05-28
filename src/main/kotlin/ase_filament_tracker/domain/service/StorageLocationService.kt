package ase_filament_tracker.domain.service

import ase_filament_tracker.domain.model.StorageLocation.DuplicateStorageLocationException
import ase_filament_tracker.domain.model.StorageLocation.LocationPath
import ase_filament_tracker.domain.model.StorageLocation.StorageLocation
import ase_filament_tracker.domain.model.StorageLocation.StorageLocationId
import ase_filament_tracker.domain.repository.StorageLocationRepository

class StorageLocationService(
    private val repository: StorageLocationRepository
) {
    fun createStorageLocation(path: LocationPath, description: String = ""): StorageLocation {
        if (repository.existsByPath(path)) {
            throw DuplicateStorageLocationException("A storage location with path '${path.value}' already exists.")
        }
        
        return StorageLocation(StorageLocationId.generate(), path, description)
    }
}
