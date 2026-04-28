package ase_filament_tracker.domain.service.consumption

import ase_filament_tracker.domain.model.PrintJob.MaterialUsage
import ase_filament_tracker.domain.model.filament.Color
import ase_filament_tracker.domain.model.filament.Diameter
import ase_filament_tracker.domain.model.filament.Filament
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.model.filament.Weight
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ConsumptionServiceTest {

    @Test
    fun `should reduce filament weight correctly when using ManualConsumptionStrategy`() {
        // Arrange (ATRIP: Arrange)
        val filament = Filament(
            id = FilamentId.generate(),
            color = Color("Red", "#FF0000"),
            diameter = Diameter(1.75),
            remainingWeight = Weight(1000.0)
        )
        val service = ConsumptionService()
        val strategy = ManualConsumptionStrategy(Weight(150.0))

        // Act (ATRIP: Act)
        service.recordConsumption(filament, strategy)

        // Assert (ATRIP: Assert)
        assertEquals(850.0, filament.remainingWeight.valueInGrams, "Restgewicht sollte 1000 - 150 = 850 sein")
    }

    @Test
    fun `should throw exception when consuming more than available`() {
        // Arrange
        val filament = Filament(
            id = FilamentId.generate(),
            color = Color("Blue", "#0000FF"),
            diameter = Diameter(1.75),
            remainingWeight = Weight(100.0)
        )
        val service = ConsumptionService()
        val strategy = ManualConsumptionStrategy(Weight(200.0))

        // Act & Assert
        val exception = assertThrows(IllegalArgumentException::class.java) {
            service.recordConsumption(filament, strategy)
        }
        
        assertTrue(exception.message!!.contains("Nicht genug Filament"), "Exception sollte auf fehlendes Filament hinweisen")
    }

    @Test
    fun `should add purge waste when using PrintJobConsumptionStrategy with default settings`() {
        // Arrange
        val filament = Filament(
            id = FilamentId.generate(),
            color = Color("Black", "#000000"),
            diameter = Diameter(1.75),
            remainingWeight = Weight(500.0)
        )
        val service = ConsumptionService()
        // Ein Druckjob verbraucht laut Slicer 50g
        val strategy = PrintJobConsumptionStrategy(MaterialUsage(50.0))

        // Act
        service.recordConsumption(filament, strategy)

        // Assert
        // Erwartet: 500 - (50 + 2g Purge Waste) = 448g
        assertEquals(448.0, filament.remainingWeight.valueInGrams)
    }
}
