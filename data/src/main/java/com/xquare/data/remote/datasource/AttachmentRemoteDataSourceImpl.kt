package com.xquare.data.remote.datasource

import com.xquare.data.remote.FileUploadManager
import com.xquare.domain.entity.attachment.FileEntity
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class AttachmentRemoteDataSourceImpl @Inject constructor(
    private val fileUploadManager: FileUploadManager,
) : AttachmentRemoteDataSource {
    override suspend fun uploadFile(file: File): Flow<FileEntity> =
        fileUploadManager.uploadFile(file)
}
