package ase_filament_tracker.domain.model.filament

class Filament(
    val id: FilamentId,
    val color: Color,
    val diameter: Diameter,
    var remainingWeight: Weight
) {
    fun consume(amount: Weight) {
        val newWeight = remainingWeight.valueInGrams - amount.valueInGrams
        require(newWeight >= 0) {
            "Nicht genug Filament vorhanden. Verfügbar: ${remainingWeight.valueInGrams}g, Angefordert: ${amount.valueInGrams}g"
        }
        remainingWeight = Weight(newWeight)
    }
}
