package ase_filament_tracker.domain.service.consumption

import ase_filament_tracker.domain.model.PrintJob.MaterialUsage
import ase_filament_tracker.domain.model.filament.Weight

class PrintJobConsumptionStrategy(
    private val actualUsage: MaterialUsage,
    private val includePurgeLineWaste: Boolean = true
) : ConsumptionStrategy {
    
    private val PURGE_WASTE_GRAMS = 2.0

    override fun calculateConsumption(): Weight {
        val baseGrams = actualUsage.valueInGrams
        val finalGrams = if (includePurgeLineWaste) baseGrams + PURGE_WASTE_GRAMS else baseGrams
        
        return Weight(finalGrams)
    }
}
