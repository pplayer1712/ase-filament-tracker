package ase_filament_tracker.adapter.outbound.persistence

import ase_filament_tracker.domain.model.MaterialProfile.FanSpeed
import ase_filament_tracker.domain.model.MaterialProfile.MaterialProfile
import ase_filament_tracker.domain.model.MaterialProfile.MaterialProfileId
import ase_filament_tracker.domain.model.MaterialProfile.PrintSpeed
import ase_filament_tracker.domain.model.MaterialProfile.RetractionSettings
import ase_filament_tracker.domain.model.MaterialProfile.Temperature
import ase_filament_tracker.domain.repository.MaterialProfileRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MaterialProfilePersistenceAdapter(
    private val springDataRepository: SpringDataMaterialProfileRepository
) : MaterialProfileRepository {

    override fun save(profile: MaterialProfile): MaterialProfile {
        val entity = MaterialProfileJpaEntity(
            id = profile.id.value.toString(),
            name = profile.name,
            bedTemperatureInCelsius = profile.bedTemperature.valueInCelsius,
            nozzleTemperatureInCelsius = profile.nozzleTemperature.valueInCelsius,
            printSpeedInMmPerS = profile.printSpeed.valueInMmPerSecond,
            fanSpeedInPercent = profile.fanSpeed.valueInPercent,
            retractionDistanceInMm = profile.retractionSettings.distanceInMm,
            retractionSpeedInMmPerS = profile.retractionSettings.speedInMmPerSecond
        )
        springDataRepository.save(entity)
        return profile
    }

    override fun findById(id: MaterialProfileId): MaterialProfile? {
        val opt = springDataRepository.findById(id.value.toString())
        if (opt.isEmpty) return null
        return toDomain(opt.get())
    }

    override fun findAll(): List<MaterialProfile> {
        return springDataRepository.findAll().map { toDomain(it) }
    }

    override fun delete(id: MaterialProfileId) {
        springDataRepository.deleteById(id.value.toString())
    }

    private fun toDomain(entity: MaterialProfileJpaEntity): MaterialProfile {
        return MaterialProfile(
            id = MaterialProfileId(UUID.fromString(entity.id)),
            name = entity.name,
            bedTemperature = Temperature(entity.bedTemperatureInCelsius),
            nozzleTemperature = Temperature(entity.nozzleTemperatureInCelsius),
            printSpeed = PrintSpeed(entity.printSpeedInMmPerS),
            fanSpeed = FanSpeed(entity.fanSpeedInPercent),
            retractionSettings = RetractionSettings(entity.retractionDistanceInMm, entity.retractionSpeedInMmPerS)
        )
    }
}
