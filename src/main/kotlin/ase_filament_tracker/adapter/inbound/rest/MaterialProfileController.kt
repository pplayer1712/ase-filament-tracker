package ase_filament_tracker.adapter.inbound.rest

import ase_filament_tracker.application.dto.AddMaterialProfileCommand
import ase_filament_tracker.application.port.`in`.MaterialProfileUseCase
import ase_filament_tracker.domain.model.MaterialProfile.MaterialProfileId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/material-profiles")
class MaterialProfileController(
    private val materialProfileUseCase: MaterialProfileUseCase
) {

    @GetMapping
    fun getAllProfiles(): ResponseEntity<List<MaterialProfileResponse>> {
        val profiles = materialProfileUseCase.getAllMaterialProfiles()
            .map { MaterialProfileResponse(
                id = it.id.value.toString(),
                name = it.name,
                bedTemperatureInCelsius = it.bedTemperature.valueInCelsius,
                nozzleTemperatureInCelsius = it.nozzleTemperature.valueInCelsius,
                printSpeedInMmPerS = it.printSpeed.valueInMmPerSecond,
                fanSpeedInPercent = it.fanSpeed.valueInPercent,
                retractionDistanceInMm = it.retractionSettings.distanceInMm,
                retractionSpeedInMmPerS = it.retractionSettings.speedInMmPerSecond
            )}
        return ResponseEntity.ok(profiles)
    }

    @GetMapping("/{id}")
    fun getProfile(@PathVariable id: UUID): ResponseEntity<MaterialProfileResponse> {
        val profile = materialProfileUseCase.getMaterialProfileById(MaterialProfileId(id))
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(
            MaterialProfileResponse(
                id = profile.id.value.toString(),
                name = profile.name,
                bedTemperatureInCelsius = profile.bedTemperature.valueInCelsius,
                nozzleTemperatureInCelsius = profile.nozzleTemperature.valueInCelsius,
                printSpeedInMmPerS = profile.printSpeed.valueInMmPerSecond,
                fanSpeedInPercent = profile.fanSpeed.valueInPercent,
                retractionDistanceInMm = profile.retractionSettings.distanceInMm,
                retractionSpeedInMmPerS = profile.retractionSettings.speedInMmPerSecond
            )
        )
    }

    @PostMapping
    fun addProfile(@RequestBody request: AddMaterialProfileRequest): ResponseEntity<Map<String, String>> {
        val command = AddMaterialProfileCommand(
            name = request.name,
            bedTemperatureInCelsius = request.bedTemperatureInCelsius,
            nozzleTemperatureInCelsius = request.nozzleTemperatureInCelsius,
            printSpeedInMmPerS = request.printSpeedInMmPerS,
            fanSpeedInPercent = request.fanSpeedInPercent,
            retractionDistanceInMm = request.retractionDistanceInMm,
            retractionSpeedInMmPerS = request.retractionSpeedInMmPerS
        )
        val id = materialProfileUseCase.addMaterialProfile(command)
        return ResponseEntity.ok(mapOf("id" to id.value.toString()))
    }

    @DeleteMapping("/{id}")
    fun deleteProfile(@PathVariable id: UUID): ResponseEntity<Void> {
        materialProfileUseCase.deleteMaterialProfile(MaterialProfileId(id))
        return ResponseEntity.noContent().build()
    }
}

data class MaterialProfileResponse(
    val id: String,
    val name: String,
    val bedTemperatureInCelsius: Int,
    val nozzleTemperatureInCelsius: Int,
    val printSpeedInMmPerS: Int,
    val fanSpeedInPercent: Int,
    val retractionDistanceInMm: Double,
    val retractionSpeedInMmPerS: Int
)

data class AddMaterialProfileRequest(
    val name: String,
    val bedTemperatureInCelsius: Int,
    val nozzleTemperatureInCelsius: Int,
    val printSpeedInMmPerS: Int,
    val fanSpeedInPercent: Int,
    val retractionDistanceInMm: Double,
    val retractionSpeedInMmPerS: Int
)
