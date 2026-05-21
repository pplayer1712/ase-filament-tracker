package ase_filament_tracker.adapter.outbound.persistence

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "filaments")
class FilamentJpaEntity(
    @Id
    var id: String, 
    
    var colorName: String,
    var colorHex: String?,
    
    var diameterInMm: Double,
    var remainingWeightInGrams: Double
)
