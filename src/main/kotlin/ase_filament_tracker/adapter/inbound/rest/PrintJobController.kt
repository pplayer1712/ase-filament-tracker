package ase_filament_tracker.adapter.inbound.rest

import ase_filament_tracker.application.dto.CreatePrintJobCommand
import ase_filament_tracker.application.dto.FinishPrintJobCommand
import ase_filament_tracker.application.port.`in`.PrintJobUseCase
import ase_filament_tracker.domain.model.PrintJob.PrintJobId
import ase_filament_tracker.domain.model.filament.FilamentId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/print-jobs")
class PrintJobController(
    private val printJobUseCase: PrintJobUseCase
) {

    @GetMapping
    fun getAllPrintJobs(): ResponseEntity<List<PrintJobResponse>> {
        val jobs = printJobUseCase.getAllPrintJobs()
            .map { PrintJobResponse(it.id.value.toString(), it.filamentId.value.toString(), it.estimatedUsage.valueInGrams, it.status.name) }
        return ResponseEntity.ok(jobs)
    }

    @GetMapping("/{id}")
    fun getPrintJob(@PathVariable id: UUID): ResponseEntity<PrintJobResponse> {
        val job = printJobUseCase.getPrintJobById(PrintJobId(id))
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(
            PrintJobResponse(job.id.value.toString(), job.filamentId.value.toString(), job.estimatedUsage.valueInGrams, job.status.name)
        )
    }

    @GetMapping("/by-filament/{filamentId}")
    fun getPrintJobsByFilament(@PathVariable filamentId: UUID): ResponseEntity<List<PrintJobResponse>> {
        val jobs = printJobUseCase.getPrintJobsByFilament(FilamentId(filamentId))
            .map { PrintJobResponse(it.id.value.toString(), it.filamentId.value.toString(), it.estimatedUsage.valueInGrams, it.status.name) }
        return ResponseEntity.ok(jobs)
    }

    @PostMapping
    fun createPrintJob(@RequestBody request: CreatePrintJobRequest): ResponseEntity<Map<String, String>> {
        val command = CreatePrintJobCommand(
            filamentId = request.filamentId,
            estimatedUsageInGrams = request.estimatedUsageInGrams
        )
        val id = printJobUseCase.createPrintJob(command)
        return ResponseEntity.ok(mapOf("id" to id.value.toString()))
    }

    @PostMapping("/{id}/finish")
    fun finishPrintJob(
        @PathVariable id: UUID,
        @RequestBody request: FinishPrintJobRequest
    ): ResponseEntity<Void> {
        val command = FinishPrintJobCommand(
            printJobId = id,
            actualUsageInGrams = request.actualUsageInGrams
        )
        printJobUseCase.finishPrintJob(command)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun deletePrintJob(@PathVariable id: UUID): ResponseEntity<Void> {
        printJobUseCase.deletePrintJob(PrintJobId(id))
        return ResponseEntity.noContent().build()
    }
}

data class PrintJobResponse(
    val id: String,
    val filamentId: String,
    val estimatedUsageInGrams: Double,
    val status: String
)

data class CreatePrintJobRequest(
    val filamentId: UUID,
    val estimatedUsageInGrams: Double
)

data class FinishPrintJobRequest(
    val actualUsageInGrams: Double
)
