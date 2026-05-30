package ase_filament_tracker.adapter.outbound.persistence

import ase_filament_tracker.domain.model.filament.Color
import ase_filament_tracker.domain.model.filament.Diameter
import ase_filament_tracker.domain.model.filament.Filament
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.model.filament.Weight
import ase_filament_tracker.domain.repository.FilamentRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class FilamentPersistenceAdapter(
    private val springDataRepository: SpringDataFilamentRepository
) : FilamentRepository {

    override fun save(filament: Filament): Filament {
        val entity = FilamentJpaEntity(
            id = filament.id.value.toString(),
            colorName = filament.color.name,
            colorHex = filament.color.hexCode,
            diameterInMm = filament.diameter.valueInMm,
            remainingWeightInGrams = filament.remainingWeight.valueInGrams
        )
        springDataRepository.save(entity)
        return filament
    }

    override fun findById(id: FilamentId): Filament? {
        val entityOpt = springDataRepository.findById(id.value.toString())
        if (entityOpt.isEmpty) return null
        return toDomain(entityOpt.get())
    }

    override fun findAll(): List<Filament> {
        return springDataRepository.findAll().map { toDomain(it) }
    }

    override fun delete(id: FilamentId) {
        springDataRepository.deleteById(id.value.toString())
    }

    private fun toDomain(entity: FilamentJpaEntity): Filament {
        return Filament(
            id = FilamentId(UUID.fromString(entity.id)),
            color = Color(entity.colorName, entity.colorHex),
            diameter = Diameter(entity.diameterInMm),
            remainingWeight = Weight(entity.remainingWeightInGrams)
        )
    }
}
