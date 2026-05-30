package ase_filament_tracker.adapter.inbound.rest

import ase_filament_tracker.application.dto.AddFilamentCommand
import ase_filament_tracker.application.dto.RecordConsumptionCommand
import ase_filament_tracker.application.port.`in`.AddFilamentUseCase
import ase_filament_tracker.application.port.`in`.DeleteFilamentUseCase
import ase_filament_tracker.application.port.`in`.GetFilamentUseCase
import ase_filament_tracker.application.port.`in`.RecordConsumptionUseCase
import ase_filament_tracker.domain.model.filament.FilamentId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/filaments")
class FilamentController(
    private val addFilamentUseCase: AddFilamentUseCase,
    private val recordConsumptionUseCase: RecordConsumptionUseCase,
    private val getFilamentUseCase: GetFilamentUseCase,
    private val deleteFilamentUseCase: DeleteFilamentUseCase
) {

    @GetMapping
    fun getAllFilaments(): ResponseEntity<List<FilamentResponse>> {
        val filaments = getFilamentUseCase.getAllFilaments()
            .map { FilamentResponse(it.id.value.toString(), it.color.name, it.color.hexCode, it.diameter.valueInMm, it.remainingWeight.valueInGrams) }
        return ResponseEntity.ok(filaments)
    }

    @GetMapping("/{id}")
    fun getFilament(@PathVariable id: UUID): ResponseEntity<FilamentResponse> {
        val filament = getFilamentUseCase.getFilamentById(FilamentId(id))
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(
            FilamentResponse(filament.id.value.toString(), filament.color.name, filament.color.hexCode, filament.diameter.valueInMm, filament.remainingWeight.valueInGrams)
        )
    }

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

    @DeleteMapping("/{id}")
    fun deleteFilament(@PathVariable id: UUID): ResponseEntity<Void> {
        deleteFilamentUseCase.deleteFilament(FilamentId(id))
        return ResponseEntity.noContent().build()
    }
}

data class FilamentResponse(
    val id: String,
    val colorName: String,
    val colorHex: String?,
    val diameterInMm: Double,
    val remainingWeightInGrams: Double
)

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
