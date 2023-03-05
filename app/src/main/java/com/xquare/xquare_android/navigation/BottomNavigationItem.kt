package com.xquare.xquare_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.xquare.xquare_android.R

enum class BottomNavigationItem(
    val route: String,
    val icon: @Composable () -> Painter,
    val label: String
) {
    Home(
        route = "home",
        icon = { painterResource(R.drawable.ic_home) },
        label = "홈"
    ),
    Schedule(
        route = "schedule",
        icon = { painterResource(R.drawable.ic_schedule) },
        label = "일정"
    ),
    Feed(
        route = "feed",
        icon = { painterResource(R.drawable.ic_feed) },
        label = "피드"
    ),
    Application(
        route = "apply",
        icon = { painterResource(R.drawable.ic_apply) },
        label = "신청"
    ),
    All(
        route = "all",
        icon = { painterResource(R.drawable.ic_all) },
        label = "전체"
    ),
}