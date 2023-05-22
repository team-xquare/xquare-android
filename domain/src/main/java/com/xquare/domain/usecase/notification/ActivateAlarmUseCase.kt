package com.xquare.domain.usecase.notification

import com.xquare.domain.entity.notification.ActivateAlarmEntity
import com.xquare.domain.repository.notification.AlarmRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class ActivateAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
): UseCase<ActivateAlarmEntity,Unit>() {
    override suspend fun execute(data: ActivateAlarmEntity) {
        alarmRepository.activateAlarm(isActivate = data.isActivated,topic = data.topic)
    }
}