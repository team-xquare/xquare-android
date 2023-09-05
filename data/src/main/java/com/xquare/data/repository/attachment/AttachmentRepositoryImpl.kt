package com.xquare.data.repository.attachment

import com.xquare.data.remote.FileUploadManager
import com.xquare.domain.entity.attachment.FileEntity
import com.xquare.domain.repository.attachment.AttachmentRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class AttachmentRepositoryImpl @Inject constructor(
    private val fileUploadManager: FileUploadManager,
) : AttachmentRepository {
    override suspend fun uploadFile(file: File): Flow<FileEntity> {
        return try {
            fileUploadManager.uploadFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
