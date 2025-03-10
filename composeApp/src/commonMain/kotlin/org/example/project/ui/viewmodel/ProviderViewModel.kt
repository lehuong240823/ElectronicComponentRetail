package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.ProviderRepository
import org.example.project.domain.model.Provider

class ProviderViewModel(private val providerRepository: ProviderRepository) {
    private val _provider = mutableStateOf<Provider?>(null)
    val provider: State<Provider?> get() = _provider

    private val _providersList = mutableStateOf<List<Provider>>(emptyList())
    val providersList: State<List<Provider>> get() = _providersList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createProvider(provider: Provider) {
        val result = providerRepository.createProvider(provider)
        if (result != null) {
            _operationStatus.value = "Provider Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Provider"
        }
    }

    suspend fun getProvider(providerId: Int) {
        val result = providerRepository.getProvider(providerId)
        if (result != null) {
            _provider.value = result
        } else {
            _operationStatus.value = "Provider Not Found"
        }
    }

    suspend fun updateProvider(providerId: Int, provider: Provider) {
        val result = providerRepository.updateProvider(providerId, provider)
        if (result != null) {
            _operationStatus.value = "Provider Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update Provider"
        }
    }

    suspend fun deleteProvider(providerId: Int) {
        val result = providerRepository.deleteProvider(providerId)
        if (result) {
            _operationStatus.value = "Provider Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete Provider"
        }
    }

    suspend fun getAllProviders() {
        val result = providerRepository.getAllProviders()
        _providersList.value = result?.content ?: emptyList()
    }
}