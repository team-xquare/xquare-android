package com.xquare.domain.usecase.notification

import com.xquare.domain.entity.notification.AlarmCategoriesEntity
import com.xquare.domain.repository.notification.AlarmRepository
import com.xquare.domain.usecase.UseCase
import javax.inject.Inject

class FetchAlarmCategoriesUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
): UseCase<Unit,AlarmCategoriesEntity>() {
    override suspend fun execute(data: Unit): AlarmCategoriesEntity =
        alarmRepository.fetchAlarmCategories()
}