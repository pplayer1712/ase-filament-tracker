package ase_filament_tracker.domain.model.PrintJob

data class MaterialUsage(val valueInGrams: Double) {
    init {
        require(valueInGrams > 0) { "Material usage must be positive: $valueInGrams" }
    }
}
