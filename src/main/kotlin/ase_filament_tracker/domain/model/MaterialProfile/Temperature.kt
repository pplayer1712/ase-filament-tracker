package ase_filament_tracker.domain.model.MaterialProfile

data class Temperature(val valueInCelsius: Int) {
    init {
        require(valueInCelsius > 0) { "Print temperature must be positive: $valueInCelsius" }
    }
}