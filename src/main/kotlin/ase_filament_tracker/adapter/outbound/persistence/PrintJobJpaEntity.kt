package ase_filament_tracker.adapter.outbound.persistence

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "print_jobs")
class PrintJobJpaEntity(
    @Id
    var id: String,
    var filamentId: String,
    var estimatedUsageInGrams: Double,
    var status: String
)
