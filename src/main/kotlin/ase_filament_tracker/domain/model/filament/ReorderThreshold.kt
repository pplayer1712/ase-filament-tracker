package ase_filament_tracker.domain.model.filament

data class ReorderThreshold(val valueInGrams: Double) {
    init {
        require(valueInGrams >= 0) { "Nachbestellschwelle darf nicht negativ sein: $valueInGrams" }
    }
}
