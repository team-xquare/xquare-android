package com.xquare.data.remote.datasource.attachment

import android.util.Log
import com.xquare.data.remote.api.AttachmentApi
import com.xquare.data.remote.response.attachment.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.attachment.FileEntity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class AttachmentRemoteDataSourceImpl @Inject constructor(
    private val attachmentApi: AttachmentApi
): AttachmentRemoteDataSource {
    override suspend fun uploadFile(file: File): FileEntity =
        sendHttpRequest(
            httpRequest = {
                val body = getImageMultipart("files", file)
                Log.d("TAG", "body: ${body.headers}")
                attachmentApi.uploadFile(body).toEntity()
            }
        )
    private fun getImageMultipart(key: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody("multipart/form-data".toMediaType())
        )
    }
}
