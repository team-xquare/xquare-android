package com.xquare.domain.usecase.webview

import com.xquare.domain.repository.WebViewRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchAuthorizationHeadersUseCase @Inject constructor(
    private val webViewRepository: WebViewRepository,
) : UseCase<Unit, Map<String, String>>() {

    override suspend fun execute(data: Unit): Map<String, String> =
        webViewRepository.fetchAuthorizationHeaders()
}