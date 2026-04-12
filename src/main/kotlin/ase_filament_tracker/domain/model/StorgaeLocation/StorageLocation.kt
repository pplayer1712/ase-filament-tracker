package ase_filament_tracker.domain.model.StorgaeLocation

class StorageLocation(
    val id: StorageLocationId,
    val path: LocationPath,
    val description: String = ""
) {
    fun withDescription(newDescription: String): StorageLocation {
        return StorageLocation(id, path, newDescription)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StorageLocation) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = "StorageLocation(id=$id, path=$path)"
}
