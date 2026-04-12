package ase_filament_tracker.domain.model.filament

import java.util.UUID

@JvmInline
value class FilamentId(val value: UUID) {
    companion object {
        fun generate(): FilamentId = FilamentId(UUID.randomUUID())
    }
}
