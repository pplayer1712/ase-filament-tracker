package ase_filament_tracker.application.service

import ase_filament_tracker.application.dto.RecordConsumptionCommand
import ase_filament_tracker.domain.model.filament.Color
import ase_filament_tracker.domain.model.filament.Diameter
import ase_filament_tracker.domain.model.filament.Filament
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.model.filament.Weight
import ase_filament_tracker.domain.repository.ConsumptionEventRepository
import ase_filament_tracker.domain.repository.FilamentRepository
import ase_filament_tracker.domain.service.consumption.ConsumptionService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class ConsumptionApplicationServiceTest {

    @Test
    fun `should orchestrate consumption and save updated filament`() {
        val filamentRepoMock = mockk<FilamentRepository>(relaxed = true)
        val eventRepoMock = mockk<ConsumptionEventRepository>(relaxed = true)
        val domainService = ConsumptionService()
        val appService = ConsumptionApplicationService(filamentRepoMock, domainService, eventRepoMock)

        val filamentId = FilamentId.generate()
        val dummyFilament = Filament(
            id = filamentId,
            color = Color("Blue", "#0000FF"),
            diameter = Diameter(1.75),
            remainingWeight = Weight(500.0)
        )

        every { filamentRepoMock.findById(filamentId) } returns dummyFilament

        val command = RecordConsumptionCommand(
            filamentId = filamentId.value,
            amountInGrams = 100.0,
            isPrintJob = false
        )

        appService.recordConsumption(command)

        // Sicherstellen, dass save exakt 1x mit dem mutierten Filament gecalled wurde
        verify(exactly = 1) { filamentRepoMock.save(dummyFilament) }
        // Sicherstellen, dass das ConsumptionEvent gespeichert wurde
        verify(exactly = 1) { eventRepoMock.save(any()) }
        assert(dummyFilament.remainingWeight.valueInGrams == 400.0)
    }
}
