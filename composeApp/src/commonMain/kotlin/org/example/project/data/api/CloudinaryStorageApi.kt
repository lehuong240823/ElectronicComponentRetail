package org.example.project.data.api

import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import org.example.project.*
import org.example.project.core.HttpClient

//https://res.cloudinary.com/{cloud_name}/image/upload/{public_id}.{format}
class CloudinaryStorageApi {
    suspend fun uploadImage(imageData: ByteArray): CloudinaryUploadResponse {
        val endPoint = "https://api.cloudinary.com/v1_1/$CLOUDINARY_CLOUD_NAME/image/upload?api_key=$CLOUDINARY_API_KEY&upload_preset=unsigned-preset"
        return HttpClient.client.submitFormWithBinaryData(
            url = endPoint,
            formData = formData {
                append("file", imageData, Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")  // ✅ Set Content-Type
                    append(HttpHeaders.ContentDisposition, "form-data; name=\"file\"; filename=\"random\"")
                })

                //append("name", "random")
                //append("upload_preset", "unsigned-preset")
                //append("api_key", "$CLOUDINARY_API_KEY")
                //append("public_id", "my_custom_name")
            }
        ).body()
    }
}

@Serializable
data class CloudinaryUploadResponse(
    val asset_id: String,
    val public_id: String,
    val version: Long,
    val version_id: String,
    val signature: String,
    val width: Int,
    val height: Int,
    val format: String,
    val resource_type: String,
    val created_at: String,
    val tags: List<String> = emptyList(),
    val bytes: Int,
    val type: String,
    val etag: String,
    val placeholder: Boolean,
    val url: String,
    val secure_url: String,
    val folder: String? = null,
    val original_filename: String
)

