package com.xquare.data.local.preference

import android.content.SharedPreferences
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import javax.inject.Inject

class AuthPreferenceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AuthPreference {

    override suspend fun saveAccessToken(accessToken: String) =
        saveStringPreference(ACCESS_TOKEN, accessToken)

    override suspend fun fetchAccessToken(): String =
        fetchStringPreference(ACCESS_TOKEN)

    override suspend fun clearAccessToken() =
        clearPreference(ACCESS_TOKEN)

    override suspend fun saveRefreshToken(refreshToken: String) =
        saveStringPreference(REFRESH_TOKEN, refreshToken)

    override suspend fun fetchRefreshToken(): String =
        fetchStringPreference(REFRESH_TOKEN)

    override suspend fun clearRefreshToken() =
        clearPreference(REFRESH_TOKEN)

    override suspend fun saveAccessTokenExpireAt(accessTokenExpireAt: LocalDateTime) =
        saveLongPreference(ACCESS_TOKEN_EXPIRED_AT, accessTokenExpireAt.atZone(ZoneId.systemDefault()).toEpochSecond())

    override suspend fun fetchAccessTokenExpireAt(): LocalDateTime =
        Instant.ofEpochSecond(fetchLongPreference(ACCESS_TOKEN_EXPIRED_AT)).atZone(ZoneId.systemDefault()).toLocalDateTime()

    override suspend fun clearAccessTokenExpireAt() =
        clearPreference(ACCESS_TOKEN_EXPIRED_AT)

    override suspend fun saveRefreshTokenExpireAt(refreshTokenExpireAt: LocalDateTime) =
        saveLongPreference(REFRESH_TOKEN_EXPIRED_AT, refreshTokenExpireAt.atZone(ZoneId.systemDefault()).toEpochSecond())

    override suspend fun fetchRefreshTokenExpireAt(): LocalDateTime =
        Instant.ofEpochSecond(fetchLongPreference(REFRESH_TOKEN_EXPIRED_AT)).atZone(ZoneId.systemDefault()).toLocalDateTime()

    override suspend fun clearRefreshTokenExpireAt() =
        clearPreference(REFRESH_TOKEN_EXPIRED_AT)

    override suspend fun fetchRole(): String =
        fetchStringPreference(ROLE)

    override suspend fun saveRole(role: String) =
        saveStringPreference(ROLE, role)

    override suspend fun clearRole() =
        clearPreference(ROLE)

    override suspend fun saveUserId(userId: String) =
        saveStringPreference(USER_ID, userId)

    override suspend fun fetchUserId(): String =
        fetchStringPreference(USER_ID)

    override suspend fun clearUserId() =
        clearPreference(USER_ID)

    private fun fetchStringPreference(key: String): String =
        sharedPreferences.getString(key, null) ?: ""

    private fun saveStringPreference(key: String, value: String) =
        editPreference { it.putString(key, value) }

    private fun fetchLongPreference(key: String): Long =
        sharedPreferences.getLong(key, 0)

    private fun saveLongPreference(key: String, value: Long) =
        editPreference { it.putLong(key, value) }

    private fun clearPreference(key: String) =
        editPreference { it.remove(key) }

    private fun editPreference(edit: (SharedPreferences.Editor) -> Unit) =
        sharedPreferences.edit().let {
            edit(it)
            it.apply()
        }

    companion object Key {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val ACCESS_TOKEN_EXPIRED_AT = "ACCESS_TOKEN_EXPIRED_AT"
        const val REFRESH_TOKEN_EXPIRED_AT = "REFRESH_TOKEN_EXPIRED_AT"
        const val USER_ID = "USER_ID"
        const val ROLE = "ROLE"
    }
}