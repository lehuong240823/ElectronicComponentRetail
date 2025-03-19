package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.ReviewRepository
import org.example.project.domain.model.Review

class ReviewViewModel(private val reviewRepository: ReviewRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdReview = mutableStateOf<Review?>(null)
    val createdReview: State<Review?> get() = _createdReview
    
    private val _review = mutableStateOf<Review?>(null)
    val review: State<Review?> get() = _review
    
    private val _updatedReview = mutableStateOf<Review?>(null)
    val updatedReview: State<Review?> get() = _updatedReview

    private val _reviewsList = mutableStateOf<List<Review>>(emptyList())
    val reviewsList: State<List<Review>> get() = _reviewsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createReview(review: Review) {
        val result = reviewRepository.createReview(review)
        if (result != null) {
            _createdReview.value = result
            _operationStatus.value = "Review Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Review"
        }
    }

    suspend fun getReview(reviewId: Int) {
        val result = reviewRepository.getReview(reviewId)
        if (result != null) {
            _review.value = result
            _operationStatus.value = "Review Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get Review or Review Not Found"
        }
    }

    suspend fun updateReview(reviewId: Int, review: Review) {
        val result = reviewRepository.updateReview(reviewId, review)
        if (result != null) {
            _updatedReview.value = result
            _operationStatus.value = "Review Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update Review"
        }
    }

    suspend fun deleteReview(reviewId: Int) {
        val result = reviewRepository.deleteReview(reviewId)
        if (result) {
            _operationStatus.value = "Review Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete Review"
        }
    }

    suspend fun getAllReviews(currentPage: Int) {
        val result = reviewRepository.getAllReviews(currentPage)
        if (result != null) {
            _reviewsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Review Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All Review"
        }
    }

    suspend fun getReviewsByOrderItemId(currentPage: Int, orderItem: Int) {
        val result = reviewRepository.getReviewsByOrderItemId(currentPage, orderItem)
        if (result != null) {
            _reviewsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Review Get All Successfully"
        } else {
            _operationStatus.value = "Review to Get All Account"
        }
    }
}