package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.JobPositionRepository
import org.example.project.domain.model.JobPosition

class JobPositionViewModel(private val jobPositionRepository: JobPositionRepository) {
    private val _jobPosition = mutableStateOf<JobPosition?>(null)
    val jobPosition: State<JobPosition?> get() = _jobPosition

    private val _jobPositionsList = mutableStateOf<List<JobPosition>>(emptyList())
    val jobPositionsList: State<List<JobPosition>> get() = _jobPositionsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createJobPosition(jobPosition: JobPosition) {
        val result = jobPositionRepository.createJobPosition(jobPosition)
        if (result != null) {
            _operationStatus.value = "JobPosition Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create JobPosition"
        }
    }

    suspend fun getJobPosition(jobPositionId: Int) {
        val result = jobPositionRepository.getJobPosition(jobPositionId)
        if (result != null) {
            _jobPosition.value = result
        } else {
            _operationStatus.value = "JobPosition Not Found"
        }
    }

    suspend fun updateJobPosition(jobPositionId: Int, jobPosition: JobPosition) {
        val result = jobPositionRepository.updateJobPosition(jobPositionId, jobPosition)
        if (result != null) {
            _operationStatus.value = "JobPosition Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update JobPosition"
        }
    }

    suspend fun deleteJobPosition(jobPositionId: Int) {
        val result = jobPositionRepository.deleteJobPosition(jobPositionId)
        if (result) {
            _operationStatus.value = "JobPosition Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete JobPosition"
        }
    }

    suspend fun getAllJobPositions() {
        val result = jobPositionRepository.getAllJobPositions()
        _jobPositionsList.value = result?.content ?: emptyList()
    }
}