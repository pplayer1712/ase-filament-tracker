package ase_filament_tracker.application.dto

data class AddFilamentCommand(
    val colorName: String,
    val colorHex: String,
    val diameterInMm: Double,
    val initialWeightInGrams: Double
)
