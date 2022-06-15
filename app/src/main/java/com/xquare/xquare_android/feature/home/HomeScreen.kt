package com.xquare.xquare_android.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.xquare.domain.entity.meal.MealEntity
import com.xquare.domain.entity.point.DormitoryPointEntity
import com.xquare.domain.entity.user.HomeUserEntity
import com.xquare.xquare_android.theme.Gray50

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val userName = viewModel.userName.collectAsState().value
    val dormitoryPoint = viewModel.dormitoryPoint.collectAsState().value
    val meal = viewModel.todayMeal.collectAsState().value

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        viewModel.run {
//            fetchUserName()
//            fetchDormitoryPoint()
//            fetchTodayMeal()
        }
    }

    Scaffold(scaffoldState = scaffoldState) {
        HomeContent(user = userName, dormitoryPoint = dormitoryPoint, meal = meal)
    }
}

@Composable
fun HomeContent(user: HomeUserEntity, dormitoryPoint: DormitoryPointEntity, meal: MealEntity) {
    Column(
        modifier = Modifier
            .background(Gray50)
            .fillMaxSize()
            .padding(12.dp)
    ) {
        HomeUserCard(user = user, dormitoryPoint = dormitoryPoint)
        Spacer(modifier = Modifier.height(12.dp))
        HomeMealCard(meal = meal)
    }
}

@Composable
fun HomeUserCard(user: HomeUserEntity, dormitoryPoint: DormitoryPointEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .padding(10.dp, 0.dp)

    ) {
        Image(
            painter = rememberAsyncImagePainter(model = user.profileImage),
            contentDescription = "profileImage",
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .padding(10.dp)
        )

        Column {
            Text(text = user.name, fontSize = 18.sp, color = Color.Black)
            Text(text = "상점 ${dormitoryPoint.goodPoint}점 벌점 ${dormitoryPoint.badPoint}")
        }
    }
}

@Composable
fun HomeMealCard(meal: MealEntity) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(
            text = "오늘의 메뉴",
            fontSize = 22.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp)
        )

        Row(
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            HomeMealItem(title = "아침", menus = meal.breakfast)
            Spacer(modifier = Modifier.width(10.dp))
            HomeMealItem(title = "점심", menus = meal.lunch)
            Spacer(modifier = Modifier.width(10.dp))
            HomeMealItem(title = "저녁", menus = meal.dinner)
        }
    }
}

@Composable
fun HomeMealItem(title: String, menus: List<String>) {
    var menuText = ""
    if (menus.isEmpty()) {
        menuText = "급식이 없습니다"
    } else {
        menus.forEach {
            menuText += "\n$it"
        }
    }

    Column(
        modifier = Modifier
            .size(145.dp, 200.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Gray50)
            .padding(10.dp)

    ) {
        Text(text = title, fontSize = 18.sp, modifier = Modifier.padding(10.dp))
        Text(text = menuText, fontSize = 16.sp, modifier = Modifier.padding(10.dp))
    }
}

@Preview
@Composable
fun HomeContentPreview() {
    HomeContent(
        user = HomeUserEntity("image", "김재원"),
        dormitoryPoint = DormitoryPointEntity(5, 2),
        meal = MealEntity(
            listOf("밥", "김치", "김", "생선"),
            listOf("밥", "김치", "김", "생선"),
            listOf("밥", "김치", "김", "생선")
        )
    )
}

@Preview
@Composable
fun HomeMealItemPreview() {
    HomeMealItem(title = "아침", menus = listOf("밥", "김치", "김", "생선"))
}