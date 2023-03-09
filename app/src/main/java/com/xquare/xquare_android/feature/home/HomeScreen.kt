package com.xquare.xquare_android.feature.home

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.*
import com.semicolon.design.notoSansFamily
import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.entity.user.HomeUserEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val userData = viewModel.userSimpleData.collectAsState().value
    val meal = viewModel.todayMeal.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.run {
            fetchTodayMeal()
            fetchUserSimpleData()
        }
    }
    HomeContent(
        userData = userData,
        meal = meal,
        onAllMealClick = { navController.navigate(AppNavigationItem.AllMeal.route) },
        onAlarmClick = { navController.navigate(AppNavigationItem.Alarm.route) },
        onUserCardClick = { navController.navigate(AppNavigationItem.PointHistory.route) }
    )
}

@Composable
fun HomeContent(
    userData: HomeUserEntity,
    meal: MealEntity,
    onUserCardClick: () -> Unit,
    onAllMealClick: () -> Unit,
    onAlarmClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(gray50)
            .padding(top = DevicePaddings.statusBarHeightDp.dp)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        HomeAppBar(onAlarmClick = onAlarmClick)
        HomeUserCard(userData = userData, onClick = onUserCardClick)
        Spacer(Modifier.size(16.dp))
        HomeMealCard(meal = meal, onAllMealClick = onAllMealClick)
    }
}

@Composable
fun HomeAppBar(onAlarmClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Subtitle4(text = "홈")
        Icon(
            painter = painterResource(id = R.drawable.ic_alarm),
            contentDescription = "alarm",
            tint = gray500,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.End)
                .size(24.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) {
                    onAlarmClick()
                }
        )
    }
}

@Composable
fun HomeUserCard(userData: HomeUserEntity, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                enabled = true
            ) { onClick() }

    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = userData.profileFileImage,
                placeholder = ColorPainter(gray200),
                error = painterResource(id = R.drawable.ic_profile_default),
            ),
            contentDescription = "profileImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(22.dp))
        )
        Spacer(Modifier.size(12.dp))
        Column {
            Text(
                text = userData.name,
                fontSize = 18.sp,
                fontFamily = notoSansFamily,
                fontWeight = FontWeight.Medium,
                color = gray900
            )
            Body2(
                text = "상점 ${userData.goodPoint}점 벌점 ${userData.badPoint}",
                color = gray700
            )
        }
    }
}


@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeMealCard(
    meal: MealEntity,
    onAllMealClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "오늘의 메뉴",
                fontSize = 18.sp,
                color = gray900,
                fontFamily = notoSansFamily,
                fontWeight = FontWeight.Medium,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        enabled = true
                    ) { onAllMealClick() },
                tint = Color.Unspecified
            )
        }
        Spacer(Modifier.size(12.dp))
        CompositionLocalProvider(
            LocalOverScrollConfiguration provides null
        ) {
            Row(
                Modifier.horizontalScroll(
                    scrollState
                )
            ) {
                Spacer(Modifier.size(4.dp))
                HomeMealItem(
                    title = "아침",
                    menus = meal.breakfast,
                    calorie = meal.caloriesOfBreakfast
                )
                Spacer(Modifier.size(8.dp))
                HomeMealItem(
                    title = "점심",
                    menus = meal.lunch,
                    calorie = meal.caloriesOfLunch
                )
                Spacer(Modifier.size(8.dp))
                HomeMealItem(
                    title = "저녁",
                    menus = meal.dinner,
                    calorie = meal.caloriesOfDinner
                )
                Spacer(Modifier.size(4.dp))
            }
        }
    }
}

@Composable
fun HomeMealItem(title: String, menus: List<String>, calorie: String) {
    Column(
        modifier = Modifier
            .size(150.dp, 210.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(gray50)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Body1(text = title, color = gray800)
            Body2(
                text = calorie,
                color = gray800,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = Alignment.End)
            )
        }
        Spacer(Modifier.size(8.dp))
        menus.forEach {
            Body2(text = it, color = gray800)
        }
    }
}
