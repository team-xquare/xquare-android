package com.xquare.xquare_android.feature.pick

import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.semicolon.design.Body1
import com.semicolon.design.Body2
import com.semicolon.design.Subtitle4
import com.semicolon.design.color.primary.gray.gray200
import com.semicolon.design.color.primary.gray.gray300
import com.semicolon.design.color.primary.gray.gray50
import com.semicolon.design.color.primary.gray.gray800
import com.semicolon.design.color.primary.gray.gray900
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.MainActivity
import com.xquare.xquare_android.R
import com.xquare.xquare_android.component.Header
import com.xquare.xquare_android.getActivity
import com.xquare.xquare_android.util.DevicePaddings

@Composable
fun PassScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val mainActivity: MainActivity = context as MainActivity
    val vm: PassViewModel = hiltViewModel()
    val passData = vm.passData.collectAsState().value

    LaunchedEffect(Unit) {
        mainActivity.window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        vm.fetchPassData()
    }
    Scaffold(
        modifier = Modifier
            .background(white)
            .padding(
                top = DevicePaddings.statusBarHeightDp.dp,
                bottom = DevicePaddings.navigationBarHeightDp.dp,
            ),
        topBar = {
            Header(
                painter = painterResource(id = R.drawable.ic_back),
                title = "외출증",
                onIconClick = { navController.popBackStack() }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.size(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        painter = rememberAsyncImagePainter(
                            model = passData.profile_file_name,
                            placeholder = ColorPainter(gray200),
                            error = painterResource(id = R.drawable.ic_profile_default)
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Subtitle4(text = passData.student_name)
                }
                Spacer(modifier = Modifier.size(38.dp))
                PassInfoText(
                    title = "외출시간",
                    content = buildAnnotatedString {
                        append(passData.start_time)
                        append(" ~ ")
                        append(passData.end_time)
                    }.toString()
                )
                Spacer(modifier = Modifier.size(17.dp))
                PassInfoText(title = "사유", content = passData.reason)
                Spacer(modifier = Modifier.size(28.dp))
                PassInfoText(title = "확인교사", content = passData.teacher_name)
            }
        }
    }
}

@Composable
private fun PassInfoText(
    title: String,
    content: String,
) {
    Column {
        Body2(text = title, color = gray900)
        Spacer(Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color = gray300, shape = RoundedCornerShape(8.dp))
                .background(color = gray50)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Body1(text = content, color = gray800)
        }
    }
}
