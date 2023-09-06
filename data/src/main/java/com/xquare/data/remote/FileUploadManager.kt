package com.xquare.data.remote

import android.webkit.MimeTypeMap
import com.xquare.data.remote.ContentType.Companion.mediaType
import com.xquare.data.remote.api.AttachmentApi
import com.xquare.data.remote.request.attachment.FetchPreSignedUrlRequest
import com.xquare.domain.entity.attachment.FileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    suspend fun uploadFile(vararg files: File): Flow<FileEntity> {
        return try {
            flow {
                require(files.all { file -> file.length() < 10 * 1_000_000 })

                val request = files.toList().toRequest()
                val response =
                    attachmentApi.fetchPreSignedUrl(request).responses.onEachIndexed { index, response ->
                        files[index].uploadOn(response.preSignedUrl)
                    }

                emit(FileEntity(response.map { it.url }))
            }
        } catch (e: Exception) {
            error("Failed to upload file(s).")
        }
    }

    private fun File.uploadOn(url: String) {
        val newRequest = Request.Builder()
            .url(url)
            .put(this.asRequestBody(this.mediaType))
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            httpClient.newCall(newRequest).execute()
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
