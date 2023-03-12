package com.xquare.xquare_android.feature.all

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.gray.gray600
import com.semicolon.design.color.primary.gray.gray700
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun AllScreen(navController: NavController) {
    val schoolMenuList = listOf("오늘의 자습감독 선생님")//"동아리 지원하기", "오늘의 자습감독 선생님", "랭킹")
    val dormitoryMenuList = listOf("봉사 지원하기", "청소판 확인하기")
    Scaffold(
        modifier = Modifier
            .background(white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp
            ),
        topBar = { AppBar(text = "전체") }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 27.dp)
            ) {
                RowMenuItem(painterResource(id = R.drawable.ic_thumbs_up), "상벌점 확인") {
                    navController.navigate(AppNavigationItem.PointHistory.route)
                }
                Spacer(modifier = Modifier.size(8.dp))
                RowMenuItem(painterResource(id = R.drawable.ic_profile), "마이페이지") {
                    navController.navigate(AppNavigationItem.Profile.route)
                }
                Spacer(modifier = Modifier.size(8.dp))
                RowMenuItem(painterResource(id = R.drawable.ic_warning), "버그 제보") {
                    navController.navigate(AppNavigationItem.Bug.route)
                }
            }
            Spacer(modifier = Modifier.size(30.dp))
            /* Body1(text = "학교", color = gray900, fontWeight = FontWeight.Medium)
             schoolMenuList.forEachIndexed { index, title ->
                 Spacer(modifier = Modifier.size(12.dp))
                 ColumnMenuItem(title) {
                     when (index) {
                         0 -> navController.navigate(AppNavigationItem.Director.route)
                     }
                 }
             }*/
            Spacer(modifier = Modifier.size(27.dp))
            /*Body1(text = "기숙사", color = gray900, fontWeight = FontWeight.Medium)
            dormitoryMenuList.forEach { title ->
                Spacer(modifier = Modifier.size(12.dp))
                ColumnMenuItem(title) {
                    // TODO
                }
            }*/
        }
    }
}

@Composable
private fun RowScope.RowMenuItem(
    painter: Painter,
    title: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                enabled = true
            ) { onClick() }
            .weight(1f)
            .background(gray50, RoundedCornerShape(8.dp))
            .padding(top = 15.dp, bottom = 11.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(modifier = Modifier.height(20.dp), painter = painter, tint = gray700, contentDescription = null)
        Spacer(modifier = Modifier.size(7.dp))
        Body2(text = title, color = gray600, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ColumnMenuItem(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                enabled = true
            ) { onClick() }
            .fillMaxWidth()
            .background(gray50, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Body1(text = text, color = gray900)
    }
}

@Preview(showBackground = true)
@Composable
fun AllScreenPreview() {
    AllScreen(rememberNavController())
}