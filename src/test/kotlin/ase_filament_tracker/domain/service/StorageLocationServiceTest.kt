package ase_filament_tracker.domain.service

import ase_filament_tracker.domain.model.StorageLocation.DuplicateStorageLocationException
import ase_filament_tracker.domain.model.StorageLocation.LocationPath
import ase_filament_tracker.domain.repository.StorageLocationRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class StorageLocationServiceTest {

    @Test
    fun `should create new StorageLocation when path is unique`() {
        val repositoryMock = mockk<StorageLocationRepository>()
        val service = StorageLocationService(repositoryMock)
        val path = LocationPath("Regal 1 / Box A")

        every { repositoryMock.existsByPath(path) } returns false

        val result = service.createStorageLocation(path, "Test Description")

        assertNotNull(result)
        assertEquals(path, result.path)
        
        verify(exactly = 1) { repositoryMock.existsByPath(path) }
    }

    @Test
    fun `should throw exception when path already exists`() {
        val repositoryMock = mockk<StorageLocationRepository>()
        val service = StorageLocationService(repositoryMock)
        val path = LocationPath("Regal 1 / Box A")

        every { repositoryMock.existsByPath(path) } returns true

        assertThrows(DuplicateStorageLocationException::class.java) {
            service.createStorageLocation(path)
        }
        
        verify(exactly = 1) { repositoryMock.existsByPath(path) }
    }
}
