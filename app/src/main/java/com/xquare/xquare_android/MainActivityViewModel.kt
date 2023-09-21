package com.xquare.xquare_android

import com.xquare.domain.entity.github.GithubOAuthEntity
import com.xquare.domain.usecase.github.FetchGithubOAuthCheckUseCase
import com.xquare.domain.usecase.github.FetchGithubOAuthUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val fetchGithubOAuthUseCase: FetchGithubOAuthUseCase,
    private val fetchGithubOAuthCheckUseCase: FetchGithubOAuthCheckUseCase
) : BaseViewModel<MainActivityViewModel.Event>() {

    fun registerGithubUser(githubOAuthEntity: GithubOAuthEntity) {
        execute(
            job = {
                val connected = runBlocking(Dispatchers.IO) {
                    fetchGithubOAuthCheckUseCase.execute(Unit).is_connected
                }.also { println("CONNCONN $it") }
                if (connected) {
                    fetchGithubOAuthUseCase.execute(githubOAuthEntity)
                }
            },
            onSuccess = { emitEvent(Event.OAuthSuccess) },
            onFailure = { emitEvent(Event.OAuthFailure) }
        )
    }

    sealed class Event {
        object OAuthSuccess : Event()
        object OAuthFailure : Event()
    }
}