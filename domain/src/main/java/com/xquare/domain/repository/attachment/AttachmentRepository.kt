package com.xquare.domain.repository.attachment

import com.xquare.domain.entity.attachment.FileEntity
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AttachmentRepository {
    suspend fun uploadFile(file: File): Flow<FileEntity>
}