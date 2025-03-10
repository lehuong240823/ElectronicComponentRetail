package org.example.project.data.api

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.example.project.core.HttpClient
import org.example.project.core.getUrl
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.ReviewImage

class ReviewImageApi {
    val endPoint = "/api/reviewImages"

    suspend fun getAllReviewImages(): PaginatedResponse<ReviewImage> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<ReviewImage>>()
    }

    suspend fun getReviewImage(reviewImageId: Int): ReviewImage {
        return Json.decodeFromString<ReviewImage>(HttpClient.client.get(urlString = getUrl("${endPoint}/$reviewImageId")).body())
    }
    
    suspend fun createReviewImage(reviewImage: ReviewImage): ReviewImage {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(reviewImage)
        }.body()
    }

    suspend fun updateReviewImage(reviewImageId: Int, reviewImage: ReviewImage): ReviewImage {
        return HttpClient.client.put(getUrl("${endPoint}/$reviewImageId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(reviewImage)
        }.body()
    }

    suspend fun deleteReviewImage(reviewImageId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$reviewImageId"))
            true
        } catch (e: Exception) {
            println("Error deleting reviewImage: ${e.message}")
            false
        }
    }
}