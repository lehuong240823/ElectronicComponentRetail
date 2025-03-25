package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.AccessLevelRepository
import org.example.project.data.repository.CloudinaryStorageRepository
import org.example.project.domain.model.AccessLevel

class CloudinaryViewModel(private val cloudinaryStorageRepository: CloudinaryStorageRepository) {
    private val _url = mutableStateOf<String>("")
    val url: State<String?> get() = _url

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun uploadImage(imageData: ByteArray) {
        val result = cloudinaryStorageRepository.uploadImage(imageData)
        if (result != null) {
            _url.value = result.secure_url
            println("surl: "+_url.value)
            _operationStatus.value = "Successfully"
        } else {
            _operationStatus.value = "Failed"
        }
    }
}