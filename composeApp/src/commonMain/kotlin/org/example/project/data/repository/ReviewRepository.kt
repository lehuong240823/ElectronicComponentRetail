package org.example.project.data.repository

import org.example.project.data.api.ReviewApi
import org.example.project.domain.model.Review

class ReviewRepository(private val reviewApi: ReviewApi) {

    suspend fun getReview(userId: Int): Review? {
        return try {
            reviewApi.getReview(userId)
        } catch (e: Exception) {
            println("Error fetching review: \${e.message}")
            null
        }
    }

    suspend fun getAllReviews(): List<Review>? {
        return try {
            reviewApi.getAllReviews()
        } catch (e: Exception) {
            println("Error fetching reviews: \${e.message}")
            null
        }
    }

    suspend fun createReview(review: Review): Review? {
        return try {
            reviewApi.createReview(review)
        } catch (e: Exception) {
            println("Error creating review: \${e.message}")
            null
        }
    }

    suspend fun updateReview(reviewId: Int, review: Review): Review? {
        return try {
            reviewApi.updateReview(reviewId, review)
        } catch (e: Exception) {
            println("Error updating review: \${e.message}")
            null
        }
    }

    suspend fun deleteReview(reviewId: Int): Boolean {
        return try {
            reviewApi.deleteReview(reviewId)
        } catch (e: Exception) {
            println("Error deleting review: \${e.message}")
            false
        }
    }
}
