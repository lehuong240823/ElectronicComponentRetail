package org.example.project.data.repository

import org.example.project.domain.model.ReviewImage
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.ReviewImageApi

class ReviewImageRepository(private val reviewImageApi: ReviewImageApi) {

    suspend fun getAllReviewImages(): PaginatedResponse<ReviewImage>? {
        return try {
            reviewImageApi.getAllReviewImages()
        } catch (e: Exception) {
            println("Error fetching reviewImages: ${e.message}")
            null
        }
    }
    
    suspend fun getReviewImage(userId: Int): ReviewImage? {
        return try {
            reviewImageApi.getReviewImage(userId)
        } catch (e: Exception) {
            println("Error fetching reviewImage: ${e.message}")
            null
        }
    }

    suspend fun createReviewImage(reviewImage: ReviewImage): ReviewImage? {
        return try {
            reviewImageApi.createReviewImage(reviewImage)
        } catch (e: Exception) {
            println("Error creating reviewImage: ${e.message}")
            null
        }
    }

    suspend fun updateReviewImage(reviewImageId: Int, reviewImage: ReviewImage): ReviewImage? {
        return try {
            reviewImageApi.updateReviewImage(reviewImageId, reviewImage)
        } catch (e: Exception) {
            println("Error updating reviewImage: ${e.message}")
            null
        }
    }

    suspend fun deleteReviewImage(reviewImageId: Int): Boolean {
        return try {
            reviewImageApi.deleteReviewImage(reviewImageId)
        } catch (e: Exception) {
            println("Error deleting reviewImage: ${e.message}")
            false
        }
    }
}
