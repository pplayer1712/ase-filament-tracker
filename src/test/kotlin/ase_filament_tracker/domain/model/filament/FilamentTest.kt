package ase_filament_tracker.domain.model.filament

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FilamentTest {

    @Test
    fun `Weight cannot be negative`() {
        assertThrows<IllegalArgumentException> {
            Weight(-10.0)
        }
    }

    @Test
    fun `Weight addition and subtraction work correctly`() {
        val w1 = Weight(100.0)
        val w2 = Weight(50.0)
        
        assertEquals(150.0, (w1 + w2).valueInGrams)
        assertEquals(50.0, (w1 - w2).valueInGrams)
    }

    @Test
    fun `Color validates hex codes correctly`() {
        val validColor = Color("Red", "#FF0000")
        assertEquals("#FF0000", validColor.hexCode)

        assertThrows<IllegalArgumentException> {
            Color("Invalid", "FF0000") // missing #
        }
        
        assertThrows<IllegalArgumentException> {
            Color("Invalid", "#FF000") // wrong length
        }
    }

    @Test
    fun `Filament consumes weight correctly`() {
        val filament = Filament(
            id = FilamentId.generate(),
            color = Color("Black"),
            diameter = Diameter(1.75),
            remainingWeight = Weight(1000.0)
        )

        filament.consume(Weight(200.0))
        assertEquals(800.0, filament.remainingWeight.valueInGrams)
    }

    @Test
    fun `Filament throws exception when consuming more than remaining`() {
        val filament = Filament(
            id = FilamentId.generate(),
            color = Color("Blue"),
            diameter = Diameter(1.75),
            remainingWeight = Weight(500.0)
        )

        assertThrows<IllegalArgumentException> {
            filament.consume(Weight(600.0))
        }
    }
}
