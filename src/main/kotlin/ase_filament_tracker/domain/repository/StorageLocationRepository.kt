package ase_filament_tracker.domain.repository

import ase_filament_tracker.domain.model.StorgaeLocation.LocationPath
import ase_filament_tracker.domain.model.StorgaeLocation.StorageLocation
import ase_filament_tracker.domain.model.StorgaeLocation.StorageLocationId

interface StorageLocationRepository {
    fun save(storageLocation: StorageLocation)
    fun findById(id: StorageLocationId): StorageLocation?
    fun existsByPath(path: LocationPath): Boolean
}
