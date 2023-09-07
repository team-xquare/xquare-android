package com.xquare.xquare_android.feature.github

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.Body3
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.gray.gray700
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.purple.purple200
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

    LaunchedEffect(Unit){
        viewModel.fetchInformation()
        viewModel.eventFlow.collect(){
            when(it){
                is GithubViewModel.Event.InformationSuccess -> {
                    information = it.data
                }
                is GithubViewModel.Event.Failure -> {
                    makeToast(context, "Github 정보를 불러오기에 실패했습니다.")
                }

            }
        }
    }
    Github(
        githubInformation = information,
        githubList = githubList,
        onBack = { navController.popBackStack() })

}

@Composable
fun Github(
    githubList: GithubListEntity?,
    githubInformation: GithubInformationEntity?,
    onBack: () -> Unit,
) {
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
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                ),
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                GithubItem(
                    modifier = Modifier.weight(1f),
                    crown = painterResource(id = R.drawable.ic_github_crown),
                    profile = painterResource(id = R.drawable.ic_profile_default),
                    name = "김연우",
                    id = "@yeon0821",
                    commit = "1,828커밋",
                    color = Silver,
                    centerPadding = 15.dp,
                    tint = Silver
                )
                GithubItem(
                    modifier = Modifier.weight(1f),
                    crown = painterResource(id = R.drawable.ic_github_crown),
                    profile = painterResource(id = R.drawable.ic_profile_default),
                    name = "박준수",
                    id = "@junjaㅈboy",
                    commit = "18,181커밋",
                    color = Gold,
                    centerPadding = 30.dp,
                    tint = Gold
                )
                GithubItem(
                    modifier = Modifier.weight(1f),
                    crown = painterResource(id = R.drawable.ic_github_crown),
                    profile = painterResource(id = R.drawable.ic_profile_default),
                    name = "정승훈",
                    id = "@Tmdhoon2",
                    commit = "1,818커밋",
                    color = Bronze,
                    centerPadding = 1.dp,
                    tint = Bronze
                )
            }
            Spacer(Modifier.size(17.dp))
            Body1(text = "나의 순위", color = gray900, fontWeight = FontWeight.Bold)
            Spacer(Modifier.size(16.dp))
            GithubRankingItem(
                ranking = "2위",
                profile = painterResource(id = R.drawable.ic_profile_default),
                name = "김연우",
                id = "@yeon0821",
                commit = "1,828 커밋",
                borderState = true
            )
            Spacer(Modifier.size(16.dp))
            Body1(text = "전체 순위", color = gray900, fontWeight = FontWeight.Bold)
            Spacer(Modifier.size(16.dp))
            LazyColumn() {
                //TODO(준수님이 해주시겠죠?)
            }
        }
    }
}

@Composable
private fun GithubRankingItem(
    ranking: String,
    profile: Painter,
    name: String,
    id: String,
    commit: String,
    borderState: Boolean,
) {
    val borderColor = if (borderState) purple200 else gray50
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .background(color = gray50, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
            .padding(start = 25.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Body2(text = ranking, fontWeight = FontWeight.Normal, color = gray900)
        Spacer(Modifier.size(15.dp))
        Image(
            painter = profile,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(30.dp)),
        )
        Spacer(Modifier.size(10.dp))
        Column {
            Body2(text = name, fontWeight = FontWeight.Medium, color = gray900)
            Body3(text = id, fontWeight = FontWeight.Normal, color = gray700)
        }
        Body1(
            text = commit,
            fontWeight = FontWeight.SemiBold,
            color = gray900,
            modifier = Modifier.padding(start = 25.dp)
        )
    }
}

@Composable
private fun RowScope.GithubItem(
    modifier: Modifier = Modifier,
    crown: Painter,
    profile: Painter,
    name: String,
    id: String,
    commit: String,
    color: Color,
    centerPadding: Dp,
    tint: Color
) {
    Column(
        modifier = modifier
            .background(gray50, RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = color, shape = RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            Modifier.padding(top = 10.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Icon(
                    painter = crown,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.TopCenter),
                    tint = tint
                )
                Image(
                    painter = profile,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = (15.5).dp)
                        .size(44.dp)
                        .clip(RoundedCornerShape(30.dp)),
                )
            }
            Spacer(Modifier.height(centerPadding))
            Subtitle4(text = name, color = gray900, fontWeight = FontWeight.SemiBold)
            Body3(text = id, color = gray700, fontWeight = FontWeight.Normal)
            Body3(text = commit, color = gray900, fontWeight = FontWeight.Normal)
        }
    }
}

