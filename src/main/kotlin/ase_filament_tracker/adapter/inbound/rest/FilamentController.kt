package ase_filament_tracker.adapter.inbound.rest

import ase_filament_tracker.application.dto.AddFilamentCommand
import ase_filament_tracker.application.dto.RecordConsumptionCommand
import ase_filament_tracker.application.port.`in`.AddFilamentUseCase
import ase_filament_tracker.application.port.`in`.RecordConsumptionUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/filaments")
class FilamentController(
    private val addFilamentUseCase: AddFilamentUseCase,
    private val recordConsumptionUseCase: RecordConsumptionUseCase
) {

    @PostMapping
    fun addFilament(@RequestBody request: AddFilamentRequest): ResponseEntity<Map<String, String>> {
        val command = AddFilamentCommand(
            colorName = request.colorName,
            colorHex = request.colorHex,
            diameterInMm = request.diameterInMm,
            initialWeightInGrams = request.initialWeightInGrams
        )
        val filamentId = addFilamentUseCase.addFilament(command)
        return ResponseEntity.ok(mapOf("id" to filamentId.value.toString()))
    }

    @PostMapping("/{id}/consume")
    fun consumeFilament(
        @PathVariable id: UUID, 
        @RequestBody request: ConsumeRequest
    ): ResponseEntity<Void> {
        val command = RecordConsumptionCommand(
            filamentId = id,
            amountInGrams = request.amountInGrams,
            isPrintJob = request.isPrintJob,
            includePurgeLineWaste = request.includePurgeLineWaste ?: false
        )
        recordConsumptionUseCase.recordConsumption(command)
        return ResponseEntity.ok().build()
    }
}

data class AddFilamentRequest(
    val colorName: String,
    val colorHex: String,
    val diameterInMm: Double,
    val initialWeightInGrams: Double
)

data class ConsumeRequest(
    val amountInGrams: Double,
    val isPrintJob: Boolean = false,
    val includePurgeLineWaste: Boolean? = false
)
