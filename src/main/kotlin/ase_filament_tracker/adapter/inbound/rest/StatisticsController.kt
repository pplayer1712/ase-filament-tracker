package ase_filament_tracker.adapter.inbound.rest

import ase_filament_tracker.application.port.`in`.GetFilamentUseCase
import ase_filament_tracker.domain.model.filament.ReorderThreshold
import ase_filament_tracker.domain.service.StatisticsService
import ase_filament_tracker.domain.service.reorder.ReorderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/statistics")
class StatisticsController(
    private val getFilamentUseCase: GetFilamentUseCase,
    private val statisticsService: StatisticsService,
    private val reorderService: ReorderService
) {

    /**
     * Returns all filaments that are below their reorder threshold.
     * Threshold is provided as a query parameter (in grams), default 100g.
     */
    @GetMapping("/reorder-suggestions")
    fun getReorderSuggestions(
        @RequestParam(defaultValue = "100.0") thresholdInGrams: Double
    ): ResponseEntity<List<ReorderSuggestionResponse>> {
        val threshold = ReorderThreshold(thresholdInGrams)
        val suggestions = getFilamentUseCase.getAllFilaments()
            .filter { reorderService.isReorderRecommended(it, threshold) }
            .map { ReorderSuggestionResponse(it.id.value.toString(), it.color.name, it.remainingWeight.valueInGrams, thresholdInGrams) }
        return ResponseEntity.ok(suggestions)
    }

    /**
     * Returns remaining weight for all filaments as a simple stock overview.
     */
    @GetMapping("/stock-overview")
    fun getStockOverview(): ResponseEntity<List<StockOverviewResponse>> {
        val overview = getFilamentUseCase.getAllFilaments()
            .map { StockOverviewResponse(it.id.value.toString(), it.color.name, it.color.hexCode, it.remainingWeight.valueInGrams) }
        return ResponseEntity.ok(overview)
    }
}

data class ReorderSuggestionResponse(
    val filamentId: String,
    val colorName: String,
    val remainingWeightInGrams: Double,
    val thresholdInGrams: Double
)

data class StockOverviewResponse(
    val filamentId: String,
    val colorName: String,
    val colorHex: String?,
    val remainingWeightInGrams: Double
)
