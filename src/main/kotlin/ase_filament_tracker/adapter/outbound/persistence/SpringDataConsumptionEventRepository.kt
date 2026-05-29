package ase_filament_tracker.adapter.outbound.persistence

import org.springframework.data.repository.CrudRepository

interface SpringDataConsumptionEventRepository : CrudRepository<ConsumptionEventJpaEntity, String> {
    fun findAllByFilamentId(filamentId: String): List<ConsumptionEventJpaEntity>
}
