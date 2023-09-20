package com.xquare.xquare_android

import com.xquare.domain.entity.github.GithubOAuthEntity
import com.xquare.domain.usecase.github.FetchGithubOAuthUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityVIewModel @Inject constructor(
    private val fetchGithubOAuthUseCase: FetchGithubOAuthUseCase
): BaseViewModel<MainActivityVIewModel.Event>() {

   fun fetchGithubOAuth(githubOAuthEntity: GithubOAuthEntity) =
       execute(
           job = {fetchGithubOAuthUseCase.execute(githubOAuthEntity)},
           onSuccess = { emitEvent(Event.OAuthSuccess)},
           onFailure = { emitEvent(Event.OAuthFailure)}
       )


   sealed class Event {

       object OAuthSuccess : Event()

       object OAuthFailure : Event()
    }
}