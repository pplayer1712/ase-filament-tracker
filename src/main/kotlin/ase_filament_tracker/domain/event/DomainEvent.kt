package ase_filament_tracker.domain.event

import java.time.Instant

interface DomainEvent {
    val occurredOn: Instant
}
