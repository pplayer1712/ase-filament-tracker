package ase_filament_tracker.application.dto

data class AddMaterialProfileCommand(
    val name: String,
    val bedTemperatureInCelsius: Int,
    val nozzleTemperatureInCelsius: Int,
    val printSpeedInMmPerS: Int,
    val fanSpeedInPercent: Int,
    val retractionDistanceInMm: Double,
    val retractionSpeedInMmPerS: Int
)
