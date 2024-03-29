package com.xquare.xquare_android.feature.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.semicolon.design.Body2
import com.semicolon.design.button.ColoredLargeButton
import com.semicolon.design.color.primary.dark.dark200
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.gray.gray500
import com.semicolon.design.color.primary.gray.gray700
import com.semicolon.design.color.primary.gray.gray800
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.R
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun OnboardScreen(navController: NavController) {
    OnBoard(
        images = listOf(
            painterResource(R.drawable.img_onboard_1),
            painterResource(R.drawable.img_onboard_2),
            painterResource(R.drawable.img_onboard_3),
            painterResource(R.drawable.img_onboard_4),
            painterResource(R.drawable.img_onboard_5),
        ),
        onSignUpClick = { navController.navigate(AppNavigationItem.SignUp.route) },
        onSignInClick = { navController.navigate(AppNavigationItem.SignIn.route) }
    )
}

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun OnBoard(
    images: List<Painter>,
    onSignUpClick: () -> Unit = {},
    onSignInClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(white)
            .padding(bottom = DevicePaddings.navigationBarHeightDp.dp),
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            val pagerState = rememberPagerState()
            HorizontalPager(
                modifier = Modifier
                    .padding(bottom = 208.dp)
                    .fillMaxSize(),
                count = images.count(),
                state = pagerState
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = images[this.currentPage],
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val interactionSource = remember { MutableInteractionSource() }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = purple400,
                    inactiveColor = dark200
                )
                Spacer(Modifier.size(40.dp))
                ColoredLargeButton(text = "바로 시작하기") { onSignUpClick() }
                Spacer(Modifier.size(20.dp))
                Row(
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onSignInClick()
                        },
                ) {
                    Body2(text = "이미 계정이 있으신가요? ", color = gray800)
                    Body2(text = "로그인하기", fontWeight = FontWeight.Medium)
                }
                Spacer(Modifier.size(40.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnBoardPreview() {
    OnBoard(listOf())
}