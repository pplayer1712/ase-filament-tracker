package ase_filament_tracker.domain.model.MaterialProfile

import java.util.UUID

@JvmInline
value class MaterialProfileId(val value: UUID) {
    companion object {
        fun generate(): MaterialProfileId = MaterialProfileId(UUID.randomUUID())
    }
}

class MaterialProfile(
    val id: MaterialProfileId,
    val name: String,
    val bedTemperature: Temperature,
    val nozzleTemperature: Temperature,
    val printSpeed: PrintSpeed,
    val fanSpeed: FanSpeed,
    val retractionSettings: RetractionSettings
) {
    init {
        require(name.isNotBlank()) { "Profile name cannot be empty" }
    }
}
