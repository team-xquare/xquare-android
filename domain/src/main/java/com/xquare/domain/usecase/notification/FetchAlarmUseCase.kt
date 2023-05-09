package com.xquare.domain.usecase.notification

import com.xquare.domain.entity.notification.AlarmEntity
import com.xquare.domain.repository.notification.AlarmRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
) : UseCase<Unit, AlarmEntity>() {
    override suspend fun execute(data: Unit): AlarmEntity =
        alarmRepository.fetchAlarmHistory()
}