package ase_filament_tracker.adapter.outbound.persistence

import ase_filament_tracker.domain.model.PrintJob.MaterialUsage
import ase_filament_tracker.domain.model.PrintJob.PrintJob
import ase_filament_tracker.domain.model.PrintJob.PrintJobId
import ase_filament_tracker.domain.model.PrintJob.PrintJobStatus
import ase_filament_tracker.domain.model.filament.FilamentId
import ase_filament_tracker.domain.repository.PrintJobRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PrintJobPersistenceAdapter(
    private val springDataRepository: SpringDataPrintJobRepository
) : PrintJobRepository {

    override fun save(printJob: PrintJob): PrintJob {
        val entity = PrintJobJpaEntity(
            id = printJob.id.value.toString(),
            filamentId = printJob.filamentId.value.toString(),
            estimatedUsageInGrams = printJob.estimatedUsage.valueInGrams,
            status = printJob.status.name
        )
        springDataRepository.save(entity)
        return printJob
    }

    override fun findById(id: PrintJobId): PrintJob? {
        val opt = springDataRepository.findById(id.value.toString())
        if (opt.isEmpty) return null
        return toDomain(opt.get())
    }

    override fun findAll(): List<PrintJob> {
        return springDataRepository.findAll().map { toDomain(it) }
    }

    override fun findByFilamentId(filamentId: FilamentId): List<PrintJob> {
        return springDataRepository.findAllByFilamentId(filamentId.value.toString())
            .map { toDomain(it) }
    }

    override fun delete(id: PrintJobId) {
        springDataRepository.deleteById(id.value.toString())
    }

    private fun toDomain(entity: PrintJobJpaEntity): PrintJob {
        return PrintJob(
            id = PrintJobId(UUID.fromString(entity.id)),
            filamentId = FilamentId(UUID.fromString(entity.filamentId)),
            estimatedUsage = MaterialUsage(entity.estimatedUsageInGrams),
            status = PrintJobStatus.valueOf(entity.status)
        )
    }
}
