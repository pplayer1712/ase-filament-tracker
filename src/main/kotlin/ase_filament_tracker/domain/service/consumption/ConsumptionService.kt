package ase_filament_tracker.domain.service.consumption

import ase_filament_tracker.domain.model.filament.Filament


class ConsumptionService {

    fun recordConsumption(filament: Filament, strategy: ConsumptionStrategy) {
        val amountToConsume = strategy.calculateConsumption()
        filament.consume(amountToConsume)
    }
}
