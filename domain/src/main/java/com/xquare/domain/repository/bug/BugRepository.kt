package com.xquare.domain.repository.bug

import com.xquare.domain.entity.bug.BugEntity

interface BugRepository {
    suspend fun uploadBug(bugEntity: BugEntity)
}