package ase_filament_tracker.application.service

import ase_filament_tracker.application.dto.AddMaterialProfileCommand
import ase_filament_tracker.application.port.`in`.MaterialProfileUseCase
import ase_filament_tracker.domain.model.MaterialProfile.FanSpeed
import ase_filament_tracker.domain.model.MaterialProfile.MaterialProfile
import ase_filament_tracker.domain.model.MaterialProfile.MaterialProfileId
import ase_filament_tracker.domain.model.MaterialProfile.PrintSpeed
import ase_filament_tracker.domain.model.MaterialProfile.RetractionSettings
import ase_filament_tracker.domain.model.MaterialProfile.Temperature
import ase_filament_tracker.domain.repository.MaterialProfileRepository

class MaterialProfileApplicationService(
    private val materialProfileRepository: MaterialProfileRepository
) : MaterialProfileUseCase {

    override fun addMaterialProfile(command: AddMaterialProfileCommand): MaterialProfileId {
        val profile = MaterialProfile(
            id = MaterialProfileId.generate(),
            name = command.name,
            bedTemperature = Temperature(command.bedTemperatureInCelsius),
            nozzleTemperature = Temperature(command.nozzleTemperatureInCelsius),
            printSpeed = PrintSpeed(command.printSpeedInMmPerS),
            fanSpeed = FanSpeed(command.fanSpeedInPercent),
            retractionSettings = RetractionSettings(command.retractionDistanceInMm, command.retractionSpeedInMmPerS)
        )
        materialProfileRepository.save(profile)
        return profile.id
    }

    override fun getAllMaterialProfiles(): List<MaterialProfile> {
        return materialProfileRepository.findAll()
    }

    override fun getMaterialProfileById(id: MaterialProfileId): MaterialProfile? {
        return materialProfileRepository.findById(id)
    }

    override fun deleteMaterialProfile(id: MaterialProfileId) {
        materialProfileRepository.delete(id)
    }
}
