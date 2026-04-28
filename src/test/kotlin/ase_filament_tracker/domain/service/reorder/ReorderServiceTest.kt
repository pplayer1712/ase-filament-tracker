package ase_filament_tracker.domain.service.reorder

import ase_filament_tracker.domain.model.filament.Color
import ase_filament_tracker.domain.model.filament.Diameter
import ase_filament_tracker.domain.model.filament.Filament
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.model.filament.ReorderThreshold
import ase_filament_tracker.domain.model.filament.Weight
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ReorderServiceTest {

    @Test
    fun `should recommend reorder when weight is below threshold`() {
        val filament = Filament(
            id = FilamentId.generate(),
            color = Color("Black", "#000000"),
            diameter = Diameter(1.75),
            remainingWeight = Weight(150.0)
        )
        val threshold = ReorderThreshold(200.0)
        val service = ReorderService()

        val recommendation = service.isReorderRecommended(filament, threshold)

        assertTrue(recommendation, "Da 150g < 200g, muss nachbestellt werden")
    }

    @Test
    fun `should not recommend reorder when weight is above threshold`() {
        val filament = Filament(
            id = FilamentId.generate(),
            color = Color("White", "#FFFFFF"),
            diameter = Diameter(1.75),
            remainingWeight = Weight(500.0)
        )
        val threshold = ReorderThreshold(200.0)
        val service = ReorderService()

        val recommendation = service.isReorderRecommended(filament, threshold)

        assertFalse(recommendation, "Da 500g > 200g, ist noch genug Material vorhanden")
    }
}
