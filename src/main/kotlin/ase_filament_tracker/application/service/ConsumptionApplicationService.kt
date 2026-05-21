package ase_filament_tracker.application.service

import ase_filament_tracker.application.dto.RecordConsumptionCommand
import ase_filament_tracker.application.port.`in`.RecordConsumptionUseCase
import ase_filament_tracker.domain.model.PrintJob.MaterialUsage
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.model.filament.Weight
import ase_filament_tracker.domain.repository.FilamentRepository
import ase_filament_tracker.domain.service.consumption.ConsumptionService
import ase_filament_tracker.domain.service.consumption.ManualConsumptionStrategy
import ase_filament_tracker.domain.service.consumption.PrintJobConsumptionStrategy

class ConsumptionApplicationService(
    private val filamentRepository: FilamentRepository,
    private val consumptionDomainService: ConsumptionService
) : RecordConsumptionUseCase {

    override fun recordConsumption(command: RecordConsumptionCommand) {
        val filamentId = FilamentId(command.filamentId)
        val filament = filamentRepository.findById(filamentId) 
            ?: throw IllegalArgumentException("Filament not found")

        val strategy = if (command.isPrintJob) {
            PrintJobConsumptionStrategy(
                actualUsage = MaterialUsage(command.amountInGrams),
                includePurgeLineWaste = command.includePurgeLineWaste
            )
        } else {
            ManualConsumptionStrategy(removedAmount = Weight(command.amountInGrams))
        }

        // Orchestration: Delegation an den Domain Service
        consumptionDomainService.recordConsumption(filament, strategy)
        
        // Persistence
        filamentRepository.save(filament)
    }
}
