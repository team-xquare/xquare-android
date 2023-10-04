package com.xquare.xquare_android.feature.github

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.Body1
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.github.GithubInformationEntity
import com.xquare.domain.entity.github.GithubListEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.CenterAppBar
import com.xquare.xquare_android.theme.Bronze
import com.xquare.xquare_android.theme.Gold
import com.xquare.xquare_android.theme.Silver
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast

@Composable
fun GithubScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val viewModel: GithubViewModel = hiltViewModel()
    var information: GithubInformationEntity? by remember { mutableStateOf(null) }
    var githubList: GithubListEntity? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect {
            when (it) {
                is GithubViewModel.Event.InformationSuccess -> {
                    information = it.data
                }

                is GithubViewModel.Event.GithubInformationFailure -> {}

                is GithubViewModel.Event.GithubUserListFailure -> {
                    makeToast(context, "Github 랭킹을 불러올 수 없습니다.")
                }

                is GithubViewModel.Event.UserListSuccess -> {
                    githubList = it.list
                }
            }
        }
    }

    Github(
        githubInformation = information,
        githubList = githubList,
        onBack = { navController.popBackStack() },
    )
}

@Composable
fun Github(
    githubList: GithubListEntity?,
    githubInformation: GithubInformationEntity?,
    onBack: () -> Unit,
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .background(white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
        topBar = {
            CenterAppBar(
                painter = painterResource(id = R.drawable.ic_back),
                text = "Github 랭킹",
                onIconClick = onBack
            )
        },
    ) { padValues ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(padValues),
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            githubList?.run {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    GithubItem(
                        modifier = Modifier.weight(1f),
                        githubList = githubList.users[1],
                        crown = painterResource(id = R.drawable.ic_github_crown),
                        color = Silver,
                        centerPadding = 10.dp,
                        tint = Silver,
                        image = 34.dp
                    )
                    GithubItem(
                        modifier = Modifier.weight(1f),
                        githubList = githubList.users[0],
                        crown = painterResource(id = R.drawable.ic_github_crown),
                        color = Gold,
                        centerPadding = 25.dp,
                        tint = Gold,
                        image = 44.dp
                    )
                    GithubItem(
                        modifier = Modifier.weight(1f),
                        githubList = githubList.users[2],
                        crown = painterResource(id = R.drawable.ic_github_crown),
                        color = Bronze,
                        centerPadding = 8.dp,
                        tint = Bronze,
                        image = 34.dp
                    )
                }
            }
            Spacer(Modifier.size(17.dp))
            if (githubInformation != null) {
                Body1(text = "나의 순위", color = gray900, fontWeight = FontWeight.Bold)
                Spacer(Modifier.size(16.dp))
                GithubRankingItem(
                    githubInformation = githubInformation,
                    borderState = true
                )
                Spacer(Modifier.size(16.dp))
            }
            if (githubList != null) {
                Body1(text = "전체 순위", color = gray900, fontWeight = FontWeight.Bold)
                Spacer(Modifier.size(16.dp))
                LazyColumn(
                    state = lazyListState
                ) {
                    items(githubList.users.count()) {
                        GithubAllRankingItem(
                            githubList = githubList.users[it],
                            borderState = false
                        )
                    }
                }
            }
        }
    }
}
