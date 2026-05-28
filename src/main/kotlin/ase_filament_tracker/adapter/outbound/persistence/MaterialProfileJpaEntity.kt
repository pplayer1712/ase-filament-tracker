package ase_filament_tracker.adapter.outbound.persistence

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "material_profiles")
class MaterialProfileJpaEntity(
    @Id
    var id: String,
    var name: String,
    var bedTemperatureInCelsius: Int,
    var nozzleTemperatureInCelsius: Int,
    var printSpeedInMmPerS: Int,
    var fanSpeedInPercent: Int,
    var retractionDistanceInMm: Double,
    var retractionSpeedInMmPerS: Int
)
