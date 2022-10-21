package com.xquare.domain.repository

interface WebViewRepository {

    suspend fun fetchAuthorizationHeaders(): Map<String, String>

    suspend fun refreshAuthorizationHeaders(): Map<String, String>
}