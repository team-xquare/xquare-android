package com.xquare.xquare_android.feature.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun AlarmScreen(
    navController: NavController,
) {
    Alarm(
        onBackPress = { navController.popBackStack() }
    )
}

@Composable
fun Alarm(
    onBackPress: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
    ) {
        AppBar(
            painter = painterResource(R.drawable.ic_placeholder),
            text = "알림",
            onIconClick = onBackPress,
        )
    }
}
