package com.xquare.xquare_android.feature.home

import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.Body3
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.*
import com.semicolon.design.color.primary.purple.purple400
import com.semicolon.design.color.primary.white.white
import com.semicolon.design.notoSansFamily
import com.xquare.domain.entity.pick.ClassPositionEntity
import com.xquare.domain.entity.pick.PassTimeEntity
import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.entity.user.HomeUserEntity
import com.xquare.xquare_android.MainActivity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.navigation.AppNavigationItem
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val mainActivity = context as MainActivity

    val viewModel: HomeViewModel = hiltViewModel()
    val userData = viewModel.userSimpleData.collectAsState().value
    val meal = viewModel.todayMeal.collectAsState().value
    val classPosition = viewModel.classPosition.collectAsState().value
    val passCheck = viewModel.passCheck.collectAsState().value
    LaunchedEffect(Unit) {
        mainActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        viewModel.run {
            fetchPassTime()
            fetchClassPosition()
            fetchUserSimpleData()
            fetchTodayMeal()
        }
        viewModel.eventFlow.collect {
            when (it) {
                is HomeViewModel.Event.BackToClassRoom -> viewModel.fetchClassPosition()
            }
        }
    }
    HomeContent(
        userData = userData,
        meal = meal,
        classPosition = classPosition,
        passCheck = passCheck,
        onAllMealClick = { navController.navigate(AppNavigationItem.AllMeal.route) },
        onAlarmClick = { navController.navigate(AppNavigationItem.Alarm.route) },
        onUserCardClick = { navController.navigate(AppNavigationItem.PointHistory.route) },
        onClassClick = { viewModel.backToClassRoom() },
        onPassClick = { navController.navigate(AppNavigationItem.Pass.route) }
    )
}

@Composable
fun HomeContent(
    userData: HomeUserEntity,
    meal: MealEntity,
    classPosition: ClassPositionEntity,
    passCheck: PassTimeEntity,
    onUserCardClick: () -> Unit,
    onAllMealClick: () -> Unit,
    onAlarmClick: () -> Unit,
    onClassClick: () -> Unit,
    onPassClick: () -> Unit,
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
        HomePickContent(
            classPosition = classPosition,
            passCheck = passCheck,
            onClassClick = onClassClick,
            onPassClick = onPassClick,
        )
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
        Subtitle4(text = "홈", fontWeight = FontWeight.Bold)
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
                fontSize = 16.sp,
                fontFamily = notoSansFamily,
                fontWeight = FontWeight.Bold,
                color = gray900
            )
            Body2(
                text = "상점 ${userData.goodPoint}점 벌점 ${userData.badPoint}점",
                color = gray700
            )
        }
    }
}


@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
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
            .padding(vertical = 16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "오늘의 메뉴",
                fontSize = 16.sp,
                color = gray900,
                fontFamily = notoSansFamily,
                fontWeight = FontWeight.Normal,
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
        Spacer(Modifier.size(12.dp)
            .padding(horizontal = 12.dp))
        CompositionLocalProvider {
            Row(
                Modifier
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 12.dp)
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
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .size(150.dp, 210.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(gray50)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .verticalScroll(scrollState)
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
        if (menus.isEmpty()) {
            Body2(text = "등록된 정보가\n없습니다.", color = gray800)
        } else {
            menus.forEach {
                Body2(text = it, color = gray800)
            }
        }
    }
}

enum class HomePickCardButtonState(
    val backgroundColor: Color,
    val text: String,
    val textColor: Color,
    val strokeColor: Color,
) {
    ClassPosition(
        backgroundColor = white,
        text = "교실로 돌아가기",
        textColor = gray700,
        strokeColor = gray400,
    ),
    PassCheck(
        backgroundColor = purple400,
        text = "외출증 확인하기",
        textColor = white,
        strokeColor = Color(0x00000000)
    ),
}

@Composable
fun HomePickContent(
    classPosition: ClassPositionEntity,
    passCheck: PassTimeEntity,
    onClassClick: () -> Unit,
    onPassClick: () -> Unit,
) {
    if (classPosition.name.isNotEmpty()) {
        Spacer(modifier = Modifier.size(16.dp))
        HomePickCard(
            state = HomePickCardButtonState.ClassPosition,
            topText = buildAnnotatedString {
                append("현재 ")
                append(classPosition.name)
                append("님은")
            }.toString(),
            underText = "에 있습니다.",
            pointText = classPosition.location_classroom,
        ) {
            onClassClick()
        }
    }
    if (passCheck.user_id.isNotEmpty()) {
        Spacer(modifier = Modifier.size(16.dp))
        HomePickCard(
            state = HomePickCardButtonState.PassCheck,
            topText = buildAnnotatedString {
                append(passCheck.name)
                append("님의 외출시간은")
            }.toString(),
            underText = "까지 입니다.",
            pointText = passCheck.end_time,
        ) {
            onPassClick()
        }
    }
}
@Composable
fun HomePickCard(
    state: HomePickCardButtonState,
    topText: String,
    underText: String,
    pointText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = white,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Spacer(modifier = Modifier.size(16.dp))
            Body2(text = topText)
            Row {
                Body2(text = pointText, fontWeight = FontWeight.Medium)
                Body2(text = underText)
            }
            Spacer(modifier = Modifier.size(16.dp))
        }
        Box(
            modifier = Modifier
                .padding(end = 16.dp)
                .fillMaxWidth()
                .width(118.dp)
                .height(48.dp)
                .wrapContentWidth(align = Alignment.End)
                .background(
                    color = state.backgroundColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = state.strokeColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                ) {
                    onClick()
                },
            contentAlignment = Alignment.Center,
        ) {
            Body3(
                text = state.text,
                color = state.textColor,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
    }
}
