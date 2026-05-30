package ase_filament_tracker.domain.model.filament

class Filament(
    val id: FilamentId,
    val color: Color,
    val diameter: Diameter,
    var remainingWeight: Weight
) {
    fun consume(amount: Weight) {
        require(remainingWeight >= amount) {
            "Nicht genug Filament vorhanden. Verfügbar: ${remainingWeight.valueInGrams}g, Angefordert: ${amount.valueInGrams}g"
        }
        remainingWeight -= amount
    }
}
