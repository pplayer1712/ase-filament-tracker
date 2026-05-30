package ase_filament_tracker.application.service

import ase_filament_tracker.application.dto.CreatePrintJobCommand
import ase_filament_tracker.application.dto.FinishPrintJobCommand
import ase_filament_tracker.application.port.`in`.PrintJobUseCase
import ase_filament_tracker.domain.model.PrintJob.MaterialUsage
import ase_filament_tracker.domain.model.PrintJob.PrintJob
import ase_filament_tracker.domain.model.PrintJob.PrintJobId
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.repository.FilamentRepository
import ase_filament_tracker.domain.repository.PrintJobRepository
import ase_filament_tracker.domain.service.consumption.ConsumptionService
import ase_filament_tracker.domain.service.consumption.PrintJobConsumptionStrategy

class PrintJobApplicationService(
    private val printJobRepository: PrintJobRepository,
    private val filamentRepository: FilamentRepository,
    private val consumptionService: ConsumptionService
) : PrintJobUseCase {

    override fun createPrintJob(command: CreatePrintJobCommand): PrintJobId {
        val filamentId = FilamentId(command.filamentId)
        filamentRepository.findById(filamentId)
            ?: throw IllegalArgumentException("Filament not found: ${command.filamentId}")
        val printJob = PrintJob(
            id = PrintJobId.generate(),
            filamentId = filamentId,
            estimatedUsage = MaterialUsage(command.estimatedUsageInGrams)
        )
        printJobRepository.save(printJob)
        return printJob.id
    }

    override fun finishPrintJob(command: FinishPrintJobCommand) {
        val printJobId = PrintJobId(command.printJobId)
        val printJob = printJobRepository.findById(printJobId)
            ?: throw IllegalArgumentException("PrintJob not found: ${command.printJobId}")
        val actualUsage = MaterialUsage(command.actualUsageInGrams)

        printJob.finishJob(actualUsage)

        // Reduce filament stock
        val filament = filamentRepository.findById(printJob.filamentId)
            ?: throw IllegalArgumentException("Filament not found: ${printJob.filamentId}")
        val strategy = PrintJobConsumptionStrategy(actualUsage = actualUsage, includePurgeLineWaste = true)
        consumptionService.recordConsumption(filament, strategy)

        filamentRepository.save(filament)
        printJobRepository.save(printJob)
    }

    override fun getAllPrintJobs(): List<PrintJob> = printJobRepository.findAll()

    override fun getPrintJobById(id: PrintJobId): PrintJob? = printJobRepository.findById(id)

    override fun getPrintJobsByFilament(filamentId: FilamentId): List<PrintJob> =
        printJobRepository.findByFilamentId(filamentId)

    override fun deletePrintJob(id: PrintJobId) = printJobRepository.delete(id)
}
