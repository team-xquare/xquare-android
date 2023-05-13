package com.xquare.data.remote.datasource

import com.xquare.domain.entity.reports.BugEntity

interface BugRemoteDataSource {

    suspend fun uploadBug(bugEntity: BugEntity)
}