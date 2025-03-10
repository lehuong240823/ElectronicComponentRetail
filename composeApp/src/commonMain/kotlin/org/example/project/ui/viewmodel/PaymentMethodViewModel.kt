package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.PaymentMethodRepository
import org.example.project.domain.model.PaymentMethod

class PaymentMethodViewModel(private val paymentMethodRepository: PaymentMethodRepository) {
    private val _paymentMethod = mutableStateOf<PaymentMethod?>(null)
    val paymentMethod: State<PaymentMethod?> get() = _paymentMethod

    private val _paymentMethodsList = mutableStateOf<List<PaymentMethod>>(emptyList())
    val paymentMethodsList: State<List<PaymentMethod>> get() = _paymentMethodsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createPaymentMethod(paymentMethod: PaymentMethod) {
        val result = paymentMethodRepository.createPaymentMethod(paymentMethod)
        if (result != null) {
            _operationStatus.value = "PaymentMethod Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create PaymentMethod"
        }
    }

    suspend fun getPaymentMethod(paymentMethodId: Int) {
        val result = paymentMethodRepository.getPaymentMethod(paymentMethodId)
        if (result != null) {
            _paymentMethod.value = result
        } else {
            _operationStatus.value = "PaymentMethod Not Found"
        }
    }

    suspend fun updatePaymentMethod(paymentMethodId: Int, paymentMethod: PaymentMethod) {
        val result = paymentMethodRepository.updatePaymentMethod(paymentMethodId, paymentMethod)
        if (result != null) {
            _operationStatus.value = "PaymentMethod Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update PaymentMethod"
        }
    }

    suspend fun deletePaymentMethod(paymentMethodId: Int) {
        val result = paymentMethodRepository.deletePaymentMethod(paymentMethodId)
        if (result) {
            _operationStatus.value = "PaymentMethod Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete PaymentMethod"
        }
    }

    suspend fun getAllPaymentMethods() {
        val result = paymentMethodRepository.getAllPaymentMethods()
        _paymentMethodsList.value = result?.content ?: emptyList()
    }
}