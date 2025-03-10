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
import org.example.project.domain.model.JobPosition

class JobPositionApi {
    val endPoint = "/api/jobPositions"

    suspend fun getAllJobPositions(): PaginatedResponse<JobPosition> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<JobPosition>>()
    }

    suspend fun getJobPosition(jobPositionId: Int): JobPosition {
        return Json.decodeFromString<JobPosition>(HttpClient.client.get(urlString = getUrl("${endPoint}/$jobPositionId")).body())
    }
    
    suspend fun createJobPosition(jobPosition: JobPosition): JobPosition {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(jobPosition)
        }.body()
    }

    suspend fun updateJobPosition(jobPositionId: Int, jobPosition: JobPosition): JobPosition {
        return HttpClient.client.put(getUrl("${endPoint}/$jobPositionId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(jobPosition)
        }.body()
    }

    suspend fun deleteJobPosition(jobPositionId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$jobPositionId"))
            true
        } catch (e: Exception) {
            println("Error deleting jobPosition: ${e.message}")
            false
        }
    }
}