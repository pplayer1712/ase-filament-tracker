package ase_filament_tracker.domain.repository

import ase_filament_tracker.domain.model.PrintJob.PrintJob
import ase_filament_tracker.domain.model.PrintJob.PrintJobId
import ase_filament_tracker.domain.model.filament.FilamentId

interface PrintJobRepository {
    fun save(printJob: PrintJob): PrintJob
    fun findById(id: PrintJobId): PrintJob?
    fun findAll(): List<PrintJob>
    fun findByFilamentId(filamentId: FilamentId): List<PrintJob>
    fun delete(id: PrintJobId)
}
