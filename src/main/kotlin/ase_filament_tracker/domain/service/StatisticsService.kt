package ase_filament_tracker.domain.service

import ase_filament_tracker.domain.model.consumption.ConsumptionEvent
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.model.filament.Weight
import java.time.Instant

class StatisticsService {

    fun calculateTotalConsumption(events: List<ConsumptionEvent>): Weight {
        val totalGrams = events.sumOf { it.consumedWeight.valueInGrams }
        return Weight(totalGrams)
    }

    fun calculateConsumptionInPeriod(events: List<ConsumptionEvent>, start: Instant, end: Instant): Weight {
        val filtered = events.filter { 
            !it.occurredOn.isBefore(start) && !it.occurredOn.isAfter(end)
        }
        return calculateTotalConsumption(filtered)
    }

    fun aggregateByFilament(events: List<ConsumptionEvent>): Map<FilamentId, Weight> {
        return events.groupBy { it.filamentId }
            .mapValues { (_, filamentEvents) -> calculateTotalConsumption(filamentEvents) }
    }
}
