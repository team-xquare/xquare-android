package com.xquare.xquare_android.feature.allmeal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xquare.domain.entity.meal.AllMealEntity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.AppBar
import com.xquare.xquare_android.util.makeToast

@Composable
fun AllMealScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: AllMealViewModel = hiltViewModel()
    var allMeal: AllMealEntity? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        viewModel.fetchAllMeal()
        viewModel.eventFlow.collect {
            when (it) {
                is AllMealViewModel.Event.Success -> {
                    allMeal = it.data
                }
                is AllMealViewModel.Event.Failure -> {
                    makeToast(context, "급식을 불러오는 데 실패했습니다")
                }
            }
        }
    }
    AllMeal(
        allMeal = allMeal,
        onBackPress = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AllMeal(
    allMeal: AllMealEntity?,
    onBackPress: () -> Unit
) {
    Column {
        AppBar(
            painter = painterResource(R.drawable.ic_placeholder),
            text = "전체 급식",
            onIconClick = onBackPress
        )
        allMeal?.let {
            CompositionLocalProvider(
                LocalOverScrollConfiguration provides null
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(allMeal.meals.count()) {
                        if (it == 0) Spacer(Modifier.size(20.dp))
                        MealDetail(allMeal.meals[it])
                        if (it == allMeal.meals.lastIndex)
                            Spacer(Modifier.size(20.dp))
                        else Spacer(Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}
