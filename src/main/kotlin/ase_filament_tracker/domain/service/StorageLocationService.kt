package ase_filament_tracker.domain.service

import ase_filament_tracker.domain.model.StorgaeLocation.DuplicateStorageLocationException
import ase_filament_tracker.domain.model.StorgaeLocation.LocationPath
import ase_filament_tracker.domain.model.StorgaeLocation.StorageLocation
import ase_filament_tracker.domain.model.StorgaeLocation.StorageLocationId
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
