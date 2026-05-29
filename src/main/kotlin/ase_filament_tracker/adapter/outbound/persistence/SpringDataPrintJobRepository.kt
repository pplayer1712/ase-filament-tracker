package ase_filament_tracker.adapter.outbound.persistence

import org.springframework.data.repository.CrudRepository

interface SpringDataPrintJobRepository : CrudRepository<PrintJobJpaEntity, String> {
    fun findAllByFilamentId(filamentId: String): List<PrintJobJpaEntity>
}
