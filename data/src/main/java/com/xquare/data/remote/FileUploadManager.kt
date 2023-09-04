package com.xquare.data.remote

import android.webkit.MimeTypeMap
import com.xquare.data.remote.ContentType.Companion.mediaType
import com.xquare.data.remote.api.AttachmentApi
import com.xquare.data.remote.request.attachment.FetchPreSignedUrlRequest
import com.xquare.domain.entity.attachment.FileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FileUploadManager(
    private val attachmentApi: AttachmentApi,
    private val httpLoggingInterceptor: Interceptor,
) {
    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    suspend fun uploadFile(vararg files: File): FileEntity {
        return try {
            require(files.all { file -> file.length() < 10 * 1_000_000 })

            val request1 = files.toList().toRequest()

            val response =
                attachmentApi.fetchPreSignedUrl(request1).responses.onEachIndexed { index, response ->
                    request1.requests[index].let { request ->
                        val file = files[index]
                        val type = file.mediaType
                        val newRequest = Request.Builder()
                            .url(response.preSignedUrl)
                            .put(file.asRequestBody(type))
                            .build()
                        CoroutineScope(Dispatchers.IO).launch {
                            httpClient.newCall(newRequest).execute()
                        }
                    }
                }

            FileEntity(fileUrls = response.map { it.url })
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}

enum class ContentType(val value: String) {
    JPEG("image/jpeg"),
    JPG("image/jpg"),
    JPE("image/jpe"),
    GIF("image/gif"),
    WEBP("image/webp"),
    PNG("image/png"),
    HEIC("image/heic");

    companion object {
        val File.mediaType: MediaType
            get() {
                val type = fromString(MimeTypeMap.getFileExtensionFromUrl(this.path)).value
                return type.toMediaType()
            }

        fun fromString(value: String): ContentType = when (value) {
            "jpeg" -> JPEG
            "jpg" -> JPG
            "jpe" -> JPE
            "gif" -> GIF
            "webp" -> WEBP
            "png" -> PNG
            "heic" -> HEIC
            else -> throw IllegalArgumentException()
        }
    }
}

fun File.toRequest(): FetchPreSignedUrlRequest.ImageFileRequest {
    return FetchPreSignedUrlRequest.ImageFileRequest(
        originalFilename = this.name,
        contentType = ContentType.fromString(MimeTypeMap.getFileExtensionFromUrl(this.path)).value,
        fileSize = this.length(),
    )
}

fun List<File>.toRequest(): FetchPreSignedUrlRequest =
    FetchPreSignedUrlRequest(requests = this.map(File::toRequest))
