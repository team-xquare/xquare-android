package com.xquare.xquare_android

import androidx.lifecycle.viewModelScope
import com.xquare.domain.entity.github.GithubOAuthEntity
import com.xquare.domain.usecase.github.FetchGithubOAuthCheckUseCase
import com.xquare.domain.usecase.github.FetchGithubOAuthUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val fetchGithubOAuthUseCase: FetchGithubOAuthUseCase,
    private val fetchGithubOAuthCheckUseCase: FetchGithubOAuthCheckUseCase,
) : BaseViewModel<MainActivityViewModel.Event>() {

    private var called = false
    fun registerGithubUser(githubOAuthEntity: GithubOAuthEntity) {
        if (!called) {
            called = true
            viewModelScope.launch(Dispatchers.IO) {
                runCatching {
                    val connected = fetchGithubOAuthCheckUseCase.execute(Unit).is_connected

                    if (!connected) {
                        fetchGithubOAuthUseCase.execute(githubOAuthEntity)
                    }
                }.onSuccess {
                    emitEvent(Event.OAuthSuccess)
                }.onFailure {
                    emitEvent(Event.OAuthFailure)
                    it.printStackTrace()
                }
            }
        }
    }

    sealed class Event {
        object OAuthSuccess : Event()
        object OAuthFailure : Event()
    }
}
