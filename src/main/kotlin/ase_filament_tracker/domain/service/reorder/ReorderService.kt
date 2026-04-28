package ase_filament_tracker.domain.service.reorder

import ase_filament_tracker.domain.model.filament.Filament
import ase_filament_tracker.domain.model.filament.ReorderThreshold

class ReorderService {

    fun isReorderRecommended(filament: Filament, threshold: ReorderThreshold): Boolean {
        return filament.remainingWeight.valueInGrams <= threshold.valueInGrams
    }
}
