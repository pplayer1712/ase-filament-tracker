package ase_filament_tracker.domain.model.MaterialProfile

data class RetractionSettings(
    val distanceInMm: Double,
    val speedInMmPerSecond: Int
) {
    init {
        require(distanceInMm > 0) { "Retraction distance must be positive: $distanceInMm" }
        require(speedInMmPerSecond > 0) { "Retraction speed must be positive: $speedInMmPerSecond" }
    }
}