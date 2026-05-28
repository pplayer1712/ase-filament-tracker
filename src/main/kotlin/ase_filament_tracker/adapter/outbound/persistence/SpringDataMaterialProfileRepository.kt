package ase_filament_tracker.adapter.outbound.persistence

import org.springframework.data.repository.CrudRepository

interface SpringDataMaterialProfileRepository : CrudRepository<MaterialProfileJpaEntity, String>
