package org.example.project

import java.io.File
import kotlin.text.trimIndent

const val Entity = "Transaction"
 var entity = Entity.lowercase()

val endPt = "\\\$endPt" 
val errorMessage: String = "\\\${e.message}"
fun main() {

    genApi()
    genRepository()
}
fun genApi() {
    val template = """
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
import org.example.project.domain.model.${Entity}

class ${Entity}Api {
    val endPoint = "/api/${entity}s"

    suspend fun getAll${Entity}s(): List<${Entity}> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<List<${Entity}>>()
    }

    suspend fun get${Entity}(${entity}Id: Int): ${Entity} {
        return Json.decodeFromString<${Entity}>(HttpClient.client.get(urlString = getUrl("$endPt/${'$'}${entity}Id")).body())
    }
    
    suspend fun create${Entity}(${entity}: ${Entity}): ${Entity} {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(${entity})
        }.body()
    }

    suspend fun update${Entity}(${entity}Id: Int, ${entity}: ${Entity}): ${Entity} {
        return HttpClient.client.put(getUrl("$endPt/${'$'}${entity}Id")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(${entity})
        }.body()
    }

    suspend fun delete${Entity}(${entity}Id: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("$endPt/${'$'}${entity}Id"))
            true
        } catch (e: Exception) {
            println("Error deleting ${entity}: errorMessage")
            false
        }
    }
}
    """.trimIndent()

    val file = File("src/commonMain/kotlin/org/example/project/data/api/$Entity" + "Api.kt")
    file.writeText(template)
    println("File generated at: ${file.absolutePath}")
}
fun genRepository () {
    val template = """
package org.example.project.data.repository

import org.example.project.data.api.${Entity}Api
import org.example.project.domain.model.${Entity}

class ${Entity}Repository(private val ${entity}Api: ${Entity}Api) {

    suspend fun get${Entity}(userId: Int): ${Entity}? {
        return try {
            ${entity}Api.get${Entity}(userId)
        } catch (e: Exception) {
            println("Error fetching ${entity}: $errorMessage")
            null
        }
    }

    suspend fun getAll${Entity}s(): List<${Entity}>? {
        return try {
            ${entity}Api.getAll${Entity}s()
        } catch (e: Exception) {
            println("Error fetching ${entity}s: $errorMessage")
            null
        }
    }

    suspend fun create${Entity}(${entity}: ${Entity}): ${Entity}? {
        return try {
            ${entity}Api.create${Entity}(${entity})
        } catch (e: Exception) {
            println("Error creating ${entity}: $errorMessage")
            null
        }
    }

    suspend fun update${Entity}(${entity}Id: Int, ${entity}: ${Entity}): ${Entity}? {
        return try {
            ${entity}Api.update${Entity}(${entity}Id, ${entity})
        } catch (e: Exception) {
            println("Error updating ${entity}: $errorMessage")
            null
        }
    }

    suspend fun delete${Entity}(${entity}Id: Int): Boolean {
        return try {
            ${entity}Api.delete${Entity}(${entity}Id)
        } catch (e: Exception) {
            println("Error deleting ${entity}: $errorMessage")
            false
        }
    }
}

    """.trimIndent()

    val file = File("src/commonMain/kotlin/org/example/project/data/repository/$Entity" + "Repository.kt")
    file.writeText(template)
    println("File generated at: ${file.absolutePath}")
}
