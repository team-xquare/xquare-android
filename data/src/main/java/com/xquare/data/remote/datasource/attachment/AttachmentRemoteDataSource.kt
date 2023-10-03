package com.xquare.data.remote.datasource.attachment

import com.xquare.domain.entity.attachment.FileEntity
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AttachmentRemoteDataSource {
    suspend fun uploadFile(file: File): Flow<FileEntity>
}