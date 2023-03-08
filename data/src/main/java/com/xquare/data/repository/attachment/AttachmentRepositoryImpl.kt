package com.xquare.data.repository.attachment

import com.xquare.data.remote.datasource.AttachmentRemoteDataSource
import com.xquare.domain.entity.attachment.FileEntity
import com.xquare.domain.repository.attachment.AttachmentRepository
import java.io.File
import javax.inject.Inject

class AttachmentRepositoryImpl @Inject constructor(
    private val attachmentRemoteDataSource: AttachmentRemoteDataSource
): AttachmentRepository {
    override suspend fun uploadFile(file: File): FileEntity =
        attachmentRemoteDataSource.uploadFile(file)

}