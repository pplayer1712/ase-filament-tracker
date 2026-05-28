package ase_filament_tracker.domain.model.StorageLocation

data class LocationPath(val value: String) {
    init {
        require(value.isNotBlank()) { "Location path cannot be empty" }
    }
}
