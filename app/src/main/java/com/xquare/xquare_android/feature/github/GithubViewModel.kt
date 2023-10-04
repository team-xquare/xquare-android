package com.xquare.xquare_android.feature.github

import androidx.lifecycle.viewModelScope
import com.xquare.domain.entity.github.GithubInformationEntity
import com.xquare.domain.entity.github.GithubListEntity
import com.xquare.domain.usecase.github.FetchGithubInformationUseCase
import com.xquare.domain.usecase.github.FetchGithubListUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubViewModel @Inject constructor(
    private val fetchGithubInformationUseCase: FetchGithubInformationUseCase,
    private val fetchGithubListUseCase: FetchGithubListUseCase,
) : BaseViewModel<GithubViewModel.Event>() {
    init {
        fetchInformation()
    }

    private fun fetchInformation() =
        execute(
            job = { fetchGithubInformationUseCase.execute(Unit) },
            onSuccess = {
                emitEvent(Event.InformationSuccess(it))
                fetchUserList()
            },
            onFailure = {
                emitEvent(Event.GithubInformationFailure)
                fetchUserList()
            }
        )

    private fun fetchUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                fetchGithubListUseCase.execute(Unit)
            }.onSuccess {
                emitEvent(Event.UserListSuccess(it))
            }.onFailure {
                emitEvent(Event.GithubUserListFailure)
            }
        }
    }

    sealed class Event {
        data class InformationSuccess(val data: GithubInformationEntity) : Event()
        data class UserListSuccess(val list: GithubListEntity) : Event()
        object GithubInformationFailure : Event()
        object GithubUserListFailure : Event()
    }
}