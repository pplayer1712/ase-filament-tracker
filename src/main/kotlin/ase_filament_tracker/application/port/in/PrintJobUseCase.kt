package ase_filament_tracker.application.port.`in`

import ase_filament_tracker.application.dto.CreatePrintJobCommand
import ase_filament_tracker.application.dto.FinishPrintJobCommand
import ase_filament_tracker.domain.model.PrintJob.PrintJob
import ase_filament_tracker.domain.model.PrintJob.PrintJobId
import ase_filament_tracker.domain.model.filament.FilamentId

interface PrintJobUseCase {
    fun createPrintJob(command: CreatePrintJobCommand): PrintJobId
    fun finishPrintJob(command: FinishPrintJobCommand)
    fun getAllPrintJobs(): List<PrintJob>
    fun getPrintJobById(id: PrintJobId): PrintJob?
    fun getPrintJobsByFilament(filamentId: FilamentId): List<PrintJob>
    fun deletePrintJob(id: PrintJobId)
}
