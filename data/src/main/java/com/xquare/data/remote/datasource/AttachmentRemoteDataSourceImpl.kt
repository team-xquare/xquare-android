package com.xquare.data.remote.datasource

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.xquare.data.remote.api.AttachmentApi
import com.xquare.data.remote.request.FileRequest
import com.xquare.data.remote.response.attachment.toEntity
import com.xquare.data.sendHttpRequest
import com.xquare.domain.entity.attachment.FileEntity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.get
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
