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
import org.example.project.domain.model.Category

class CategoryApi {
    val endPoint = "/api/categorys"

    suspend fun getAllCategorys(): PaginatedResponse<Category> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<Category>>()
    }

    suspend fun getCategory(categoryId: Int): Category {
        return Json.decodeFromString<Category>(HttpClient.client.get(urlString = getUrl("${endPoint}/$categoryId")).body())
    }
    
    suspend fun createCategory(category: Category): Category {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(category)
        }.body()
    }

    suspend fun updateCategory(categoryId: Int, category: Category): Category {
        return HttpClient.client.put(getUrl("${endPoint}/$categoryId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(category)
        }.body()
    }

    suspend fun deleteCategory(categoryId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$categoryId"))
            true
        } catch (e: Exception) {
            println("Error deleting category: ${e.message}")
            false
        }
    }
}