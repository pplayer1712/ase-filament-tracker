package ase_filament_tracker.domain.model.MaterialProfile

data class PrintSpeed(val valueInMmPerSecond: Int) {
    init {
        require(valueInMmPerSecond > 0) { "Print speed must be positive: $valueInMmPerSecond" }
    }
}