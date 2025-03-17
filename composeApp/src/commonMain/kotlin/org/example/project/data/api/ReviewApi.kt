package org.example.project.data.api

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import org.example.project.getPageSize
import org.example.project.core.HttpClient
import org.example.project.core.BASE_URL
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.Review

class ReviewApi {
    val endPoint = "/api/reviews"

    suspend fun getAllReviews(currentPage: Int): PaginatedResponse<Review> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getReview(reviewId: Int): Review {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${reviewId}").body()
    }
    
    suspend fun createReview(review: Review): Review {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(review)
        }.body()
    }

    suspend fun updateReview(reviewId: Int, review: Review): Review {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${reviewId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(review)
        }.body()
    }

    suspend fun deleteReview(reviewId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${reviewId}")
            true
        } catch (e: Exception) {
            println("Error deleting review: ${e.message}")
            false
        }
    }
}