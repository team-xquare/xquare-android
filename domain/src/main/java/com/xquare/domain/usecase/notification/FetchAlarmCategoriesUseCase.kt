package com.xquare.domain.usecase.notification

import com.xquare.domain.entity.notification.AlarmCategoriesEntity
import com.xquare.domain.repository.notification.AlarmRepository
import com.xquare.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAlarmCategoriesUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
): UseCase<Unit, Flow<AlarmCategoriesEntity>>() {
    override suspend fun execute(data: Unit): Flow<AlarmCategoriesEntity> =
        alarmRepository.fetchAlarmCategories()
}