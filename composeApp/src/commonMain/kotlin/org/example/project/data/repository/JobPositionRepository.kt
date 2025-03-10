package org.example.project.data.repository

import org.example.project.domain.model.JobPosition
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.JobPositionApi

class JobPositionRepository(private val jobPositionApi: JobPositionApi) {

    suspend fun getAllJobPositions(): PaginatedResponse<JobPosition>? {
        return try {
            jobPositionApi.getAllJobPositions()
        } catch (e: Exception) {
            println("Error fetching jobPositions: ${e.message}")
            null
        }
    }
    
    suspend fun getJobPosition(userId: Int): JobPosition? {
        return try {
            jobPositionApi.getJobPosition(userId)
        } catch (e: Exception) {
            println("Error fetching jobPosition: ${e.message}")
            null
        }
    }

    suspend fun createJobPosition(jobPosition: JobPosition): JobPosition? {
        return try {
            jobPositionApi.createJobPosition(jobPosition)
        } catch (e: Exception) {
            println("Error creating jobPosition: ${e.message}")
            null
        }
    }

    suspend fun updateJobPosition(jobPositionId: Int, jobPosition: JobPosition): JobPosition? {
        return try {
            jobPositionApi.updateJobPosition(jobPositionId, jobPosition)
        } catch (e: Exception) {
            println("Error updating jobPosition: ${e.message}")
            null
        }
    }

    suspend fun deleteJobPosition(jobPositionId: Int): Boolean {
        return try {
            jobPositionApi.deleteJobPosition(jobPositionId)
        } catch (e: Exception) {
            println("Error deleting jobPosition: ${e.message}")
            false
        }
    }
}
