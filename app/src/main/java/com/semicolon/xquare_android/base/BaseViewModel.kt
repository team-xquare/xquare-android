package com.semicolon.xquare_android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.semicolon.domain.exception.NeedLoginException
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {

    private val _eventFlow = MutableEventFlow<T>()
    val eventFlow = _eventFlow.asEventFlow()

    protected suspend fun emitEvent(event: T) {
        _eventFlow.emit(event)
    }

    protected fun <V> execute(
        job: suspend () -> V,
        onSuccess: suspend (value: V) -> Unit,
        onFailure: suspend (t: Throwable) -> Unit
    ) = viewModelScope.launch {
        kotlin.runCatching { job() }
            .onSuccess { onSuccess(it) }
            .onFailure {
                if (it is NeedLoginException) throw it
                onFailure(it)
            }
    }
}