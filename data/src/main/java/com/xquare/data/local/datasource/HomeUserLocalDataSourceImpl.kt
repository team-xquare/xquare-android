package com.xquare.data.local.datasource

import com.xquare.data.dao.HomeUserDao
import com.xquare.data.local.entity.homeUser.toEntity
import com.xquare.data.local.entity.homeUser.toRoomEntity
import com.xquare.domain.entity.user.HomeUserEntity
import javax.inject.Inject

class HomeUserLocalDataSourceImpl @Inject constructor(
    private val homeUserDao: HomeUserDao
): HomeUserLocalDataSource{
    override suspend fun fetchHomeUser(): HomeUserEntity =
        homeUserDao.fetchHomeUser().toEntity()


    override suspend fun saveHomeUser(homeUserEntity: HomeUserEntity) =
        homeUserDao.updateHomeUser(homeUserEntity.toRoomEntity())

}
