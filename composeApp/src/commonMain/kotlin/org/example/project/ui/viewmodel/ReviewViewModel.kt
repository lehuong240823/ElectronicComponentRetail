package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.ReviewRepository
import org.example.project.domain.model.Review

class ReviewViewModel(private val reviewRepository: ReviewRepository) {
    private val _review = mutableStateOf<Review?>(null)
    val review: State<Review?> get() = _review

    private val _reviewsList = mutableStateOf<List<Review>>(emptyList())
    val reviewsList: State<List<Review>> get() = _reviewsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createReview(review: Review) {
        val result = reviewRepository.createReview(review)
        if (result != null) {
            _operationStatus.value = "Review Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Review"
        }
    }

    suspend fun getReview(reviewId: Int) {
        val result = reviewRepository.getReview(reviewId)
        if (result != null) {
            _review.value = result
        } else {
            _operationStatus.value = "Review Not Found"
        }
    }

    suspend fun updateReview(reviewId: Int, review: Review) {
        val result = reviewRepository.updateReview(reviewId, review)
        if (result != null) {
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

    suspend fun getAllReviews() {
        val result = reviewRepository.getAllReviews()
        _reviewsList.value = result?.content ?: emptyList()
    }
}