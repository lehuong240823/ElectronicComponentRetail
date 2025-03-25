package org.example.project.data.repository

import org.example.project.domain.model.AccessLevel
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.AccessLevelApi
import org.example.project.data.api.CloudinaryStorageApi
import org.example.project.data.api.CloudinaryUploadResponse

class CloudinaryStorageRepository(private val cloudinaryStorageApi: CloudinaryStorageApi) {
    suspend fun uploadImage(imageData: ByteArray): CloudinaryUploadResponse? {
        return try {
            cloudinaryStorageApi.uploadImage(imageData)
        } catch (e: Exception) {
            println("Error fetching image: ${e.message}")
            null
        }
    }
}