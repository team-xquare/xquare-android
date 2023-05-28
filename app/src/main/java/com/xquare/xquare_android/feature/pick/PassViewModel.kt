package com.xquare.xquare_android.feature.pick

import android.os.Build
import androidx.annotation.RequiresApi
import com.xquare.domain.entity.pick.PassDataEntity
import com.xquare.domain.usecase.pick.FetchPassDataUseCase
import com.xquare.xquare_android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PassViewModel @Inject constructor(
    private val fetchPassDataUseCase: FetchPassDataUseCase
): BaseViewModel<PassViewModel.Event>() {


    private val _passData = MutableStateFlow(
        PassDataEntity(
            "","","","","","","", picnic_date = ""
        )
    )
    val passData: StateFlow<PassDataEntity> = _passData

    fun fetchPassData() {
        execute(
            job = { fetchPassDataUseCase.execute(Unit) },
            onSuccess = { _passData.tryEmit(it) },
            onFailure = { emitEvent(Event.Fail) }
        )
    }

    sealed class Event {
        object Fail : Event()
    }
}