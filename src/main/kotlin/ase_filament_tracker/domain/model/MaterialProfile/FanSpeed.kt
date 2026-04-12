package ase_filament_tracker.domain.model.MaterialProfile

data class FanSpeed(val valueInPercent: Int) {
    init {
        require(valueInPercent in 0..100) { "Fan speed must be between 0 and 100: $valueInPercent" }
    }
}