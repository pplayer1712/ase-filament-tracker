package ase_filament_tracker.adapter.config

import ase_filament_tracker.application.port.`in`.AddFilamentUseCase
import ase_filament_tracker.application.port.`in`.DeleteFilamentUseCase
import ase_filament_tracker.application.port.`in`.GetFilamentUseCase
import ase_filament_tracker.application.port.`in`.MaterialProfileUseCase
import ase_filament_tracker.application.port.`in`.PrintJobUseCase
import ase_filament_tracker.application.port.`in`.RecordConsumptionUseCase
import ase_filament_tracker.application.service.ConsumptionApplicationService
import ase_filament_tracker.application.service.FilamentApplicationService
import ase_filament_tracker.application.service.FilamentQueryService
import ase_filament_tracker.application.service.MaterialProfileApplicationService
import ase_filament_tracker.application.service.PrintJobApplicationService
import ase_filament_tracker.domain.repository.ConsumptionEventRepository
import ase_filament_tracker.domain.repository.FilamentRepository
import ase_filament_tracker.domain.repository.MaterialProfileRepository
import ase_filament_tracker.domain.repository.PrintJobRepository
import ase_filament_tracker.domain.service.StatisticsService
import ase_filament_tracker.domain.service.consumption.ConsumptionService
import ase_filament_tracker.domain.service.reorder.ReorderService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CleanArchitectureConfig {

    // Domain Services
    @Bean
    fun consumptionDomainService(): ConsumptionService = ConsumptionService()

    @Bean
    fun statisticsService(): StatisticsService = StatisticsService()

    @Bean
    fun reorderService(): ReorderService = ReorderService()

    // Filament Application Services
    @Bean
    fun filamentApplicationService(filamentRepository: FilamentRepository): AddFilamentUseCase =
        FilamentApplicationService(filamentRepository)

    @Bean
    fun consumptionApplicationService(
        filamentRepository: FilamentRepository,
        consumptionDomainService: ConsumptionService,
        consumptionEventRepository: ConsumptionEventRepository
    ): RecordConsumptionUseCase =
        ConsumptionApplicationService(filamentRepository, consumptionDomainService, consumptionEventRepository)

    @Bean
    fun filamentQueryService(filamentRepository: FilamentRepository): FilamentQueryService =
        FilamentQueryService(filamentRepository)

    @Bean
    fun getFilamentUseCase(filamentQueryService: FilamentQueryService): GetFilamentUseCase =
        filamentQueryService

    @Bean
    fun deleteFilamentUseCase(filamentQueryService: FilamentQueryService): DeleteFilamentUseCase =
        filamentQueryService

    // MaterialProfile Application Services
    @Bean
    fun materialProfileUseCase(materialProfileRepository: MaterialProfileRepository): MaterialProfileUseCase =
        MaterialProfileApplicationService(materialProfileRepository)

    // PrintJob Application Services
    @Bean
    fun printJobUseCase(
        printJobRepository: PrintJobRepository,
        filamentRepository: FilamentRepository,
        consumptionDomainService: ConsumptionService
    ): PrintJobUseCase =
        PrintJobApplicationService(printJobRepository, filamentRepository, consumptionDomainService)
}
