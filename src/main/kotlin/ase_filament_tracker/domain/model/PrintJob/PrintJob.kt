package ase_filament_tracker.domain.model.PrintJob

import ase_filament_tracker.domain.model.filament.FilamentId

enum class PrintJobStatus {
    IN_PROGRESS,
    FINISHED,
    CANCELLED
}

class PrintJob(
    val id: PrintJobId,
    val filamentId: FilamentId,
    val estimatedUsage: MaterialUsage,
    var status: PrintJobStatus = PrintJobStatus.IN_PROGRESS
) {

    fun finishJob(actualUsage: MaterialUsage) {
        require(status == PrintJobStatus.IN_PROGRESS) { "Only full in-progress jobs can be finished." }
        
        status = PrintJobStatus.FINISHED
    }
}
