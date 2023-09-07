package com.xquare.data.remote.datasource.attachment

import com.xquare.domain.entity.attachment.FileEntity
import java.io.File

interface AttachmentRemoteDataSource {
    suspend fun uploadFile(file: File): FileEntity
}