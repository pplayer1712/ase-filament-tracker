package ase_filament_tracker.adapter.config

import ase_filament_tracker.application.port.`in`.AddFilamentUseCase
import ase_filament_tracker.application.port.`in`.RecordConsumptionUseCase
import ase_filament_tracker.application.service.ConsumptionApplicationService
import ase_filament_tracker.application.service.FilamentApplicationService
import ase_filament_tracker.domain.repository.FilamentRepository
import ase_filament_tracker.domain.service.consumption.ConsumptionService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CleanArchitectureConfig {

    // Domain Services
    @Bean
    fun consumptionDomainService(): ConsumptionService {
        return ConsumptionService()
    }

    // Application Services
    @Bean
    fun filamentApplicationService(
        filamentRepository: FilamentRepository
    ): AddFilamentUseCase {
        return FilamentApplicationService(filamentRepository)
    }

    @Bean
    fun consumptionApplicationService(
        filamentRepository: FilamentRepository,
        consumptionDomainService: ConsumptionService
    ): RecordConsumptionUseCase {
        return ConsumptionApplicationService(filamentRepository, consumptionDomainService)
    }
}
