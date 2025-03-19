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
import org.example.project.domain.model.JobPosition

class JobPositionApi {
    val endPoint = "/api/job-positions"

    suspend fun getAllJobPositions(currentPage: Int): PaginatedResponse<JobPosition> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getJobPosition(jobPositionId: Int): JobPosition {
        return HttpClient.client.get("$BASE_URL${endPoint}/${jobPositionId}").body()
    }
    
    suspend fun createJobPosition(jobPosition: JobPosition): JobPosition {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(jobPosition)
        }.body()
    }

    suspend fun updateJobPosition(jobPositionId: Int, jobPosition: JobPosition): JobPosition {
        return HttpClient.client.put("$BASE_URL${endPoint}/${jobPositionId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(jobPosition)
        }.body()
    }

    suspend fun deleteJobPosition(jobPositionId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${jobPositionId}")
            true
        } catch (e: Exception) {
            println("Error deleting jobPosition: ${e.message}")
            false
        }
    }
}