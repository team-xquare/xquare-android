package com.xquare.xquare_android.feature.release

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.reports.ReleaseEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun ReleaseScreen(
    navController: NavController,
){
    val viewModel: ReleaseViewModel = hiltViewModel()
    val release = viewModel.release.collectAsStateWithLifecycle().value
    LaunchedEffect(Unit) {
        viewModel.fetchRelease()
    }
    Release(
        release = release,
        onBackPress = { navController.popBackStack() }
    )
}

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@Composable
private fun Release(
    release: ReleaseEntity?,
    onBackPress: () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    Column(
        modifier = Modifier
            .background(white)
            .fillMaxSize()
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
    ) {
        Header(
            painter = painterResource(R.drawable.ic_back),
            title = "업데이트 사항",
            onIconClick = onBackPress
        )
        release?.run {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(release.releaseNoteList.count()) {
                    if (it == 0) Spacer(Modifier.size(20.dp))
                    ReleaseDetail(
                        releaseWithUpdateEntity = release.releaseNoteList[it],
                        borderState = it == 0
                    )
                    if (it == release.releaseNoteList.lastIndex)
                        Spacer(Modifier.size(20.dp))
                    else Spacer(Modifier.size(16.dp))
                }
            }
        }
    }
}