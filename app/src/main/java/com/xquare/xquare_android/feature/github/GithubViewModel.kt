package com.xquare.xquare_android.feature.github

import com.xquare.domain.entity.github.GithubInformationEntity
import com.xquare.domain.entity.github.GithubListEntity
import com.xquare.domain.entity.github.GithubOAuthCheckEntity
import com.xquare.domain.entity.github.GithubOAuthEntity
import com.xquare.domain.usecase.github.FetchGithubInformationUseCase
import com.xquare.domain.usecase.github.FetchGithubListUseCase
import com.xquare.domain.usecase.github.FetchGithubOAuthCheckUseCase
import com.xquare.domain.usecase.github.FetchGithubOAuthUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val fetchGithubInformationUseCase: FetchGithubInformationUseCase,
    private val fetchGithubListUseCase: FetchGithubListUseCase,


) : BaseViewModel<GithubViewModel.Event>() {


    fun fetchInformation() =
        execute(
            job = { fetchGithubInformationUseCase.execute(Unit) },
            onSuccess = { emitEvent(Event.InformationSuccess(it)) },
            onFailure = { emitEvent(Event.GithubInformationFailure) }
        )


    fun fetchUserList() =
        execute(
            job = { fetchGithubListUseCase.execute(Unit)},
            onSuccess = { emitEvent(Event.UserListSuccess(it))},
            onFailure = { emitEvent(Event.GithubUserListFailure)}
        )

//    fun fetch


    sealed class Event {


        data class InformationSuccess(val data: GithubInformationEntity) : Event()


        data class UserListSuccess(val list: GithubListEntity): Event()

        object GithubInformationFailure : Event()

        object GithubUserListFailure: Event()


    }
}