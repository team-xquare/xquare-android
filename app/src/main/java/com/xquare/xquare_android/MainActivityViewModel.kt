package com.xquare.xquare_android

import com.xquare.domain.entity.github.GithubOAuthEntity
import com.xquare.domain.usecase.github.FetchGithubOAuthCheckUseCase
import com.xquare.domain.usecase.github.FetchGithubOAuthUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val fetchGithubOAuthUseCase: FetchGithubOAuthUseCase,
    private val fetchGithubOAuthCheckUseCase: FetchGithubOAuthCheckUseCase
) : BaseViewModel<MainActivityViewModel.Event>() {

    fun registerGithubUser(githubOAuthEntity: GithubOAuthEntity) {
        println("CALLEDCALLED")
        execute(
            job = {
                val connected = fetchGithubOAuthCheckUseCase.execute(Unit).is_connected

                println("LOGLOGLOG $connected")
                if (!connected) {
                    fetchGithubOAuthUseCase.execute(githubOAuthEntity)
                }
            },
            onSuccess = { emitEvent(Event.OAuthSuccess)
                        println("SUCCESSSUCCESS")},
            onFailure = { emitEvent(Event.OAuthFailure) 
            println("FAILFAIL")}
        )
    }

    sealed class Event {
        object OAuthSuccess : Event()
        object OAuthFailure : Event()
    }
}