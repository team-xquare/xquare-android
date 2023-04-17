package com.xquare.xquare_android.feature.today_teacher

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xquare.domain.entity.pick.TodaySelfStudyTeacherEntity
import com.xquare.xquare_android.util.makeToast
import kotlinx.coroutines.flow.collect
import kotlin.reflect.KProperty


@Composable
fun TodayTeacherScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: TodayTeacherViewModel = hiltViewModel()
    var todayTeacher: TodaySelfStudyTeacherEntity? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        viewModel.TodaySelfStudyTeacher()
        viewModel.eventFlow.collect() {
            when (it) {
                is TodayTeacherViewModel.Event.Success -> {
                    todayTeacher = it.data
                }
                is TodayTeacherViewModel.Event.Failure -> {
                    makeToast(context, "자습감독선생님을 불러오지 못했습니다.")
                }
            }
        }
    }
    TodayTeacher(
        todayTeacher = todayTeacher,
        onBack = { navController.popBackStack() }
    )
}

@Composable
fun TodayTeacher(
    todayTeacher: TodaySelfStudyTeacherEntity?,
    onBack: () -> Unit,
){

}

@Preview(backgroundColor = true)
@Composable
fun TodayTeacherPreview(){
    TodayTeacher(
        todayTeacher =
    )
}


