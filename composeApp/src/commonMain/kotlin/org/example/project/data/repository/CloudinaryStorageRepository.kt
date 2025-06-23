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

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val message: String?, val responseBody: String? = null) : ApiResult<Nothing>()
    object NetworkError : ApiResult<Nothing>()
    object UnknownError : ApiResult<Nothing>()
}
