package ase_filament_tracker.domain.model.filament

data class Diameter(val valueInMm: Double) {
    init {
        require(valueInMm > 0) { "Durchmesser muss größer als 0 sein: $valueInMm" }
    }
}
