package ase_filament_tracker.domain.service.consumption

import ase_filament_tracker.domain.model.filament.Weight

interface ConsumptionStrategy {
    fun calculateConsumption(): Weight
}
