package ase_filament_tracker.domain.service

import ase_filament_tracker.domain.model.consumption.ConsumptionEvent
import ase_filament_tracker.domain.model.consumption.ConsumptionEventId
import ase_filament_tracker.domain.model.consumption.ConsumptionType
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.model.filament.Weight
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit

class StatisticsServiceTest {

    @Test
    fun `should calculate consumption in period`() {
        val service = StatisticsService()
        val filamentId = FilamentId.generate()
        val now = Instant.now()
        
        val events = listOf(
            ConsumptionEvent(ConsumptionEventId.generate(), filamentId, Weight(10.0), ConsumptionType.MANUAL, now.minus(5, ChronoUnit.DAYS)),
            ConsumptionEvent(ConsumptionEventId.generate(), filamentId, Weight(20.0), ConsumptionType.PRINT_JOB, now.minus(1, ChronoUnit.DAYS)),
            ConsumptionEvent(ConsumptionEventId.generate(), filamentId, Weight(50.0), ConsumptionType.MANUAL, now.plus(5, ChronoUnit.DAYS))
        )

        val total = service.calculateConsumptionInPeriod(events, now.minus(2, ChronoUnit.DAYS), now)
        
        assertEquals(20.0, total.valueInGrams)
    }

    @Test
    fun `should aggregate by filament`() {
        val service = StatisticsService()
        val f1 = FilamentId.generate()
        val f2 = FilamentId.generate()
        
        val events = listOf(
            ConsumptionEvent(ConsumptionEventId.generate(), f1, Weight(10.0), ConsumptionType.MANUAL),
            ConsumptionEvent(ConsumptionEventId.generate(), f1, Weight(20.0), ConsumptionType.PRINT_JOB),
            ConsumptionEvent(ConsumptionEventId.generate(), f2, Weight(5.0), ConsumptionType.MANUAL)
        )

        val aggregation = service.aggregateByFilament(events)
        
        assertEquals(30.0, aggregation[f1]?.valueInGrams)
        assertEquals(5.0, aggregation[f2]?.valueInGrams)
    }
}
