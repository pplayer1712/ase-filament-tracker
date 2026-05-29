package ase_filament_tracker.adapter.outbound.persistence

import ase_filament_tracker.domain.model.consumption.ConsumptionEvent
import ase_filament_tracker.domain.model.consumption.ConsumptionEventId
import ase_filament_tracker.domain.model.consumption.ConsumptionType
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.model.filament.Weight
import ase_filament_tracker.domain.repository.ConsumptionEventRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ConsumptionEventPersistenceAdapter(
    private val springDataRepository: SpringDataConsumptionEventRepository
) : ConsumptionEventRepository {

    override fun save(event: ConsumptionEvent): ConsumptionEvent {
        val entity = ConsumptionEventJpaEntity(
            id = event.id.value.toString(),
            filamentId = event.filamentId.value.toString(),
            consumedWeightInGrams = event.consumedWeight.valueInGrams,
            consumptionType = event.type.name,
            occurredOn = event.occurredOn
        )
        springDataRepository.save(entity)
        return event
    }

    override fun findById(id: ConsumptionEventId): ConsumptionEvent? {
        val opt = springDataRepository.findById(id.value.toString())
        if (opt.isEmpty) return null
        return toDomain(opt.get())
    }

    override fun findAll(): List<ConsumptionEvent> {
        return springDataRepository.findAll().map { toDomain(it) }
    }

    override fun findByFilamentId(filamentId: FilamentId): List<ConsumptionEvent> {
        return springDataRepository.findAllByFilamentId(filamentId.value.toString())
            .map { toDomain(it) }
    }

    private fun toDomain(entity: ConsumptionEventJpaEntity): ConsumptionEvent {
        return ConsumptionEvent(
            id = ConsumptionEventId(UUID.fromString(entity.id)),
            filamentId = FilamentId(UUID.fromString(entity.filamentId)),
            consumedWeight = Weight(entity.consumedWeightInGrams),
            type = ConsumptionType.valueOf(entity.consumptionType),
            occurredOn = entity.occurredOn
        )
    }
}
