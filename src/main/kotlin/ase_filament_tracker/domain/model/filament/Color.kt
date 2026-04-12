package ase_filament_tracker.domain.model.filament

data class Color(
    val name: String,
    val hexCode: String? = null
) {
    init {
        require(name.isNotBlank()) { "Farbname darf nicht leer sein" }
        hexCode?.let {
            require(it.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})\$"))) {
                "Ungültiger Hexcode: $it"
            }
        }
    }
}
