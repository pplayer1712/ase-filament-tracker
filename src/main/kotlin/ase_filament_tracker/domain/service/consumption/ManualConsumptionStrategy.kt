package ase_filament_tracker.domain.service.consumption

import ase_filament_tracker.domain.model.filament.Weight

class ManualConsumptionStrategy(
    private val removedAmount: Weight
) : ConsumptionStrategy {
    
    override fun calculateConsumption(): Weight {
        return removedAmount
    }
}
