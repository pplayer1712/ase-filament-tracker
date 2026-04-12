package ase_filament_tracker.domain.model.StorgaeLocation

import java.util.UUID

@JvmInline
value class StorageLocationId(val value: UUID) {
    companion object {
        fun generate(): StorageLocationId = StorageLocationId(UUID.randomUUID())
    }
}
