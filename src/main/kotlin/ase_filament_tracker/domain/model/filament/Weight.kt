package ase_filament_tracker.domain.model.filament

data class Weight(val valueInGrams: Double) {
    init {
        require(valueInGrams >= 0) { "Gewicht darf nicht negativ sein: $valueInGrams" }
    }

    operator fun minus(other: Weight): Weight {
        return Weight(this.valueInGrams - other.valueInGrams)
    }

    operator fun plus(other: Weight): Weight {
        return Weight(this.valueInGrams + other.valueInGrams)
    }
}
