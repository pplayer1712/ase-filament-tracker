package ase_filament_tracker.adapter.outbound.persistence

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "consumption_events")
class ConsumptionEventJpaEntity(
    @Id
    var id: String,
    var filamentId: String,
    var consumedWeightInGrams: Double,
    var consumptionType: String,
    var occurredOn: Instant
)
