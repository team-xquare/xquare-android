package com.xquare.data.remote.datasource

import com.xquare.domain.entity.bug.BugEntity

interface BugRemoteDataSource {

    suspend fun uploadBug(bugEntity: BugEntity)
}