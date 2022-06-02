package com.xquare.data

import com.xquare.domain.exception.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun File.toMultipart(): MultipartBody.Part =
    MultipartBody.Part.createFormData(
        "file",
        this.name,
        this.asRequestBody("image/*".toMediaTypeOrNull())
    )

suspend fun <T> sendHttpRequest(
    httpRequest: suspend () -> T,
    onBadRequest: (message: String) -> Throwable = { BadRequestException() },
    onUnauthorized: (message: String) -> Throwable = { UnauthorizedException() },
    onForbidden: (message: String) -> Throwable = { ForbiddenException() },
    onNotFound: (message: String) -> Throwable = { NotFoundException() },
    onConflict: (message: String) -> Throwable = { ConflictException() },
    onServerError: (code: Int) -> Throwable = { ServerException() },
    onOtherHttpStatusCode: (code: Int, message: String) -> Throwable = { _, _ -> UnknownException() }
): T =
    try {
        httpRequest()
    } catch (e: HttpException) {
        val code = e.code()
        val message = e.message()
        throw when (code) {
            400 -> onBadRequest(message)
            401 -> onUnauthorized(message)
            403 -> onForbidden(message)
            404 -> onNotFound(message)
            409 -> onConflict(message)
            500, 501, 502, 503 -> onServerError(code)
            else -> onOtherHttpStatusCode(code, message)
        }
    } catch (e: UnknownHostException) {
        throw NoInternetException()
    } catch (e: SocketTimeoutException) {
        throw TimeoutException()
    } catch (e: NeedLoginException) {
        throw e
    } catch (e: Throwable) {
        throw UnknownException()
    }