package com.xquare.data.remote

import com.xquare.data.local.preference.AuthPreference
import com.xquare.data.remote.api.AttachmentApi
import com.xquare.data.remote.request.attachment.FetchPreSignedUrlRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FileUploadManager(
    private val attachmentApi: AttachmentApi,
) {
    private val httpClient by lazy { OkHttpClient() }
    private val accessToken: String
        get() = runBlocking(Dispatchers.IO) { authPreference.fetchAccessToken() }

    suspend fun uploadFile(vararg files: File) {
        require(files.all { file -> file.length() < 10 * 1_000_000 })

        val request1 = files.toList().toRequest()
        val response1 = attachmentApi.fetchPreSignedUrl(request1)
        val requests = request1.requests

        // 네이밍 죄송합니다
        response1.responses.forEachIndexed { index, response2 ->
            requests[index].let { request2 ->
                val file = files[index]
                val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart(
                        name = "file",
                        filename = file.name,
                        body = file.asRequestBody(
                            request2.contentType.toMediaTypeOrNull(),
                        ),
                    ).build()
                val request = Request.Builder()
                    .url(response2.preSignedUrl)
                    .method("PUT", body)
                    .build()
                httpClient.newCall(request)
            }
        }
    }
}

enum class ContentType(val value: String) {
    JPEG("image/jpeg"), JPG("image/jpg"), JPE("image/jpe"), GIF("image/gif"), WEBP("image/webp");

    companion object {
        fun fromString(value: String): ContentType {
            return when (value) {
                "jpeg" -> JPEG
                "jpg" -> JPG
                "jpe" -> JPE
                "gif" -> GIF
                "webp" -> WEBP
                else -> throw IllegalArgumentException()
            }
        }
    }
}

fun File.toRequest(): FetchPreSignedUrlRequest.ImageFileRequest {
    val (_, contentType) = this.name.split('.')

    return FetchPreSignedUrlRequest.ImageFileRequest(
        originalFilename = this.name,
        contentType = ContentType.fromString(contentType).value,
        // todo 수정
        fileSize = 10
    )
}

fun List<File>.toRequest(): FetchPreSignedUrlRequest =
    FetchPreSignedUrlRequest(requests = this.map(File::toRequest))
