package com.xquare.xquare_android.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.color.primary.gray.*
import com.semicolon.design.color.primary.white.white
import com.xquare.domain.entity.profile.ProfileEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.util.DevicePaddings
import com.xquare.xquare_android.util.makeToast
import kotlinx.coroutines.flow.collect
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = hiltViewModel()
    var profile: ProfileEntity? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        viewModel.fetchProfile()
        viewModel.eventFlow.collect {
            when (it) {
                is ProfileViewModel.Event.Success -> {
                    profile = it.data
                }
                is ProfileViewModel.Event.Failure -> {
                    makeToast(context, "프로필을 불러오지 못했습니다")
                }
            }
        }
    }
    Profile(
        profile = profile,
        onBackPress = { navController.popBackStack() }
    )
}

@Composable
private fun Profile(
    profile: ProfileEntity?,
    onBackPress: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .background(white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
        topBar = {
            AppBar(
                painter = painterResource(R.drawable.ic_placeholder),
                text = "마이페이지",
                onIconClick = onBackPress
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(20.dp))
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(30.dp)),
                painter = rememberAsyncImagePainter(
                    model = profile?.profileFileName,
                    placeholder = ColorPainter(gray200),
                    error = ColorPainter(gray200)
                ),
                contentDescription = null
            )
            Spacer(Modifier.size(6.dp))
            Body2(text = "변경하기", color = gray900)
            Spacer(Modifier.size(6.dp))
            Column {
                Body2(text = "이름", color = gray900)
                Spacer(Modifier.size(8.dp))
                ProfileInfoText(text = profile?.name ?: "")
            }
            Spacer(Modifier.size(20.dp))
            Column {
                Body2(text = "생년월일", color = gray900)
                Spacer(Modifier.size(8.dp))
                ProfileInfoText(text = profile?.birthday?.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                    ?: "")
            }
            Spacer(Modifier.size(20.dp))
            Column {
                Body2(text = "학년 반 번호", color = gray900)
                Spacer(Modifier.size(8.dp))
                ProfileInfoText(text = "${profile?.grade ?: ""}학년 ${profile?.classNum ?: ""}반 ${profile?.num ?: ""}번")
            }
            Spacer(Modifier.size(20.dp))
            Column {
                Body2(text = "아이디", color = gray900)
                Spacer(Modifier.size(8.dp))
                ProfileInfoText(text = profile?.accountId ?: "")
            }
        }
    }
}

@Composable
private fun ProfileInfoText(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color = gray300, shape = RoundedCornerShape(8.dp))
            .background(color = gray50)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Body1(text = text, color = gray800)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview() {
    Profile(profile = ProfileEntity("qwer1234",
        "신희원",
        LocalDate.parse("2004-09-25"),
        3,
        2,
        9,
        null)) {

    }
}