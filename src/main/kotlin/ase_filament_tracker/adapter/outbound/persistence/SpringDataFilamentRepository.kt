package ase_filament_tracker.adapter.outbound.persistence

import org.springframework.data.repository.CrudRepository

interface SpringDataFilamentRepository : CrudRepository<FilamentJpaEntity, String>
