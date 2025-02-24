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
import org.example.project.domain.model.Review

class ReviewApi {
    val endPoint = "/api/reviews"

    suspend fun createReview(review: Review): Review {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(review)
        }.body()
    }

    suspend fun getAllReviews(): List<Review> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<List<Review>>()
    }

    suspend fun getReview(reviewId: Int): Review {
        return Json.decodeFromString<Review>(HttpClient.client.get(urlString = getUrl("\$endPt/$reviewId")).body())
    }

    suspend fun updateReview(reviewId: Int, review: Review): Review {
        return HttpClient.client.put(getUrl("\$endPt/$reviewId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(review)
        }.body()
    }

    suspend fun deleteReview(reviewId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("\$endPt/$reviewId"))
            true
        } catch (e: Exception) {
            println("Error deleting review: errorMessage")
            false
        }
    }
}