package com.xquare.xquare_android.feature.home

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.color.primary.gray.*
import com.semicolon.design.notoSansFamily
import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.entity.point.DormitoryPointEntity
import com.xquare.domain.entity.user.HomeUserEntity

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val userName = viewModel.userName.collectAsState().value
    val dormitoryPoint = viewModel.dormitoryPoint.collectAsState().value
    val meal = viewModel.todayMeal.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.run {
//            fetchUserName()
//            fetchDormitoryPoint()
//            fetchTodayMeal()
        }
    }
    HomeContent(user = userName, dormitoryPoint = dormitoryPoint, meal = meal)
}

@Composable
fun HomeContent(user: HomeUserEntity, dormitoryPoint: DormitoryPointEntity, meal: MealEntity) {
    Column(
        modifier = Modifier
            .background(gray50)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        HomeAppBar()
        HomeUserCard(user = user, dormitoryPoint = dormitoryPoint)
        Spacer(Modifier.size(16.dp))
        HomeMealCard(meal = meal)
    }
}

@Composable
fun HomeAppBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            painter = ColorPainter(gray200),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
        Spacer(Modifier.size(16.dp))
        Icon(
            painter = ColorPainter(gray200),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun HomeUserCard(user: HomeUserEntity, dormitoryPoint: DormitoryPointEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)

    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = user.profileImage,
                placeholder = ColorPainter(gray200),
                error = ColorPainter(gray200)
            ),
            contentDescription = "profileImage",
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(22.dp))
        )
        Spacer(Modifier.size(12.dp))
        Column {
            Text(
                text = user.name,
                fontSize = 18.sp,
                fontFamily = notoSansFamily,
                fontWeight = FontWeight.Medium,
                color = gray900
            )
            Body2(
                text = "?????? ${dormitoryPoint.goodPoint}??? ?????? ${dormitoryPoint.badPoint}",
                color = gray700
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeMealCard(meal: MealEntity) {
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
                text = "????????? ??????",
                fontSize = 18.sp,
                color = gray900,
                fontFamily = notoSansFamily,
                fontWeight = FontWeight.Medium,
            )
            Icon(
                painter = ColorPainter(gray200),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
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
                HomeMealItem(title = "??????", menus = meal.breakfast)
                Spacer(Modifier.size(8.dp))
                HomeMealItem(title = "??????", menus = meal.lunch)
                Spacer(Modifier.size(8.dp))
                HomeMealItem(title = "??????", menus = meal.dinner)
                Spacer(Modifier.size(4.dp))
            }
        }
    }
}

@Composable
fun HomeMealItem(title: String, menus: List<String>) {
    Column(
        modifier = Modifier
            .size(148.dp, 198.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(gray50)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Body1(text = title, color = gray800)
        Spacer(Modifier.size(8.dp))
        menus.forEach {
            Body2(text = it, color = gray800)
        }
    }
}

@Preview
@Composable
fun HomeContentPreview() {
    HomeContent(
        user = HomeUserEntity("", "?????????"),
        dormitoryPoint = DormitoryPointEntity(5, 2),
        meal = MealEntity(
            listOf("???", "??????", "???", "??????"),
            listOf("???", "??????", "???", "??????"),
            listOf("???", "??????", "???", "??????")
        )
    )
}

@Preview
@Composable
fun HomeMealItemPreview() {
    HomeMealItem(title = "??????", menus = listOf("???", "??????", "???", "??????"))
}