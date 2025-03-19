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
import org.example.project.BASE_URL
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.ReviewImage

class ReviewImageApi {
    val endPoint = "/api/review-images"

    suspend fun getAllReviewImages(currentPage: Int): PaginatedResponse<ReviewImage> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getReviewImage(reviewImageId: Int): ReviewImage {
        return HttpClient.client.get("$BASE_URL${endPoint}/${reviewImageId}").body()
    }
    
    suspend fun createReviewImage(reviewImage: ReviewImage): ReviewImage {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(reviewImage)
        }.body()
    }

    suspend fun updateReviewImage(reviewImageId: Int, reviewImage: ReviewImage): ReviewImage {
        return HttpClient.client.put("$BASE_URL${endPoint}/${reviewImageId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(reviewImage)
        }.body()
    }

    suspend fun deleteReviewImage(reviewImageId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${reviewImageId}")
            true
        } catch (e: Exception) {
            println("Error deleting reviewImage: ${e.message}")
            false
        }
    }

    suspend fun getReviewImagesByReviewId(currentPage: Int, review: Int): PaginatedResponse<ReviewImage> {
        return HttpClient.client.get("$BASE_URL${endPoint}/review/id/${review}?size=${getPageSize()}&page=${currentPage}").body()
    }
}