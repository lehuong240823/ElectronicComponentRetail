package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.ReviewImageRepository
import org.example.project.domain.model.ReviewImage

class ReviewImageViewModel(private val reviewImageRepository: ReviewImageRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdReviewImage = mutableStateOf<ReviewImage?>(null)
    val createdReviewImage: State<ReviewImage?> get() = _createdReviewImage
    
    private val _reviewImage = mutableStateOf<ReviewImage?>(null)
    val reviewImage: State<ReviewImage?> get() = _reviewImage
    
    private val _updatedReviewImage = mutableStateOf<ReviewImage?>(null)
    val updatedReviewImage: State<ReviewImage?> get() = _updatedReviewImage

    private val _reviewImagesList = mutableStateOf<List<ReviewImage>>(emptyList())
    val reviewImagesList: State<List<ReviewImage>> get() = _reviewImagesList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createReviewImage(reviewImage: ReviewImage) {
        val result = reviewImageRepository.createReviewImage(reviewImage)
        if (result != null) {
            _createdReviewImage.value = result
            _operationStatus.value = "ReviewImage Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create ReviewImage"
        }
    }

    suspend fun getReviewImage(reviewImageId: Int) {
        val result = reviewImageRepository.getReviewImage(reviewImageId)
        if (result != null) {
            _reviewImage.value = result
            _operationStatus.value = "ReviewImage Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get ReviewImage or ReviewImage Not Found"
        }
    }

    suspend fun updateReviewImage(reviewImageId: Int, reviewImage: ReviewImage) {
        val result = reviewImageRepository.updateReviewImage(reviewImageId, reviewImage)
        if (result != null) {
            _updatedReviewImage.value = result
            _operationStatus.value = "ReviewImage Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update ReviewImage"
        }
    }

    suspend fun deleteReviewImage(reviewImageId: Int) {
        val result = reviewImageRepository.deleteReviewImage(reviewImageId)
        if (result) {
            _operationStatus.value = "ReviewImage Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete ReviewImage"
        }
    }

    suspend fun getAllReviewImages(currentPage: Int) {
        val result = reviewImageRepository.getAllReviewImages(currentPage)
        if (result != null) {
            _reviewImagesList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "ReviewImage Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All ReviewImage"
        }
    }

    suspend fun getReviewImagesByReviewId(currentPage: Int, review: Int) {
        val result = reviewImageRepository.getReviewImagesByReviewId(currentPage, review)
        if (result != null) {
            _reviewImagesList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "ReviewImage Get All Successfully"
        } else {
            _operationStatus.value = "ReviewImage to Get All Account"
        }
    }
}