package com.xquare.domain.usecase.attachment

import com.xquare.domain.entity.attachment.FileEntity
import com.xquare.domain.repository.attachment.AttachmentRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(
    private val attachmentRepository: AttachmentRepository
) : UseCase<File, Flow<FileEntity>>() {
    override suspend fun execute(data: File): Flow<FileEntity> =
        attachmentRepository.uploadFile(data)
}