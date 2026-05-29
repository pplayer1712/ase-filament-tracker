package ase_filament_tracker.domain.repository

import ase_filament_tracker.domain.model.consumption.ConsumptionEvent
import ase_filament_tracker.domain.model.consumption.ConsumptionEventId
import ase_filament_tracker.domain.model.filament.FilamentId

interface ConsumptionEventRepository {
    fun save(event: ConsumptionEvent): ConsumptionEvent
    fun findById(id: ConsumptionEventId): ConsumptionEvent?
    fun findAll(): List<ConsumptionEvent>
    fun findByFilamentId(filamentId: FilamentId): List<ConsumptionEvent>
}
