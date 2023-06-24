package com.xquare.xquare_android.feature.point_history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.semicolon.design.Body2
import com.semicolon.design.color.primary.dark.dark50
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.gray.gray700
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.point.PointHistoriesEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.CenterAppBar
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun PointHistoryScreen(navController: NavController) {
    val viewModel: PointHistoryViewModel = hiltViewModel()
    val pointHistories = viewModel.pointHistory.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.fetchGoodPointHistories(offlineOnly = false)
    }
    PointHistory(
        pointHistoriesEntity = pointHistories,
        onGoodPointSelected = { viewModel.fetchGoodPointHistories() },
        onBadPointSelected = { viewModel.fetchBadPointHistories() },
        onBackPress = { navController.popBackStack() }
    )
}

@Composable
fun PointHistory(
    pointHistoriesEntity: PointHistoriesEntity?,
    onGoodPointSelected: () -> Unit,
    onBadPointSelected: () -> Unit,
    onBackPress: () -> Unit,
) {
    var isGoodPointSelected by remember { mutableStateOf(true) }
    Scaffold(
        modifier = Modifier
            .background(white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
        topBar = {
            CenterAppBar(
                painter = painterResource(R.drawable.ic_back),
                text = "상벌점 내역",
                onIconClick = onBackPress
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
        ) {
            Body2(text = "상점 ${pointHistoriesEntity?.goodPoint ?: 0}점 벌점 ${pointHistoriesEntity?.badPoint ?: 0}점", color = gray700)
            Spacer(Modifier.size(16.dp))
            Row {
                SelectableButton(text = "상점", isSelected = isGoodPointSelected) {
                    isGoodPointSelected = true
                    onGoodPointSelected()
                }
                Spacer(Modifier.size(8.dp))
                SelectableButton(text = "벌점", isSelected = !isGoodPointSelected) {
                    isGoodPointSelected = false
                    onBadPointSelected()
                }
            }
            Spacer(Modifier.size(16.dp))
            pointHistoriesEntity?.pointHistories?.let {
                LazyColumn {
                    items(it.count()) { idx ->
                        PointHistoryItem(it[idx])
                        if (idx == it.lastIndex)
                            Spacer(Modifier.size(20.dp))
                        else Spacer(Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SelectableButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(if (isSelected) purple400 else dark50, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                enabled = true
            ) { if (!isSelected) onClick() }
    ) {
        Body2(text = text, color = if (isSelected) gray50 else gray900)
    }
}

